package rabbidcompany.schoolreportproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by noneemotion on 5/8/2559.
 */
public class UserManager {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public UserManager(Context context) {
        mPrefs = context.getSharedPreferences("PREF_USER", Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }

    public boolean checkLoginValidate(String username, String password) {
        String realUsername = mPrefs.getString("EMAIL", "");
        String realPassword = mPrefs.getString("PASSWORD", "");

        if ( (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) &&
                username.equals(realUsername) && password.equals(realPassword)) {
            return true; //Validated case
        }

        return false; //Invalidated case
    }

    public boolean registerUser(String email, String password) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return false;
        }

        //Record a user into the SharedPreference.
        mEditor.putString("EMAIL", email);
        mEditor.putString("PASSWORD", password);
        return mEditor.commit();
    }
}