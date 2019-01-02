package Model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class User extends Person{


    public User() {
        super();
    }

    //add recs
    public User(String nm) {
        super(nm);
    }

    //add recs
    public User(String nm, ArrayList<String> cv) {
        super(nm, cv);
    }

}

