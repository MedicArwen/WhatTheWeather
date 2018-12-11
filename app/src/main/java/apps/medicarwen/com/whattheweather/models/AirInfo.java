package apps.medicarwen.com.whattheweather.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class AirInfo {
    public double temp;
    public double pressure;
    public double humidity;
    public double temp_min;
    public double temp_max;

    public AirInfo(JSONObject pCoord) throws JSONException
    {
        temp=pCoord.getDouble("temp");
        pressure=pCoord.getDouble("pressure");
        humidity=pCoord.getDouble("humidity");
        temp_min=pCoord.getDouble("temp_min");
        temp_max=pCoord.getDouble("temp_max");
    }
    public String getTemperature()
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(0); //nb de chiffres apres la virgule
        return format.format(temp)+"°C";
    }

}
