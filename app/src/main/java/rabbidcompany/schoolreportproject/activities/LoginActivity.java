package rabbidcompany.schoolreportproject.activities;

/**
 * Created by noneemotion on 5/8/2559.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rabbidcompany.schoolreportproject.R;
import rabbidcompany.schoolreportproject.managers.UserManager;
import rabbidcompany.schoolreportproject.users.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*********************************************************************************************
     * Variables(s)
     *********************************************************************************************/
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewRegisterPress;
    UserManager userManager;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;
    CheckBox checkBox;
    Handler handler;
    Runnable runnable;
    UserManager mManager;
    User mUser;
    int delayTime;

    /*********************************************************************************************
     * Functions/Methods(s)
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPref = getSharedPreferences("PREF_USER", Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();

        mManager = new UserManager(this);
        mUser = new User();

        //Check whether or not this application runs for the first time.
        if(sharedPref.getBoolean("IS_FIRST_TIME",true)) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.up_from_buttom, R.anim.down_from_top);
            sharedPrefEditor.putBoolean("IS_FIRST_TIME", false).commit();
            delayTime = 3000;
        }
        else{
            delayTime = 0;
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn(sharedPref) == true) {
                    startMainActivity();
                    finish();
                }
                else {
                    setContentView(R.layout.activity_login);
                    initInstance();
                }
            }
        };
        handler.postDelayed(runnable, delayTime);
    }

    private void initInstance() {
        editTextEmail = (EditText) findViewById(R.id.EditTextEmailLogInID01);
        editTextPassword = (EditText) findViewById(R.id.EditTextPasswordLogInID01);
        buttonLogin = (Button) findViewById(R.id.ButtonLoginID01);
        buttonLogin.setOnClickListener(this);
        textViewRegisterPress = (TextView) findViewById(R.id.TextViewRegisterID02);
        textViewRegisterPress.setOnClickListener(this);
        userManager = new UserManager(this);
        checkBox = (CheckBox) findViewById(R.id.CheckBoxLoginID01);
        checkBox.setOnClickListener(this);

        boolean isRemember = sharedPref.getBoolean("REMEMBER_EMAIL", false); //Remember Email or not?
        checkBox.setChecked(isRemember);

        if (isRemember) {
            editTextEmail.setText(sharedPref.getString("EMAIL", ""));
        }
    }

    private boolean isLoggedIn(SharedPreferences sharedPref) {
        return sharedPref.getBoolean("IS_LOGGED_IN", false);
    }

    private void checkLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        User user = new User(email, password);
        User validateUser = mManager.checkUserLogin(user);

        if(validateUser == null){
            showToast(getString(R.string.login_error_message));
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(User.Column.EMAIL, validateUser.getEmail());
            intent.putExtra(User.Column.ID, validateUser.getId());
            intent.putExtra(User.Column.PASSWORD, validateUser.getPassword());
            startActivity(intent);
            finish();
        }

        /*boolean isValidated = userManager.checkLoginValidate(email, password);

        if (isValidated) {
            sharedPrefEditor.putBoolean("IS_LOGGED_IN", true).commit(); //Record the logged in status.
            startMainActivity();
            finish();
        } else {
            DialogFragment dialogFragment01 = new InvalidInfoDialogFragment();
            dialogFragment01.show(getFragmentManager(), "InvalidInfo");
        }*/
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /*********************************************************************************************
     * Listener(s)
     *********************************************************************************************/
    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            checkLogin();
        }
        else if (v == checkBox){
            if(checkBox.isChecked()){
                sharedPrefEditor.putBoolean("REMEMBER_EMAIL", true);
                sharedPrefEditor.putString("EMAIL", editTextEmail.getText().toString());
                sharedPrefEditor.commit();
            }
            else if(!checkBox.isChecked()){
                sharedPrefEditor.putBoolean("REMEMBER_EMAIL", false);
                sharedPrefEditor.putString("EMAIL", "");
                sharedPrefEditor.commit();
            }
        }
        else if (v == textViewRegisterPress) {
            startRegisterActivity();
        }
    }

    /*********************************************
     * checkBox.setOnCheckedChangeListener(this);
     *********************************************/
    /* //This one does not work well because the checkBox must be pressed before entering the email.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sharedPrefEditor.putBoolean("REMEMBER_EMAIL", isChecked); //isCheck is the new state of buttonView.
        sharedPrefEditor.putString("EMAIL", editTextEmail.getText().toString());
        //sharedPrefEditor.commit();
    }*/
}