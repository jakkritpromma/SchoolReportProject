package rabbidcompany.schoolreportproject.activities;

/**
 * Created by noneemotion on 5/8/2559.
 */

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import rabbidcompany.schoolreportproject.R;
import rabbidcompany.schoolreportproject.fragments.InvalidInfoDialogFragment;
import rabbidcompany.schoolreportproject.managers.UserManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /*********************************************************************************************
     *Variables(s)
     *********************************************************************************************/
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegisterPress;
    private UserManager userManager;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private CheckBox checkBox;

    /*********************************************************************************************
     *Functions/Methods(s)
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        initInstance();
    }

    private void initInstance() {
        editTextEmail = (EditText) findViewById(R.id.EditTextEmailLogInID01);
        editTextPassword = (EditText) findViewById(R.id.EditTextPasswordLogInID01);

        buttonLogin = (Button) findViewById(R.id.ButtonLoginID01);
        buttonLogin.setOnClickListener(this);

        textViewRegisterPress = (TextView) findViewById(R.id.TextViewRegisterID02);
        textViewRegisterPress.setOnClickListener(this);

        userManager = new UserManager(this);
        sharedPref = getSharedPreferences("PREF_USER", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        checkBox = (CheckBox) findViewById(R.id.CheckBoxLoginID01);
        checkBox.setOnCheckedChangeListener(this);

        boolean isRemember = sharedPref.getBoolean("REMEMBER_EMAIL", false); //Remember Email or not?
        checkBox.setChecked(isRemember);

        if(isRemember){
            editTextEmail.setText(sharedPref.getString("EMAIL", ""));
        }
    }

    private void checkLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        boolean isValidated = userManager.checkLoginValidate(email, password);

        if (isValidated) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            DialogFragment dialogFragment01 = new InvalidInfoDialogFragment();
            dialogFragment01.show(getFragmentManager(), "InvalidInfo");
        }
    }

    /*********************************************************************************************
     *Listener(s)
     *********************************************************************************************/
    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            checkLogin();
        } else if (v == textViewRegisterPress) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    /*********************************************
     *checkBox.setOnCheckedChangeListener(this);
     *********************************************/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        editor.putBoolean("REMEMBER_EMAIL", isChecked); //isCheck is the new state of buttonView.
        editor.commit();
    }
}