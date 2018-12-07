package apps.medicarwen.com.whattheweather.models;

public class City  {

    public String mName;
    public int mWeatherResIconGrey;
    public String mTemperature;
    public String mDescription;

    public City(String name, String temp, String desc, int resIconGrey) {
        mName = name;
        mTemperature = temp;
        mDescription = desc;
        mWeatherResIconGrey = resIconGrey;
    }
}
