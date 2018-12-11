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
    public City(double lon, double lat) throws JSONException
    {
            this(DataAccessCallOpenWeather.getJsonCityMeteoPerGPS(lon, lat));
    }

}
/*
{"coord":{"lon":0.69,"lat":47.39},
"weather":[{"id":741,"main":"Fog","description":"fog","icon":"50d"}],
"base":"stations","main":{"temp":275.15,"pressure":1026,"humidity":100,
"temp_min":275.15,"temp_max":275.15},"visibility":300,"wind":{"speed":1.41,"deg":49.0082},"
clouds":{"all":90},"dt":1544518800,"sys":{"type":1,"id":6537,"message":0.005,"country":"FR",
"sunrise":1544513681,"sunset":1544544389},"id":6454060,"name":"Tours","cod":200}
*/