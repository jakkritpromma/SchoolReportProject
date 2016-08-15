package rabbidcompany.schoolreportproject.activities;

/**
 * Created by noneemotion on 5/8/2559.
 */

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rabbidcompany.schoolreportproject.R;
import rabbidcompany.schoolreportproject.managers.UserManager;
import rabbidcompany.schoolreportproject.users.User;

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener, TextWatcher {

    /*********************************************************************************************
     * *Variables(s)
     *********************************************************************************************/
    ImageView imageViewBack;
    EditText editTextEmail, editTextPassword;
    TextView textViewPasswordStrength;
    EditText editTextConfirmPassword;
    Button buttonRegister;
    UserManager mManager;

    /*********************************************************************************************
     * *Functions/Methods(s)
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register);

        mManager = new UserManager(this);
        editTextEmail = (EditText) findViewById(R.id.EditTextEmailRegID01);
        editTextPassword = (EditText) findViewById(R.id.EditTextPasswordRegID01);
        editTextPassword.addTextChangedListener(this);
        textViewPasswordStrength = (TextView) findViewById(R.id.TextViewPasswordStrengthID01);
        editTextConfirmPassword = (EditText) findViewById(R.id.EditTextConfirmPasswordRegID02);
        buttonRegister = (Button) findViewById(R.id.ButtonRegisterID01);
        buttonRegister.setOnClickListener(this);
        imageViewBack = (ImageView) findViewById(R.id.ImageViewBackRegID01);
        imageViewBack.setOnClickListener(this);
    }

    public boolean isValidEmail(String givenEmail) {
        if (givenEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(givenEmail).matches();
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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

    /*********************************************************************************************
     * *Listeners(s)
     *********************************************************************************************/
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if (isValidEmail(email) == false) {
                showToast(getString(R.string.incorrect_email_form));
            } else {
                //Check the password and the confirmed password.

                if (password.equals(confirmPassword)) {
                    User user = new User(email, password);
                    long rowId = mManager.registerUser(user);

                    if(rowId == -1){
                        showToast(getString(R.string.register_error_messages));
                    }
                    else{
                        showToast(getString(R.string.register_success));
                        finish();
                    }

                    /*boolean isSuccess = userManager.registerUser(email, password);

                    if (isSuccess) {
                        showToast(getString(R.string.register_success));
                        finish(); //Finish this activity.
                    } else {
                        showToast(getString(R.string.register_error_messages));
                    }*/

                } else {
                    showToast(getString(R.string.register_password_error));
                }
            }
        }
        if(v == imageViewBack){
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String givenPassword = s.toString();
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
            textViewPasswordStrength.setText(getResources().getString(R.string.weak_password));
            textViewPasswordStrength.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (numberOfConditions == 1) {
            textViewPasswordStrength.setText(getResources().getString(R.string.normal_password));
            textViewPasswordStrength.setTextColor(getResources().getColor(R.color.colorYellow));
        } else if (numberOfConditions == 2) {
            textViewPasswordStrength.setText(getResources().getString(R.string.strong_password));
            textViewPasswordStrength.setTextColor(getResources().getColor(R.color.colorGreen));
        }
    }
}
