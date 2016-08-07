package rabbidcompany.schoolreportproject.activities;

/**
 * Created by noneemotion on 5/8/2559.
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rabbidcompany.schoolreportproject.R;
import rabbidcompany.schoolreportproject.managers.UserManager;
import rabbidcompany.schoolreportproject.users.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin01;
    private EditText editTextUsername01;
    private EditText editTextPassword01;
    private TextView textViewRegister01;
    private Context loginActivityContext01;
    private UserManager userManager01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        initInstance();
    }

    private void initInstance() {
        buttonLogin01 = (Button) findViewById(R.id.ButtonLoginID01);
        buttonLogin01.setOnClickListener(this);
        editTextUsername01 = (EditText) findViewById(R.id.EditTextUsernameID01);
        editTextPassword01 = (EditText) findViewById(R.id.EditTextPasswordID01);
        textViewRegister01 = (TextView) findViewById(R.id.TextViewRegisterID01);
        textViewRegister01.setOnClickListener(this);
        userManager01 = new UserManager(this);
    }

    private void checkLogin() {
        String username = editTextUsername01.getText().toString().trim();
        String password = editTextPassword01.getText().toString().trim();
        boolean isValidated = userManager01.checkLoginValidate(username, password);

        if(isValidated){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            String message = getString(R.string.login_error_message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin01){
            checkLogin();
        }
        else if(v == textViewRegister01){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}