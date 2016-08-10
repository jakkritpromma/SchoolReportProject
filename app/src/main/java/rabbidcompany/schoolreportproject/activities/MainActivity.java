package rabbidcompany.schoolreportproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import rabbidcompany.schoolreportproject.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*********************************************************************************************
     * *Variable(s)
     *********************************************************************************************/
    Toolbar toolBarMainActivity;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imageViewBack;
    LinearLayout linearLayoutLogOut;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;

    /*********************************************************************************************
     * *Method/Functions(s)
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPref = getSharedPreferences("PREF_USER", Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();

        setContentView(R.layout.activity_main);
        initInstance();
    }

    private void initInstance() {
        toolBarMainActivity = (Toolbar) findViewById(R.id.ToolBarActivityMainID01);
        setSupportActionBar(toolBarMainActivity); //Tell this activity to use this Toolbar.

        //Init the hamburger button linking to the drawerLayout
        //It needs the codes in onPostCreate and onConfigurationChanged
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayoutID01);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true); //Enable the home button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Go back a single level.
        actionBarDrawerToggle.syncState(); //You cannot follow the document for this case because it is null exception.

        linearLayoutLogOut = (LinearLayout) findViewById(R.id.LinearLayoutLogoutID01);
        linearLayoutLogOut.setOnClickListener(this);
        imageViewBack = (ImageView) findViewById(R.id.ImageViewBackID01);
        imageViewBack.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //Move the below code to initInstance in onCreate because it is null exception.
        //actionBarDrawerToggle.syncState()
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //For the hamburger button
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //For the hamburger button
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /*********************************************************************************************
     * *Listeners(s)
     *********************************************************************************************/
    @Override
    public void onClick(View v) {
        if (v == linearLayoutLogOut) {
            sharedPrefEditor.putBoolean("IS_LOGGED_IN", false).commit();
            finish();
            startLoginActivity();
        }
        else if(v == imageViewBack){
            drawerLayout.closeDrawers();
        }
    }
}
