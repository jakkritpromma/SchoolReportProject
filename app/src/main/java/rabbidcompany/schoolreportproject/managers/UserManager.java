package rabbidcompany.schoolreportproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import rabbidcompany.schoolreportproject.users.User;

/**
 * Created by noneemotion on 5/8/2559.
 */
public class UserManager extends SQLiteOpenHelper implements UserManagerHelper {

    //SharedPreferences mPrefs;
    //SharedPreferences.Editor mEditor;
    public static final String TAG = UserManager.class.getSimpleName();
    private SQLiteDatabase mDatabase;

    public UserManager(Context context) {
        //mPrefs = context.getSharedPreferences("PREF_USER", Context.MODE_PRIVATE);
        //mEditor = mPrefs.edit();
        //super(context, string name, CursorFactory factory, int version);
        super(context, UserManagerHelper.DATABASE_NAME, null, UserManagerHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = String.format(
                "CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                User.TABLE,
                User.Column.ID,
                User.Column.EMAIL,
                User.Column.PASSWORD
        );
        db.execSQL(CREATE_TABLE_USER);
        Log.i(TAG, CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USER = "DROP TABLE IF EXISTS " + UserManagerHelper.DATABASE_VERSION;
        db.execSQL(DROP_USER);
        Log.i(TAG, DROP_USER);
        onCreate(mDatabase);
    }

    //Implemented method #1
    @Override
    public long registerUser(User user) {
        mDatabase = this.getWritableDatabase(); //Open the database for writing data.

        ContentValues values = new ContentValues(); //ContentValues can store values.
        values.put(User.Column.EMAIL, user.getEmail());
        values.put(User.Column.PASSWORD, user.getPassword());

        long result = mDatabase.insert(User.TABLE, null, values); //Insert a row into the database.
        mDatabase.close();

        //return 0;
        return result; //Return row's ID or -1 if any error occurs.
    }

    //Implemented method #2
    @Override
    public User checkUserLogin(User user) {
        mDatabase = this.getWritableDatabase();
        Cursor cursor = mDatabase.query(
                User.TABLE,
                null,
                User.Column.EMAIL + " = ? AND " + User.Column.PASSWORD + " = ?",
                new String[]{user.getEmail(), user.getPassword()},
                null,
                null,
                null);

        User currentUser = new User();

        if(cursor != null){
            if(cursor.moveToFirst()){ //Move to the first row
                currentUser.setId((int) cursor.getLong(0)); //Column 0
                currentUser.setEmail(cursor.getString(1)); //Column 1
                currentUser.setPassword(cursor.getString(2));
                mDatabase.close();
                return currentUser;
            }
        }
        return null;
    }

    //Implemented method #3
    @Override
    public int changePassword(User user) {
        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.Column.EMAIL, user.getEmail());
        values.put(User.Column.PASSWORD, user.getPassword());

        int row = mDatabase.update(
                User.TABLE,
                values,
                User.Column.ID + " = ?",
                new String[] {String.valueOf(user.getId())});

        mDatabase.close();
        //return 0;
        return row;
    }

    /*
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
    }*/
}