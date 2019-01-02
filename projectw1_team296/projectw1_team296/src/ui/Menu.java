package ui;

import Model.*;
import Model.GUI_extras.*;
import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu {
   // public static UserManager u = new UserManager();
    private static  String temp, description, iconAddress;
    private static final StringProperty tempProperty = new SimpleStringProperty();
    private static final StringProperty descriptionProperty = new SimpleStringProperty();
    private static final StringProperty iconProperty = new SimpleStringProperty();
    private static Scene scene1, loginScreen, createAccount, location, mainMenu, weatherScene;
    private static Button backButton = new Button("Back");
    private static Label weatherDescriptionLabel, tempLabel;
    private static ImageView imgView;
    private static int userID;
    public static ArrayList<Recommendation> recs;
    public static ArrayList<String> friends;
    public static Person currUser = new User();
    public static Database d;
    static {
        try {
            d = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void display(Stage window) {
        window.setTitle("Travel Recommendations");

        setWelcomeScene1(window);
        loginScene2(window);
        createAccountScene3(window);
        setLocationScene4(window);
        setMainMenuScene5(window);
        setScene6Weather(window);

    }
    public static void load() {
        try {
            currUser.setCitiesVisited(d.getCitiesVisited(userID));
            currUser.setFriends(d.getFriends(userID));
            currUser.setRecs(d.getRecs(userID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setWelcomeScene1(Stage window) {
        Label welcomeLabel = new Label("Welcome");
        Button login = new Button("Login");
        login.setOnAction(e -> window.setScene(loginScreen));
        Button newUser = new Button("New User");
        newUser.setOnAction(e -> window.setScene(createAccount));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 10, 20, 10));
        vbox.getChildren().addAll(welcomeLabel, login, newUser);
        vbox.setAlignment(Pos.CENTER);
        scene1 = new Scene(vbox, 500, 500);
        scene1.getStylesheets().add("travel.css");
        window.setScene(scene1);
    }

    private static void loginScene2(Stage window) {
        //Name Label
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name input
        TextField nameInput = new TextField("Tara");
        GridPane.setConstraints(nameInput,1, 0);

        Button loginButtonText = new Button("Login: ");
        GridPane.setConstraints(loginButtonText, 1, 2);
        loginButtonText.setOnAction( e -> {
            currUser.setName(nameInput.getText());
            try {
                userID = d.getUserIDFromName(currUser.name);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("could't get ID from name");
            }
            load();
            window.setScene(location);
        });

        backButton.setOnAction(e -> window.setScene(scene1));

        VBox layout2 = new VBox(20);
        layout2.getStylesheets().add("travel.css");
        layout2.setPadding(new Insets(10, 10, 10,10));
        layout2.setAlignment(Pos.CENTER_LEFT);
        layout2.getChildren().addAll(backButton, nameLabel, nameInput, loginButtonText);
        loginScreen = new Scene(layout2, 500, 500);

    }

    private static void createAccountScene3(Stage window) {
        //Name Label
        Label nameLabel = new Label("Create Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name input
        TextField nameInputEmpty = new TextField();
        GridPane.setConstraints(nameInputEmpty,1, 0);

        Button createUserButton = new Button("Create Account: ");
        GridPane.setConstraints(createUserButton, 1, 2);
        createUserButton.setOnAction( e -> {
            currUser.setName(nameInputEmpty.getText());
            try {
                d.addUser(nameInputEmpty.getText());
                userID = d.getUserIDFromName(currUser.name);
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("error could't add new user");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("Couldn't get ID from name");
            }
            window.setScene(location);
        });

        backButton.setOnAction(e -> window.setScene(scene1));

        VBox layout3 = new VBox(20);
        layout3.getStylesheets().add("travel.css");
        layout3.setPadding(new Insets(10, 10, 10,10));
        layout3.setAlignment(Pos.CENTER_LEFT);
        layout3.getChildren().addAll(backButton, nameLabel, nameInputEmpty, createUserButton);
        createAccount = new Scene(layout3, 500, 500);
    }

    private static void setLocationScene4(Stage window) {
        Label locationLabel = new Label("Enter Location:");
        TextField locationInput = new TextField();
        Button enterButton = new Button("Enter");
        enterButton.setOnAction(e -> {
            currUser.currLocation = locationInput.getText();
            if (!currUser.getCities().contains(currUser.currLocation)){
                try {
                    d.addCity(userID, currUser.currLocation);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }}
            window.setScene(mainMenu);
        });

        VBox layout4 = new VBox(20);
        layout4.getStylesheets().add("travel.css");
        layout4.setPadding(new Insets(10, 10, 10,10));
        layout4.setAlignment(Pos.CENTER_LEFT);
        layout4.getChildren().addAll(backButton, locationLabel, locationInput, enterButton);
        location = new Scene(layout4, 500, 500);

    }

    private static void setMainMenuScene5(Stage window) {
        Button weatherButton = new Button("Weather");
        weatherButton.setOnAction(e -> {
            WeatherFromWeb weather = new WeatherFromWeb();
            try {
                weather.getWeather(currUser.currLocation);
                setTemp(weather.getTemp());
                setDescription(weather.getDescription());
                imgView.setImage(new Image(weather.getIconAddress()));
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
                System.out.println("bad URL");
            }
            window.setScene(weatherScene);
        });
        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setOnAction(e -> {
            String name = AddFriendBox.display("Add New Friend", "Enter friend's name:");
            currUser.addFriend(name);
            try {
                d.addFriend(name, userID);
            } catch (Exception e1) {
                System.out.println("failed to add friend: canceled or user not found");
            }
        });
        Button addRecButton = new Button("Add Recommendation");
        addRecButton.setOnAction(e -> {
            Recommendation rec = AddRecBox.display("Add New Recommendation");
            currUser.addRecommendation(rec);
            try {
                d.addRec(userID, rec.getType(), rec.getComment(), rec.getRating(), rec.getCity());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });
        Button seeFWVButton = new Button("See friends who have visited your location");
        seeFWVButton.setOnAction(e -> {
            try {
                if (d.getFriendsWhoVisited(currUser.currLocation, userID ).size() > 0) {
                    friends = new ArrayList<>();
                    for (Person f : d.getFriendsWhoVisited(currUser.currLocation, userID)) {
                        friends.add(f.getName());
                    }
                    FriendListBox.display();
                } else {
                    ErrorBox.display("Friends who have visited "+currUser.currLocation, "Sorry, no friends have visited yet.");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        Button getRecButton = new Button("Get Recommendations");
        getRecButton.setOnAction(e -> {
            try {
                recs = d.getRecsForLocation(currUser.currLocation);
                PresentRecBox.display();
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("Error: could not get recommendations");
            }

        });
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            System.out.println("byeeeeeeee");
            window.close();

        });

        VBox layout5 = new VBox(20);
        layout5.getStylesheets().add("travel.css");
        layout5.setPadding(new Insets(10, 10, 10,10));
        layout5.setAlignment(Pos.CENTER);
        layout5.getChildren().addAll(backButton, weatherButton, addFriendButton, addRecButton, seeFWVButton, getRecButton, quitButton);
        mainMenu = new Scene(layout5, 500, 500);

    }

    private static void setScene6Weather(Stage window) {
        weatherDescriptionLabel = new Label();
        weatherDescriptionLabel.textProperty().bind(descriptionProperty);
        tempLabel = new Label("init");
        tempLabel.textProperty().bind(tempProperty);
        Image img = new Image("http://openweathermap.org/img/w/04n.png");
        imgView = new ImageView(img);
        Button backToMenu = new Button("Back");
        backToMenu.setOnAction(e -> window.setScene(mainMenu) );


        VBox layout6 = new VBox(20);
        layout6.getStylesheets().add("travel.css");
        layout6.setPadding(new Insets(10, 10, 10,10));
        layout6.setAlignment(Pos.TOP_CENTER);
        layout6.getChildren().addAll(backToMenu, imgView, tempLabel, weatherDescriptionLabel);
        weatherScene = new Scene(layout6, 500, 500);
    }

    private static void setTemp(String value) {
        tempProperty.set(value);
    }

    public static String getTemp() {
        return tempProperty.get();
    }

    public static StringProperty tempProperty() {
        return tempProperty;
    }


    public static void setDescription(String value) {
        descriptionProperty.set(value);
    }

    public static String getDescription() {
        return descriptionProperty.get();
    }

    public static StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public static void setIcon(String value) {
        iconProperty.set(value);
    }
    public static String getIcon() {
        return iconProperty.get();
    }

    public static StringProperty getIconProperty() {
        return iconProperty;
    }




}
