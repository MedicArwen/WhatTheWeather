package apps.medicarwen.com.whattheweather.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.DataAccess.DataAccessCallOpenWeather;
import apps.medicarwen.com.whattheweather.adapter.FavoriteAdapter;
import apps.medicarwen.com.whattheweather.interfaces.MyCallback;
import apps.medicarwen.com.whattheweather.models.City;
import apps.medicarwen.com.whattheweather.R;

public class FavoriteActivity extends AppCompatActivity implements MyCallback {
    private ArrayList<City> mCities;
    private RecyclerView mRecyclerViewCities;
    private FavoriteAdapter mFavoriteAdapter;
    private DialogInterface.OnClickListener listenOK;
    private DialogInterface.OnClickListener listenCancel;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("WTF", "FavoriteActivity.onCreate():Begin");
        super.onCreate(savedInstanceState);
        mContext = this;
        mCities = new ArrayList<>();

        Log.d("WTF", "FavoriteActivity.onCreate():Chargement du Layout de la fenetre");
        setContentView(R.layout.activity_favorite);
        Log.d("WTF", "FavoriteActivity.onCreate():Configuration de l'action BAR");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("WTF", "FavoriteActivity.onCreate():Recuperation du Bouton Flottant");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Log.d("WTF", "FavoriteActivity.onCreate():Abonnement du bouton flottant à l'evenement click");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDialogAddCityToFavorite(view);
            }
        });
        Log.d("WTF", "FavoriteActivity.onCreate():Configuration de la RecycledView des favoris");
        // on récupere l'objet instancié par le XML du layout (V)
        this.mRecyclerViewCities = findViewById(R.id.listeVilleFavorites);
        // associe un layout manager à la RecycledView (V)
        mRecyclerViewCities.setLayoutManager(new LinearLayoutManager(this));
        //on cree un adapteur que l'on lie au modèle (C et M)
        this.mFavoriteAdapter = new FavoriteAdapter(this, this.mCities);
        // on associe l'adapteur à la recycled view (V et C)
        mRecyclerViewCities.setAdapter(mFavoriteAdapter);
        Log.d("WTF", "FavoriteActivity.onCreate():End");
    }

    private void generateDialogAddCityToFavorite(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
        final EditText editTextCity = v.findViewById(R.id.edit_text_dialog_city);
        builder.setView(v);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message_addcity);
        listenCancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        listenOK = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("WTF", "onClick: " + editTextCity.getText().toString());
                try {
                    getCityInfoFromWeb(editTextCity.getText().toString());
                } catch (Exception e) {
                    Log.d("WTF", "onClick: " + e.getMessage());
                }
            }
        };
        builder.setPositiveButton(R.string.dialog_button_positive, listenOK);
        builder.setNegativeButton(R.string.dialog_button_cancel, listenCancel);
        builder.create().show();
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

    private void getCityInfoFromWeb(String pNomCity) {
        Log.d("WTF", "getCityInfoFromWeb:" + pNomCity);
        DataAccessCallOpenWeather.getJsonCityMeteoPerName(pNomCity, this);
    }

    @Override
    public void onFail() {
        Toast.makeText(mContext,"text_error_msg_webservice_failed",Toast.LENGTH_SHORT);
    }

    @Override
    public void onSuccess(String strResponse) {

        Log.d("WTF", "onResponse: response is successful");
        Log.d("WTF", "onResponse: " + strResponse);
        try {

            mCities.add(new City(strResponse));
            mFavoriteAdapter.notifyDataSetChanged();
            Toast.makeText(mContext,"text_error_msg_webservice_success",Toast.LENGTH_SHORT);
        } catch (Exception e) {
            Log.d("WTF", "onResponse: " + e.getMessage());
        }
    }
}

