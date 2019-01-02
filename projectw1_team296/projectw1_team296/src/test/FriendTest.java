package test;

import Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FriendTest {

    // check getting the name of a friend
    @Test
    public void testGetName() {
        User f = new User("name");
        assertTrue(f.getName().equals("name"));
    }

}
