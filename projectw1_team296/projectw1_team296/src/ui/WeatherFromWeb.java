package ui;
import Model.WeatherData;
import com.google.gson.Gson;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherFromWeb {
    public static WebView browser;
    public static String description;
    public static String temp;
    public static String iconAddress;
    public static ArrayList<String> weatherDataList;

    public void getWeather(String city) throws MalformedURLException {

        BufferedReader br = null;
        weatherDataList= new ArrayList<>();

        try {
            String apikey = "2803d021d68565c6353104e82cb3efb9";
            String weatherquery = "https://api.openweathermap.org/data/2.5/weather?q=";
            String theURL=weatherquery+city+"&APPID="+apikey;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            Gson gson = new Gson();
            WeatherData weatherData = gson.fromJson(br, WeatherData.class);

            description = weatherData.getDescription();

            //get image address
            iconAddress = weatherData.getIconAddress();

            browser = new WebView();
            WebEngine webEngine = browser.getEngine();
            webEngine.load(iconAddress);

            temp = weatherData.getTempInCelsius();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public String getIconAddress() {
        return iconAddress;
    }
}

