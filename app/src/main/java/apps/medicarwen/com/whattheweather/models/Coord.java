package apps.medicarwen.com.whattheweather.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Coord {
    public double lon;
    public double lat;
    public Coord(JSONObject pCoord) throws JSONException
    {
        lon=pCoord.getInt("lon");
        lat=pCoord.getInt("lat");
    }
}
