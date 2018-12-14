package apps.medicarwen.com.whattheweather.models;

import org.json.JSONException;
import org.json.JSONObject;

public class City  {

    public String mName;
    public int mCityId;
    public AirInfo mInfoAir;
    public Weather mMeteo;
    public Coord mCoordonnees;
    private String mJson;
    private int mIdDataBase;
    public City(String pJasonString) throws JSONException
    {
        JSONObject jSon = new JSONObject(pJasonString);
        mName=jSon.getString("name");
        mInfoAir = new AirInfo(jSon.getJSONObject("main"));
        mMeteo= new Weather((JSONObject)(jSon.getJSONArray("weather").get(0)));
        mCoordonnees = new Coord(jSon.getJSONObject("coord"));
        mCityId=jSon.getInt("id");
        mJson = pJasonString;
    }

    public City(String pCityName, int pIdCity, AirInfo pAirInfo, Weather pMeteo, Coord pCoord) {
        mName=pCityName;
        mCityId=pIdCity;
        mInfoAir=pAirInfo;
        mMeteo=pMeteo;
        mCoordonnees=pCoord;
    }

    @Override
    public String toString() {
        if (mJson==null)
            return "("+mName+","+mCityId+")";
            else
        return mJson;
    }
}