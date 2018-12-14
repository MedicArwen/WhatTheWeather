package apps.medicarwen.com.whattheweather.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;

    public Weather(JSONObject pWeather) throws JSONException
    {
        id=pWeather.getInt("id");
        main=pWeather.getString("main");
        description=pWeather.getString("description");
        icon=pWeather.getString("icon");
    }

    public Weather(String pDescription, String pIcon) {
        description=pDescription;
        icon=pIcon;
    }

    public String getIconeURL()
    {
        return "http://openweathermap.org/img/w/"+icon+".png";
    }
}
