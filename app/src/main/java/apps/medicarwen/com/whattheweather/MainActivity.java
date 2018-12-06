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

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewCityName;
    private TextView mTextConnection;
    private LinearLayout mLayoutOrange;
    private Button mBoutonFavoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mTextViewCityName=(TextView)findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connManager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnected())
        {
            //connecté au net
            Log.d("TAG","connecté");


        }
        else
        {
            //not connecté
            Log.d("tag","non connecté");

            this.mTextConnection=(TextView)findViewById(R.id.text_view_networkstatus);
            this.mBoutonFavoris=(Button)findViewById(R.id.buttonBoutonFavoris);
            this.mLayoutOrange=(LinearLayout)findViewById(R.id.layoutOrange);

            this.mTextConnection.setVisibility(View.VISIBLE);
            this.mTextConnection.setText(R.string.text_about_not_connected);
            this.mBoutonFavoris.setVisibility(View.INVISIBLE);
            this.mLayoutOrange.setVisibility(View.INVISIBLE);
        }
    }

    public void actionClickBouton1(View view) {
        //Toast.makeText(this, "YOUPLABOOM", Toast.LENGTH_SHORT).show();
    }
}
