package apps.medicarwen.com.whattheweather.DataAccess;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.interfaces.MyCallback;
import apps.medicarwen.com.whattheweather.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataAccessCallOpenWeather {
final public static String FILENAME ="FICHIERS_PREFERENCES_MYWEATHER";
final public static String CITYLIST="LISTE_CITIES_JSON";
static Handler handler;
    public static String getJsonCityMeteo(String strUrl, final MyCallback myCallback) {
        String sResponse = "";
        OkHttpClient okHttpClient;
        try {
            okHttpClient = new OkHttpClient();
            handler = new Handler();
            final Request request = new Request.Builder().url(strUrl).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallback.onFail();
                        }
                    });

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {


                    final String strResponse =response.body().string();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {

                                Log.d("WTF", "responseIsSuccessfull: "+strResponse);
                                    myCallback.onSuccess(strResponse);
                            } else {

                                Log.d("WTF", "responseIsfail ");
                                myCallback.onFail();
                            }
                        }
                    });
                }
            });

        } catch (Exception e) {
            Log.d("WTFBBQ", "getJsonCityMeteoPerGPS1: " + e.toString());
        }
        return sResponse;
    }
    public static String getJsonCityMeteoPerName(String pCityName, final MyCallback pMyCallBack)
    {
        String url = "http://api.openweathermap.org/data/2.5/weather?lang=fr&units=metric&appid=01897e497239c8aff78d9b8538fb24ea";
        url+="&q="+pCityName;
        Log.d("WTF", "getJsonCityMeteoPerGPS: "+url);
        return getJsonCityMeteo(url,pMyCallBack);

    }
    public static String getJsonCityMeteoPerGPS(String pLon, String pLat, final MyCallback pMyCallBack)
    {
        String url = "http://api.openweathermap.org/data/2.5/weather?lang=fr&units=metric&appid=01897e497239c8aff78d9b8538fb24ea";
        url+="&lat="+pLat+"&lon="+pLon;
        Log.d("WTF", "getJsonCityMeteoPerGPS: "+url);
        return getJsonCityMeteo(url,pMyCallBack);

    }
    public static void saveCityList(Context pContext,  ArrayList pListeCity)
    {
        Log.d("WTF", "saveCityList: "+pListeCity.toString());
        SharedPreferences preferences = pContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CITYLIST,pListeCity.toString());
        editor.apply();
        Log.d("WTF", "saveCityList: les préférences ont été sauvegardées.");
    }
    public static JSONArray loadCityList(Context pContext) throws JSONException
    {
        Log.d("WTF", "loadCityList: Je vais parser=");
        SharedPreferences preferences = pContext.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        Log.d("WTF", "loadCityList:"+preferences.getString(CITYLIST,""));
        JSONArray jsonArray = new JSONArray(preferences.getString(CITYLIST,""));
        Log.d("WTF", "loadCityList: array="+jsonArray.toString());
        return jsonArray;

    }
}



