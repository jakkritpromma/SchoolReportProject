package rabbidcompany.schoolreportproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import rabbidcompany.schoolreportproject.R;

/**
 * Created by noneemotion on 5/8/2559.
 */
public class SplashScreenActivity extends Activity {

    Handler handler;
    Runnable runnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        runnable = new Runnable(){
            @Override
            public void run() {
                finish();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        //overridePendingTransition(R.anim.down_from_top, R.anim.up_from_buttom);
        return super.onCreateView(name, context, attrs);
    }
}
