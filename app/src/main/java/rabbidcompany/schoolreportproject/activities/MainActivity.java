package rabbidcompany.schoolreportproject.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import rabbidcompany.schoolreportproject.R;
import rabbidcompany.schoolreportproject.managers.UserManager;
import rabbidcompany.schoolreportproject.users.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*********************************************************************************************
     * *Variable(s)
     *********************************************************************************************/
    Toolbar toolBarMainActivity;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imageViewBack;
    LinearLayout linearLayoutLogOut, linearLayoutChangePassword;
    UserManager mManager;
    User mUser;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;
    Intent intent;
    RegisterActivity registerActivity;

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
        linearLayoutChangePassword = (LinearLayout) findViewById(R.id.LinearLayoutChangePasswordID01);
        linearLayoutChangePassword.setOnClickListener(this);
        imageViewBack = (ImageView) findViewById(R.id.ImageViewBackID01);
        imageViewBack.setOnClickListener(this);

        mManager = new UserManager(this);
        mUser = new User();
        Bundle args = getIntent().getExtras();
        if (args == null) {
            showToast(getString(R.string.welcome_error_message));
            finish();
        } else {
            mUser.setId(args.getInt(User.Column.ID));
            mUser.setEmail(args.getString(User.Column.EMAIL));
        }
        intent = getIntent(); //Return the intent starting this activity.
        registerActivity = new RegisterActivity();
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

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void showDialogPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_change_password_dialog, null);
        builder.setView(view);
        final EditText editTextCurrentPassword = (EditText) view.findViewById(R.id.EditTextCurrentPasswordID01);
        final EditText editTextNewPassword = (EditText) view.findViewById(R.id.EditTextNewPasswordID01);
        editTextNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* String givenPassword = s.toString();
                int numberOfConditions = 0;

                if (givenPassword.length() < 8) {
                    numberOfConditions--;
                }
                if (isUpperCaseFound(givenPassword) && isLowerCaseFound(givenPassword)) {
                    numberOfConditions++;
                }
                if (isDigitFound(givenPassword)) {
                    numberOfConditions++;
                }
                if (numberOfConditions <= 0) {
                    editTextNewPassword.setText(getResources().getString(R.string.weak_password));
                    editTextNewPassword.setTextColor(getResources().getColor(R.color.colorRed));
                } else if (numberOfConditions == 1) {
                    editTextNewPassword.setText(getResources().getString(R.string.normal_password));
                    editTextNewPassword.setTextColor(getResources().getColor(R.color.colorYellow));
                } else if (numberOfConditions == 2) {
                    editTextNewPassword.setText(getResources().getString(R.string.strong_password));
                    editTextNewPassword.setTextColor(getResources().getColor(R.color.colorGreen));
                }*/
            }
        });

        //The positive button is clicked.
        builder.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String currentPassword = editTextCurrentPassword.getText().toString();
                        String newPassword = editTextNewPassword.getText().toString();
                        String shownString = getString(R.string.failed) + ":";
                        Bundle bundle = intent.getExtras();

                        //Case 1: No password given.
                        if (TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword)) {
                            shownString = shownString + " " + getString(R.string.please_input_passwords);
                            showToast(shownString);

                        }//Case 2: The current password is NOT given, but the new password is given.
                        else if (TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(newPassword)) {
                            shownString = shownString + " " + getString(R.string.please_input_old_password);
                            showToast(shownString);

                        }//Case 3: The current password is given, but the new password is NOT given.
                        else if (!TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword)) {

                            if (!isCurrentPasswordCorrect(currentPassword, bundle)) {
                                shownString = shownString + " " + getString(R.string.current_password_incorrect);
                               // shownString = shownString + " " + "Current Password: " + bundle.getString("password");
                            }
                            shownString = shownString + " " + getString(R.string.please_input_new_password);
                            showToast(shownString);

                        }//Case 4: All two passwords are given.
                        else if (!TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(newPassword)) {

                            if (!isCurrentPasswordCorrect(currentPassword, bundle)) {
                                shownString = shownString + " " + getString(R.string.current_password_incorrect);
                            }
                            if (currentPassword.equals(newPassword)) {
                                shownString = shownString + " " + getString(R.string.current_and_old_passwords_must_be_different);
                            }
                            if(isCurrentPasswordCorrect(currentPassword, bundle) && !currentPassword.equals(newPassword)){
                                mUser.setPassword(newPassword);
                                mManager.changePassword(mUser); //Update the database.
                                intent.putExtra("password", newPassword); //Update the intent too in order to prevent any error.
                                shownString = getString(R.string.successfully_change_password);
                                //goToLogin();
                            }
                            showToast(shownString);
                        }
                    }
                }
        );
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }

    public Boolean isCurrentPasswordCorrect(String currentPassword, Bundle bundle) {
        if (currentPassword.equals(bundle.getString("password"))){
            return true;
        }
        return false;
    }

    private boolean isUpperCaseFound(String givenPassword) {
        for (int i = 0; i < givenPassword.length(); i++) {
            if (Character.isUpperCase(givenPassword.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isLowerCaseFound(String givenPassword) {
        for (int i = 0; i < givenPassword.length(); i++) {
            if (Character.isLowerCase(givenPassword.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDigitFound(String givenPassword) {
        for (int i = 0; i < givenPassword.length(); i++) {
            if (Character.isDigit(givenPassword.charAt(i))) {
                return true;
            }
        }
        return false;
    }
/*
    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
*/

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
        if (v == linearLayoutChangePassword) {
            showDialogPassword();
        } else if (v == imageViewBack) {
            drawerLayout.closeDrawers();
        }
    }
}
