package com.github.menf.androiddb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menf on 2016-12-15.
 */

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Context context;

    private static final String DEBUG_TAG = "SqLiteDatabase";

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "database.db";
    private static final String DB_TABLE = "items";


    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_DESCRIPTION = "description";
    public static final String DESCRIPTION_OPTIONS = "INTEGER NOT NULL";
    public static final int DESCRIPTION_COLUMN = 1;
    public static final String KEY_NAME = "name";
    public static final String NAME_OPTIONS = "INTEGER NOT NULL";
    public static final int NAME_COLUMN = 2;
    public static final String KEY_IMAGE = "image";
    public static final String IMAGE_OPTIONS = "INTEGER NOT NULL";
    public static final int IMAGE_COLUMN = 3;


    private static final String DB_CREATE_ITEMS_TABLE =
            "CREATE TABLE " + DB_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
                    KEY_NAME + " " + NAME_OPTIONS + ", " +
                    KEY_IMAGE + " " + IMAGE_OPTIONS +
                    ");";
    private static final String DROP_ITEMS_TABLE =
            "DROP TABLE IF EXISTS " + DB_TABLE;

    public Database(Context context) {
        super(context, DB_TABLE, null, DB_VERSION);
    }

    // Adding new item
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.get_name()); // Item Name
        values.put(KEY_DESCRIPTION, item.get_description()); // Item Desc
        values.put(KEY_IMAGE, item.get_image()); // Item Image

        // Inserting Row
        db.insert(DB_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single item
    public Item getItem(int id) {
        // Getting single contact
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION , KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
        // return item
        return item;
    }

    // Getting all items
    public List<Item> getAllItems() {
        // Getting All Contacts
        List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DB_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.set_id(Integer.parseInt(cursor.getString(0)));
                item.set_description(Integer.parseInt(cursor.getString(1)));
                item.set_name(Integer.parseInt(cursor.getString(2)));
                item.set_image(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return itemList;
    }

    // Getting items count
    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single contact
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.get_name()); // Item Name
        values.put(KEY_DESCRIPTION, item.get_description()); // Item Desc
        values.put(KEY_IMAGE, item.get_image()); // Item Image

        // updating row
        return db.update(DB_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.get_id()) });
    }

    // Deleting single contact
    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(item.get_id()) });
        db.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_ITEMS_TABLE);

        Log.d(DEBUG_TAG, "Database creating...");
        Log.d(DEBUG_TAG, "Table " + DB_TABLE + " ver." + DB_VERSION + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ITEMS_TABLE);

        Log.d(DEBUG_TAG, "Database updating...");
        Log.d(DEBUG_TAG, "Table " + DB_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");

        onCreate(db);
    }
}

