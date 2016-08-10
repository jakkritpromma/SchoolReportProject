package rabbidcompany.schoolreportproject.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import rabbidcompany.schoolreportproject.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBarMainActivity01;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.up_from_buttom, R.anim.down_from_top);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                //Check

                setContentView(R.layout.activity_main);
                initInstance();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void initInstance() {
        toolBarMainActivity01 = (Toolbar) findViewById(R.id.ToolBarActivityMainID02);
        setSupportActionBar(toolBarMainActivity01); //Tell this activity to use this Toolbar.

    }

}
