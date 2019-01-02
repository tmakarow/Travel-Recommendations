package test;

import org.junit.jupiter.api.Test;
import Model.City;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityTest {
    // check getting the name of a friend
    @Test
    public void testGetName() {
        City c = new City("name");
        assertTrue(c.getName().equals("name"));
    }
}
