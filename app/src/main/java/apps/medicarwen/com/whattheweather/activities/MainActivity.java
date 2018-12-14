package apps.medicarwen.com.whattheweather.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import apps.medicarwen.com.whattheweather.DataAccess.DataAccessCallOpenWeather;
import apps.medicarwen.com.whattheweather.R;
import apps.medicarwen.com.whattheweather.interfaces.MyCallback;
import apps.medicarwen.com.whattheweather.models.City;
import apps.medicarwen.com.whattheweather.models.Coord;
import okhttp3.OkHttpClient;
import android.location.LocationManager;
import android.location.LocationListener;
import android.widget.Toast;
import com.squareup.picasso.*;

public class MainActivity extends AppCompatActivity implements MyCallback {
    private final static int REQUEST_CODE_ASK_PERMISSION=1;
    private TextView mTextViewCityName;
    private TextView mDescription;
    private TextView mTemperature;
    private ImageView mWeatherImage;
    private String mResponseWebService;
    private City currentCity;
    private Context mContext;


    private TextView mTextConnection;
    private LinearLayout mLayoutOrange;
    private FloatingActionButton mBoutonFavoris;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private LocationManager mLocationManager;
    private Coord mCoordonnees;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mCoordonnees= new Coord(location.getLongitude(),location.getLatitude());
            Toast.makeText(mContext,R.string.text_geoloc_succes,Toast.LENGTH_SHORT).show();
            getCityInfoFromWeb(mCoordonnees);
            mLocationManager.removeUpdates(mLocationListener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WTF", "onCreate: MainActivity");
        mContext= this;
        setContentView(R.layout.activity_main);
        this.mTextViewCityName = findViewById(R.id.text_view_city_name);
        this.mDescription = findViewById(R.id.text_description);
        this.mTemperature = findViewById(R.id.text_temp);
        this.mWeatherImage = findViewById(R.id.image_meteo);
        this.mBoutonFavoris = findViewById(R.id.boutonActionFlottant);
        mCoordonnees = null;
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
            try {
                mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,R.string.text_geoloc_nopermission,Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String []{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE_ASK_PERMISSION);
                }
                else
                {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                    Toast.makeText(this,R.string.text_geoloc_start,Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception e)
            {

            }
        }
    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSION:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {

                }
                break;
                default:super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    private void getCityInfoFromWeb(Coord pCoordonnees) {

        DataAccessCallOpenWeather.getJsonCityMeteoPerGPS(pCoordonnees,this);

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
}
