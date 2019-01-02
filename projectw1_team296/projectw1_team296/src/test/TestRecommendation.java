package test;

import org.junit.jupiter.api.Test;
import Model.Recommendation;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRecommendation {

    //check get type, get comment, rating
    @Test
    public void testGetRec() {
        Recommendation r = new Recommendation("", "", "", "", "");
        assertTrue(r.getType().equals(""));
        assertTrue(r.getComment().equals(""));
        assertTrue(r.getRating().equals(""));
    }
}
