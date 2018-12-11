package apps.medicarwen.com.whattheweather.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.adapter.FavoriteAdapter;
import apps.medicarwen.com.whattheweather.models.City;
import apps.medicarwen.com.whattheweather.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoriteActivity extends AppCompatActivity {
    private TextView texteSaisi;
    private Bundle extras;
    private ArrayList<City> mCities;
    private RecyclerView mRecyclerViewCities;
    private FavoriteAdapter favoriteAdapter;
    protected DialogInterface.OnClickListener listenOK;
    protected DialogInterface.OnClickListener listenCancel;
    private Context mContext;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        Log.d("WTF", "onCreate: Favorite Activity 1");
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    listenCancel =  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite,null);
                final EditText editTextCity = v.findViewById(R.id.edit_text_dialog_city);
                builder.setView(v);
                builder.setTitle(R.string.dialog_title);
                builder.setMessage(R.string.dialog_message_addcity);

                listenOK = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("WTF", "onClick: "+editTextCity.getText().toString());
                        try {
                            GetCityInfoFromWeb(editTextCity.getText().toString());
                        }
                        catch (Exception e)
                        {
                            Log.d("WTF", "onClick: "+e.getMessage());
                        }
                      //  mCities.add(new City(editTextCity.getText().toString(),"66°C"," Météo Surréaliste",R.drawable.weather_snowy_grey));

                    }
                };

                builder.setPositiveButton(R.string.dialog_button_positive,listenOK);
                builder.setNegativeButton(R.string.dialog_button_cancel,listenCancel);
                builder.create().show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCities= new ArrayList<>();
        this.mRecyclerViewCities = findViewById(R.id.listeVilleFavorites);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerViewCities.setLayoutManager(layoutManager);

        this.favoriteAdapter = new FavoriteAdapter(this,this.mCities);
        mRecyclerViewCities.setAdapter(favoriteAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("WTF", "onDestroy: FavoriteActivity");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("WTF", "onStart: FavoriteActivity");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WTF", "onResume: FavoriteActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("WTF", "onPause: FavoriteActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("WTF", "onStop: FavoriteActivity");
    }
    private void GetCityInfoFromWeb(String pNomCity) throws IOException {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler();
        final Request request = new Request.Builder().url("http://api.openweathermap.org/data/2.5/weather?lang=fr&units=metric&q="+pNomCity+"&appid=01897e497239c8aff78d9b8538fb24ea").build();
        Log.d("WTF", "onResponse: allo monsieur l'ordinateur");
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("WTF", "onFailure: ERROR OpenWeather");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    Log.d("WTF", "onResponse: response is successful");
                    final String stringJason = response.body().string();
                    Log.d("WTF", "onResponse: " + stringJason);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mCities.add(new City(stringJason));
                                favoriteAdapter.notifyDataSetChanged();

                            } catch (JSONException je) {
                                Log.d("WTF", "run: "+je.getMessage());

                            }

                        }
                    });
                }
            }
        });
    }

}

