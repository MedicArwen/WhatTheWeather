package apps.medicarwen.com.whattheweather.DataAccess;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.DataBaseHelper.DataBaseHelper;
import apps.medicarwen.com.whattheweather.interfaces.MyCallback;
import apps.medicarwen.com.whattheweather.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataAccessCallOpenWeather {
    final private static String FILENAME = "FICHIERS_PREFERENCES_MYWEATHER";
    final private static String CITYLIST = "LISTE_CITIES_JSON";
    private static Handler handler;

    private static String getJsonCityMeteo(String strUrl, final MyCallback myCallback) {
        String sResponse = "";
        OkHttpClient okHttpClient;
        try {
            okHttpClient = new OkHttpClient();
            handler = new Handler();
            final Request request = new Request.Builder().url(strUrl).build();
            Log.d("WTF", "request:" + strUrl);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallback.onFail();
                        }
                    });
                    Log.d("WTF", "on failure:" + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {


                    final String strResponse = response.body().string();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {

                                Log.d("WTF", "responseIsSuccessfull: " + strResponse);
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

    public static String getJsonCityMeteoPerName(String pCityName, final MyCallback pMyCallBack) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lang=fr&units=metric&appid=01897e497239c8aff78d9b8538fb24ea";
        url += "&q=" + pCityName;
        Log.d("WTF", "getJsonCityMeteoPerGPS: " + url);
        return getJsonCityMeteo(url, pMyCallBack);

    }

    public static String getJsonCityMeteoPerGPS(String pLon, String pLat, final MyCallback pMyCallBack) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lang=fr&units=metric&appid=01897e497239c8aff78d9b8538fb24ea";
        url += "&lat=" + pLat + "&lon=" + pLon;
        Log.d("WTF", "getJsonCityMeteoPerGPS: " + url);
        return getJsonCityMeteo(url, pMyCallBack);

    }

    public static void saveCityList(Context pContext, ArrayList pListeCity) {
        Log.d("WTF", "saveCityList: " + pListeCity.toString());
        SharedPreferences preferences = pContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CITYLIST, pListeCity.toString());
        editor.apply();
        Log.d("WTF", "saveCityList: les préférences ont été sauvegardées.");
    }

    public static ArrayList<City> loadCityList(Context pContext) throws JSONException {
        ArrayList<City> listeCity = new ArrayList<>();
        Log.d("WTF", "loadCityList: Je vais parser=");
        SharedPreferences preferences = pContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        Log.d("WTF", "loadCityList:" + preferences.getString(CITYLIST, ""));
        JSONArray jsonArray = new JSONArray(preferences.getString(CITYLIST, ""));
        Log.d("WTF", "loadCityList: array=" + jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            listeCity.add(new City(jsonArray.get(i).toString()));
        }
        return listeCity;

    }

    public static ArrayList<City> loadCityListDB(Context pContext) {

        Log.d("WTF", "loadCityListDB DATAACCESS");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(pContext, null, null, 1);
        Log.d("WTF", "loadCityListDB: nouveau helper cree:" + dataBaseHelper.toString());
        return dataBaseHelper.selectAllCities();
    }

    public static void saveCityListDB(Context pContext, ArrayList<City> pListeCity) {
        Log.d("WTF", "saveCityListDB: " + pListeCity.toString());
        Log.d("WTF", "saveCityListDB DATAACCESS");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(pContext, null, null, 1);
        int i = 0;
        for (City currentCity : pListeCity
                ) {
            dataBaseHelper.insertCity(currentCity);
            i++;
        }
        Log.d("WTF", "saveCityListDB: les préférences ont été sauvegardées(" + i + ")");
    }

    public static void deleteCityDB(Context pContext, City pCity) {
        Log.d("WTF", "deleteCityDB -City to Delete: "+pCity.toString());
        Log.d("WTF", "Delete DATAACCESS");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(pContext,null,null,1);
        int i = dataBaseHelper.deleteCity(pCity.mCityId);
        if (i==0)
            Log.d("WTF", "deleteCityDB: city not deleted");
            else
        Log.d("WTF", "deleteCityDB: city deleted");
    }
    public static void insertCityDB(Context pContext, City pCity) {
        Log.d("WTF", "insertCityDB "+pCity.toString());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(pContext,null,null,1);
        long i = dataBaseHelper.insertCity(pCity);
        if (i==0)
            Log.d("WTF", "insertCityDB: city not added");
        else
            Log.d("WTF", "insertCityDB: city added");
    }

}



