package Model;

import java.util.ArrayList;
import java.util.HashSet;

public class City {
    private String name;

    public City() {
        name = "";
    }

    public City(String nm) {
        name = nm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    @Override
    public String toString() {
        return "City [name=" + name + "]";
    }



}



