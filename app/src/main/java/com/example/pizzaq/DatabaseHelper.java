package com.example.pizzaq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydatabase.db";
    private static final int DB_VERSION = 1;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;

        try {
            if (mContext.getAssets().open(DB_NAME) != null) Log.d("some", "file found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method is not used, as we are copying a pre-existing database file from the assets folder
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is not used, as we are not changing the database schema
    }

    public void createDatabase() {
        // This method is not used, as we are copying a pre-existing database file from the assets folder
        try {
            // Open an InputStream to the asset representing the pre-made database
            InputStream input = mContext.getAssets().open(DB_NAME);

            // Get the path to the app's private storage for databases
            String dbPath = mContext.getDatabasePath(DB_NAME).getPath();;

            // Open an OutputStream to the app's private storage for databases
            OutputStream output = new FileOutputStream(dbPath);

            // Copy the database file from the assets folder to the app's private storage
            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer))>0){
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
