package com.example.networks.marshmallowapp.friends;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Networks on 4/18/2017.
 */
/*SQLiteHelpter :
A helper class to manage database creation and version management.
You create a subclass implementing onCreate(SQLiteDatabase), onUpgrade(SQLiteDatabase, int, int) and optionally onOpen(SQLiteDatabase), and this class takes care of opening the database if it exists, creating it if it does not, and upgrading it as necessary. Transactions are used to make sure the database is always in a sensible state.*/
public class FriendsDatabase extends SQLiteOpenHelper {
    private static final String TAG = FriendsDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 2;
    private final Context mContext;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Tables.FRIENDS +" ( "
        + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
        + FriendsContract.FriendsColumns.FRIENDS_NAME+" TEXT NOT NULL,"
        + FriendsContract.FriendsColumns.FRIENDS_EMAIL+" TEXT NOT NULL,"
        + FriendsContract.FriendsColumns.FRIENDS_PHONE+" TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if (version == 1) {
            //Add some extra fields to the database without deleting exisiting data
            version =2;
        }

        if (version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS "+ Tables.FRIENDS);
            onCreate(db);
        }

    }

    interface Tables{
        String FRIENDS ="friends";
    }

    public FriendsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
