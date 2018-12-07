package apps.medicarwen.com.whattheweather.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import apps.medicarwen.com.whattheweather.R;
import apps.medicarwen.com.whattheweather.activities.FavoriteActivity;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewCityName;
    private TextView mTextConnection;
    private LinearLayout mLayoutOrange;
    private FloatingActionButton mBoutonFavoris;
    private EditText mChamptexte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WTF", "onCreate: MainActivity");
        setContentView(R.layout.activity_main);
        this.mTextViewCityName =findViewById(R.id.text_view_city_name);
        this.mBoutonFavoris = findViewById(R.id.boutonActionFlottant);
        this.mChamptexte = findViewById(R.id.texteSaisi);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),FavoriteActivity.class);
                Log.d("WTF", "onClick: texte recupere:"+mChamptexte.getText());
                intent.putExtra("texteSaisi",mChamptexte.getText().toString());
                startActivity(intent);
            }
        };

        this.mBoutonFavoris.setOnClickListener(onClickListener);
        mTextViewCityName.setText(R.string.city_name);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            this.mTextConnection = (TextView) findViewById(R.id.text_view_networkstatus);
            this.mLayoutOrange = (LinearLayout) findViewById(R.id.layoutOrange);
            this.mTextConnection.setVisibility(View.VISIBLE);
            this.mTextConnection.setText(R.string.text_about_not_connected);
            this.mBoutonFavoris.hide();
            this.mLayoutOrange.setVisibility(View.INVISIBLE);
        }
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
}
