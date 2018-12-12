package apps.medicarwen.com.whattheweather.models;

import org.json.JSONException;
import org.json.JSONObject;

import apps.medicarwen.com.whattheweather.DataAccess.DataAccessCallOpenWeather;

public class City  {

    public String mName;
    public int mCityId;
    public AirInfo mInfoAir;
    public Weather mMeteo;
    public Coord mCoordonnees;
    public City(String pJasonString) throws JSONException
    {
        JSONObject jSon = new JSONObject(pJasonString);
        mName=jSon.getString("name");
        mInfoAir = new AirInfo(jSon.getJSONObject("main"));
        mMeteo= new Weather((JSONObject)(jSon.getJSONArray("weather").get(0)));
        mCoordonnees = new Coord(jSon.getJSONObject("coord"));
        mCityId=jSon.getInt("id");
    }


}