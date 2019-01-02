package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherData {
    //adress for icon download
    private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

    static class Weather {
        public String main;
        public String description;
        public String icon;

    }

    static class Main {
        float temp;
    }

    List<Weather> weather;

    Main main;
    String name;

    private Main getMain() {
        return main;
    }

    public String getTempInCelsius() {
        float temp = getMain().temp -273.15f;
        return "The temperature is: "+String.format("%.2f", temp)+"°C";
    }

    public String getIconAddress() {
        return ICON_ADDR + weather.get(0).icon + ".png";
    }

    public String getDescription() {
        if (weather != null && weather.size() >0) {
            return "Today there is: "+weather.get(0).description;
        }
        return null;
    }

}
