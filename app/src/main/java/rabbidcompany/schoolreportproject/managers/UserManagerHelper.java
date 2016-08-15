package rabbidcompany.schoolreportproject.managers;

import rabbidcompany.schoolreportproject.users.User;

/**
 * Created by noneemotion on 5/8/2559.
 */
public interface UserManagerHelper {
    public static final String DATABASE_NAME = "sqliteDatabaseName";
    public static final int DATABASE_VERSION = 1;

    /**Save users' data into the database.
     * If the data is recorded successfully, then return row ID.
     * If any error occurs, then return -1.
     * */
    public long registerUser(User user);

    /**Check whether email and password exist in the database or not?
     * In other words, this is a query.
     * Otherwise, return null.
     */
    public User checkUserLogin(User user);

    /**
     * Change password by querying the data before updating the data.
     * Return the number of the updated row.
     */
    public int changePassword(User user);
}
