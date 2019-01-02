package Model;

import ui.UserManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Person {
    public String name;
    public ArrayList<String> citiesVisited;
    public ArrayList<String> friends = new ArrayList<>();
    public ArrayList<Recommendation> recs;
    public String currLocation;



    public Person() {
        name = "";
        citiesVisited = new ArrayList<>();
        recs = new ArrayList<>();

    }


    public Person(String nm) {
        name = nm;
        citiesVisited = new ArrayList<>();
        recs = new ArrayList<>();

    }

    public Person(String nm, ArrayList<String> cv) {
        name = nm;
        citiesVisited = cv;
        recs = new ArrayList<>();

    }

    public String getName() {return name;}

    public ArrayList<String> getCities() {return citiesVisited;}

    public ArrayList<Recommendation> getRecs() { return recs; }

    public ArrayList<String> getFriends() {return friends;}

    public void setName(String name) {this.name = name;}

    public void setCitiesVisited(ArrayList<String> citiesVisited) {this.citiesVisited = citiesVisited;}

    public void setRecs(ArrayList<Recommendation> recs) {this.recs = recs;}

    public void setFriends(ArrayList<String> friends) {this.friends = friends;}


    @Override
    public String toString() {
        return "Person [name=" + name + ", citiesVisited=" + citiesVisited + ", recs=" + recs + ", myFriends=" + friends + "]";
    }


    public void addFriend(String user) {
        if (!friends.contains(user)) {
            friends.add(user);
        }
    }

    public void addRecommendation(Recommendation r) {
        recs.add(r);
    }

}
