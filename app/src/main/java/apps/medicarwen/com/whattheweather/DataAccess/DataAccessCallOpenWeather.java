package apps.medicarwen.com.whattheweather.DataAccess;
import android.os.Handler;
import android.util.Log;
import java.io.IOException;

import apps.medicarwen.com.whattheweather.interfaces.MyCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataAccessCallOpenWeather {

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
}



