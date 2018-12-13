package apps.medicarwen.com.whattheweather.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;

import apps.medicarwen.com.whattheweather.DataAccess.DataAccessCallOpenWeather;
import apps.medicarwen.com.whattheweather.R;
import apps.medicarwen.com.whattheweather.interfaces.MyCallback;
import apps.medicarwen.com.whattheweather.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.squareup.picasso.*;

public class MainActivity extends AppCompatActivity implements MyCallback {
    private TextView mTextViewCityName;
    private TextView mDescription;
    private TextView mTemperature;
    private ImageView mWeatherImage;
    private String mResponseWebService;
    private City currentCity;


    private TextView mTextConnection;
    private LinearLayout mLayoutOrange;
    private FloatingActionButton mBoutonFavoris;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WTF", "onCreate: MainActivity");
        setContentView(R.layout.activity_main);
        this.mTextViewCityName = findViewById(R.id.text_view_city_name);
        this.mDescription = findViewById(R.id.text_description);
        this.mTemperature = findViewById(R.id.text_temp);
        this.mWeatherImage = findViewById(R.id.image_meteo);
        this.mBoutonFavoris = findViewById(R.id.boutonActionFlottant);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        };

        this.mBoutonFavoris.setOnClickListener(onClickListener);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            afficherVueNerworkUnavailble();
        } else {
            getCityInfoFromWeb("0.688891","47.390026");
        }
    }


    private void getCityInfoFromWeb(String pLon,String pLat) {

        DataAccessCallOpenWeather.getJsonCityMeteoPerGPS(pLon,pLat,this);

    }

    private void afficherVueNerworkUnavailble() {
        this.mTextConnection = (TextView) findViewById(R.id.text_view_networkstatus);
        this.mLayoutOrange = (LinearLayout) findViewById(R.id.layoutOrange);
        this.mTextConnection.setVisibility(View.VISIBLE);
        this.mTextConnection.setText(R.string.text_about_not_connected);
        this.mBoutonFavoris.hide();
        this.mLayoutOrange.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("WTF", "onDestroy: MainActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("WTF", "onStart: Main Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WTF", "onResume: MainActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("WTF", "onPause: MainActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("WTF", "onStop: MainActivity");
    }

    private void renderCurrentWeather(String pJasonString) {
        Log.d("WTF", "onResponse: on est sur le net");
        try {
            Log.d("WTF", "renderCurrentWeather: " + pJasonString);
            currentCity = new City(pJasonString);
            this.mTextViewCityName.setText(currentCity.mName);
            this.mDescription.setText(currentCity.mMeteo.description);
            this.mTemperature.setText(currentCity.mInfoAir.getTemperature());
            Picasso.get().load(currentCity.mMeteo.getIconeURL()).into(this.mWeatherImage);
            Log.d("WTF", "renderCurrentWeather: " + currentCity.mName);
            Log.d("WTF", "renderCurrentWeather: " + currentCity.mMeteo.description);
            Log.d("WTF", "renderCurrentWeather: " + currentCity.mInfoAir.getTemperature());
        } catch (Exception e) {
            Log.d("WTF", "onCreate: " + e.getMessage());
        }

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(String strResponse) {
        Log.d("WTF", "onResponse: response is successful");

        Log.d("WTF", "onResponse: " + strResponse);
        mResponseWebService = strResponse;
        renderCurrentWeather(mResponseWebService);

    }

    /*  private void renderCurrentWeather()
    {
        this.mTextViewCityName.setText(currentCity.mName);
        this.mDescription.setText(currentCity.mMeteo.description);
        this.mTemperature.setText(currentCity.mInfoAir.getTemperature());
        Picasso.get().load(currentCity.mMeteo.getIconeURL()).into(this.mWeatherImage);
        Log.d("WTF", "renderCurrentWeather: "+currentCity.mName);
        Log.d("WTF", "renderCurrentWeather: "+currentCity.mMeteo.description);
        Log.d("WTF", "renderCurrentWeather: "+currentCity.mInfoAir.getTemperature());

    }*/
}
