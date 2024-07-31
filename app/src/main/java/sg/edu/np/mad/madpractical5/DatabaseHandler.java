package sg.edu.np.mad.madpractical5;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    private static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_FOLLOWED = "followed";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_FOLLOWED + " BOOLEAN)";
        db.execSQL(CREATE_USERS_TABLE);

        for (int i = 1; i <= 20; i++) {
            String name = "Name" + Math.round(Math.random() * 100000000);
            String desc = "Description" + Math.round(Math.random() * 100000000);
            int randomNum = new Random().nextInt(2);
            String followed = "false";
            if (randomNum == 1)
                followed = "true";
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_DESCRIPTION, desc);
            values.put(COLUMN_FOLLOWED, followed);
            db.insert(TABLE_USERS, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_FOLLOWED, user.getFollowed());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_USERS + " ;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean follow = false;

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            if (cursor.getInt(3) == 1){
                follow = true;
            }
            User user = new User(name, desc, id, follow);
            users.add(user);
        }
        return users;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOLLOWED,user.getFollowed());
        db.update(TABLE_USERS,values,COLUMN_ID + "=?", new String[] {String.valueOf(user.getId())});
    }

    public User getUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        User user = new User("", "", 1,true);
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            user.setName(cursor.getString(1));
            user.setDescription(cursor.getString(2));
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setFollowed(Boolean.parseBoolean(cursor.getString(3)));
            cursor.close();
        }
//      db.close();
        return user;
    }
}