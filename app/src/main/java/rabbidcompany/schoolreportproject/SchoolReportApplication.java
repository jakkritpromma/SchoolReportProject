package rabbidcompany.schoolreportproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;

import io.fabric.sdk.android.Fabric;

/**
 * Created by noneemotion on 1/8/2559.
 */
public class SchoolReportApplication extends Application {

    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sharedPref = getSharedPreferences("PREF_USER", Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();
        sharedPrefEditor.putBoolean("IS_FIRST_TIME", true).commit();
        //You can do this in any activity, but do this in the application class
        //in order to make sure that it works for all codes.
        Firebase.setAndroidContext(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
