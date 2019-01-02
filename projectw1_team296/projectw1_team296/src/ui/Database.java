package ui;

import Model.City;
import Model.Person;
import Model.Recommendation;
import Model.User;
import Observer.LocationMonitor;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Database extends Observable {
    private Connection conObj;
    private Statement stObj;


    public Database() throws SQLException {

        //Creating Connection class's Object which consist of database url , username and password
        conObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","root");
        //Creating Statement Class's object which is responsible for performing all db tasks
        stObj = conObj.createStatement();

        addObserver(new LocationMonitor());
    }

    private Person setUser(int ID) throws Exception {
        Person p = new User();
        p.setName(getNameFromID(ID));
        p.setCitiesVisited(getCitiesVisited(ID));
        p.setFriends(getFriends(ID));
        p.setRecs(getRecs(ID));
        return p;
    }

    public ArrayList<Recommendation> getRecs(int ID) throws SQLException {
        Statement stObj2 = conObj.createStatement();
        String query = "select * from recs";
        ResultSet rs = stObj2.executeQuery(query);
        ArrayList<Recommendation> recs = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("user_ID") == ID) {
                String author = getNameFromID(ID);
                String type = rs.getString("type");
                String comment = rs.getString("comment");
                String rating = rs.getString("rating");
                String city = rs.getString("city");
                Recommendation rec = new Recommendation(type, comment, rating, author, city);
                recs.add(rec);
            }
        }
        return recs;
    }

    public int getUserIDFromName(String name) throws Exception {
        String query = "select * from users";
        ResultSet rs = stObj.executeQuery(query);

        int ID = 0;
        while (rs.next()) {
            if (rs.getString("name").equals(name)) {
                ID = rs.getInt("ID");
            }
        }
        return ID;
    }

    public ArrayList<String> getCitiesVisited(int ID) throws Exception {
        String query = "select * from citiesVisited";
        ResultSet rs = stObj.executeQuery(query);
        ArrayList<String> citiesVisited = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("user_ID") == ID) {
                citiesVisited.add(rs.getString("city"));
            }
        }
        return citiesVisited;
    }

    public ArrayList<String> getFriends(int ID) throws Exception {
        String query = "select * from relationships";
        ResultSet rs = stObj.executeQuery(query);
        List<Integer> IDfriends = new ArrayList<>();
        ArrayList<String> friends = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("user_id1") == ID) {
                int friendID = (rs.getInt("user_id2"));
                if (rs.getInt("rel_type")== 1) {
                    IDfriends.add(friendID);
                }
            }
        }
        for (int id : IDfriends) {
            friends.add(getNameFromID(id));
            }
        return friends;
    }

    private String getNameFromID(int ID) throws SQLException {
        String query = "select * from users";
        ResultSet rs = stObj.executeQuery(query);
        String name = null;
        while (rs.next()) {
            if (rs.getInt("id") == ID) {
                name = rs.getString("name");
            }
        }
        return name;
    }


    public void addUser(String name) throws SQLException
    {
        if(name!=null)
        {
            String query = "insert into users(name)"
                    + " values (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conObj.prepareStatement(query);
            preparedStmt.setString (1, name);

            // execute the preparedstatement
            preparedStmt.execute();
        }
    }


    public void deleteData(String name) throws Exception
    {
        String query = "delete from user where name = \""+name+"\"";
        int a = stObj.executeUpdate(query);

        if(a == 1)
        {
            System.out.println("delete Successful");
        }
        else
        {
            System.out.println("deletion Failed");
        }
    }

    public void addRec(int id, String type, String comment, String rating, String city) throws SQLException {
        // the mysql insert statement
        String query = " insert into recs (user_id, type, comment, rating, city)"
                + " values (?, ?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conObj.prepareStatement(query);
        preparedStmt.setInt (1, id);
        preparedStmt.setString (2, type);
        preparedStmt.setString  (3, comment);
        preparedStmt.setString(4, rating);
        preparedStmt.setString    (5, city);

        // execute the preparedstatement
        preparedStmt.execute();
    }

    public ArrayList<Person> getFriendsWhoVisited(String location, int currUserID) throws Exception {
        Statement stObj3 = conObj.createStatement();
        String query = "select * from citiesVisited";
        ResultSet rs = stObj3.executeQuery(query);
        ArrayList<Person> friendsVisited = new ArrayList<>();
        while (rs.next()) {
            if (rs.getString("city").equals(location)) {
                int user_id = rs.getInt("user_id");
                if (ifFriend(user_id, currUserID) == 1) {
                    Person friend = setUser(user_id);
                    friendsVisited.add(friend);
                }
            }
        }
        return friendsVisited;
    }

    private int ifFriend(int user_id, int currUserID) throws SQLException {
        int ifFriend = 0;
        String query = "select * from relationships";
        ResultSet rs = stObj.executeQuery(query);
        while (rs.next()) {
            if (rs.getInt("user_id2") == user_id && rs.getInt("user_id1") == currUserID) {
                ifFriend = rs.getInt("rel_type");
            }
        }
        return ifFriend;
    }

    public ArrayList<Recommendation> getRecsForLocation(String location) throws SQLException {
        Statement stObj4 = conObj.createStatement();
        String query = "select * from recs";
        ResultSet rs = stObj4.executeQuery(query);
        ArrayList<Recommendation> recsForCity = new ArrayList<>();
        while (rs.next()) {
            if (rs.getString("city").equals(location)) {
                int authorID = rs.getInt("user_id");
                String author = getNameFromID(authorID);
                String type = rs.getString("type");
                String comment = rs.getString("comment");
                String rating = rs.getString("rating");
                String city = rs.getString("city");
                Recommendation rec = new Recommendation(author, type, comment, rating, city);
                recsForCity.add(rec);
            }
        }
        return recsForCity;
    }

    public void addFriend(String name, int userID) throws Exception {
        if(name!=null) {

            String query = "insert into relationships(user_id1, user_id2, rel_type)"
                    + " values (?, ?, ?)";


            int user_id2 = getUserIDFromName(name);

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conObj.prepareStatement(query);
            preparedStmt.setInt (1, userID);
            preparedStmt.setInt (2, user_id2);
            preparedStmt.setInt  (3, 1);

            // execute the preparedstatement
            preparedStmt.execute();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt2 = conObj.prepareStatement(query);
            preparedStmt.setInt (1, user_id2);
            preparedStmt.setInt (2, userID);
            preparedStmt.setInt  (3, 1);

            // execute the preparedstatement
            preparedStmt.execute();
        }
    }

    public void addCity(int userID, String city) throws Exception {
        if(city!=null) {
            setChanged();
            notifyObservers(city);

                String query = "insert into citiesvisited(user_id, city)"
                        + " values (?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conObj.prepareStatement(query);
                preparedStmt.setInt (1, userID);
                preparedStmt.setString (2, city);

                // execute the preparedstatement
                preparedStmt.execute();

            }
    }




}