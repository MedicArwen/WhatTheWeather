package apps.medicarwen.com.whattheweather;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WTF", "onCreate: Favorite Activity");
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

