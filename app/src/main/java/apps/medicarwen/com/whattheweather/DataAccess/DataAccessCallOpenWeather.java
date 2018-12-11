package apps.medicarwen.com.whattheweather.DataAccess;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataAccessCallOpenWeather {


    public static String getJsonCityMeteoPerGPS(double lon, double lat) {
        String sResponse="";
        OkHttpClient mOkHttpClient;
        try {
            mOkHttpClient = new OkHttpClient();
            final Request request = new Request.Builder().url("http://api.openweathermap.org/data/2.5/weather?lang=fr&units=metric&lat=47.390026&lon=0.688891&appid=01897e497239c8aff78d9b8538fb24ea").build();
            Response response = mOkHttpClient.newCall(request).execute();
            sResponse = response.body().string();
        }
        catch (Exception e)
        {
            Log.d("WTFBBQ", "getJsonCityMeteoPerGPS1: "+e.toString());
        }
        return sResponse;
    }
}



