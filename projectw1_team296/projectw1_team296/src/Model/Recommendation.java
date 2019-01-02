package Model;


import java.util.ArrayList;

public class Recommendation {
    private String type;
    private String comment;
    private String rating;
    private String authorName;
    private String city;

    public Recommendation(String authorName, String type, String comment, String rating, String city) {
        this.type = type;
        this.comment = comment;
        this.rating = rating;
        this.authorName = authorName;
        this.city = city;
    }

    public Recommendation() {
        type = "";
        comment = "";
        rating = "";
        authorName = "";
        city = "";
    }

    public String getType() { return type; }

    public String getComment() {
        return comment;
    }

    public String getRating() {
        return rating;
    }

    public String getAuthorName() { return authorName; }

    public String getCity() { return city; }

    public void setType(String type) {this.type = type;}

    public void setComment(String comment) {this.comment = comment;}

    public void setRating(String rating) {this.rating = rating;}

    public void setAuthorName(String authorName) {this.authorName = authorName;}

    public void setCity(String city) {this.city = city;}

    @Override
    public String toString() {
        return "Recommendation [ authorName=" + authorName + " , type=" + type + ", comment=" + comment + ", rating=" + rating + ",  city=" + city + "]";
    }
}
