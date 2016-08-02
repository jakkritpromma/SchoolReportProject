package rabbidcompany.schoolreportproject;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by noneemotion on 1/8/2559.
 */
public class SchoolReportApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //You can do this in any activity, but do this in the application class
        //in order to make sure that it works for all codes.
        Firebase.setAndroidContext(this);
    }
}
