package rabbidcompany.schoolreportproject.users;

import android.provider.BaseColumns;

/**
 * Created by noneemotion on 5/8/2559.
 */
public class User {

    public static final String TABLE = "user";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

    private int id;
    private String email;
    private String password;

    //Constructor
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(){

    }

    public static String getTABLE() {
        return TABLE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
