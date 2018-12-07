package apps.medicarwen.com.whattheweather.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.adapter.FavoriteAdapter;
import apps.medicarwen.com.whattheweather.models.City;
import apps.medicarwen.com.whattheweather.R;

public class FavoriteActivity extends AppCompatActivity {
    private TextView texteSaisi;
    private Bundle extras;
    private ArrayList<City> mCities;
    private RecyclerView mRecyclerViewCities;
    private FavoriteAdapter favoriteAdapter;
    protected DialogInterface.OnClickListener listenOK;
    protected DialogInterface.OnClickListener listenCancel;
    private Context mContext;

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
                        mCities.add(new City(editTextCity.getText().toString(),"66°C"," Météo Surréaliste",R.drawable.weather_snowy_grey));
                    favoriteAdapter.notifyDataSetChanged();
                    }
                };

                builder.setPositiveButton(R.string.dialog_button_positive,listenOK);
                builder.setNegativeButton(R.string.dialog_button_cancel,listenCancel);
                builder.create().show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        City city1 = new City("Montréal","-140°C","Pluies apocalyptiques",R.drawable.weather_drizzle_white);
        City city2 = new City("New York","560°C","Eté coréen",R.drawable.weather_drizzle_grey);
        City city3 = new City("Paris","-10°C","Météo bretonne",R.drawable.weather_rainy_grey);
        City city4 = new City("Toulouse","30°C","Eté indien",R.drawable.weather_sunny_grey);
        mCities= new ArrayList<>();
        mCities.add(city1);
        mCities.add(city2);
        mCities.add(city3);
        mCities.add(city4);
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
}

