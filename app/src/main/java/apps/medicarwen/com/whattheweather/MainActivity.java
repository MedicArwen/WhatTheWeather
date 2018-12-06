package apps.medicarwen.com.whattheweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewCityName;
    private TextView mTextConnection;
    private LinearLayout mLayoutOrange;
    private Button mBoutonFavoris;
    private Button mBouton1;
    private Button mBouton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mTextViewCityName =findViewById(R.id.text_view_city_name);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                makeText(v.getContext(), "bouton Favoris", LENGTH_SHORT).show();
            }
        };
        this.mBoutonFavoris =findViewById(R.id.buttonBoutonFavoris);
        this.mBouton1 =findViewById(R.id.buttonBouton1);
        this.mBouton2 =findViewById(R.id.buttonBouton2);
        this.mBouton1.setOnClickListener(onClickListener);
        this.mBouton2.setOnClickListener(onClickListener);
        this.mBoutonFavoris.setOnClickListener(onClickListener);
        mTextViewCityName.setText(R.string.city_name);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            this.mTextConnection = (TextView) findViewById(R.id.text_view_networkstatus);
            this.mLayoutOrange = (LinearLayout) findViewById(R.id.layoutOrange);

            this.mTextConnection.setVisibility(View.VISIBLE);
            this.mTextConnection.setText(R.string.text_about_not_connected);
            this.mBoutonFavoris.setVisibility(View.INVISIBLE);
            this.mLayoutOrange.setVisibility(View.INVISIBLE);
        }
    }

}
