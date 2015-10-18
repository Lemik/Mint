package com.example.leoniddushin.mint2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.leoniddushin.mint2.File.CSVFile;
import com.example.leoniddushin.mint2.Objects.Coin;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by leoniddushin on 15-02-28.
 */
public class CoinDBHelper extends SQLiteOpenHelper {
    final private static Integer VERSION = 1;
    private static final String COIN_TBL = "Coin";
    final private Context context;

    public CoinDBHelper(Context context) {
        super(context, COIN_TBL, null, VERSION);
        this.context = context;
    }

    private static final String KEY_ID = "_ID_COIN";
    private static final String KEY_FK_collection = "FK_collection_id";
    private static final String KEY_title = "Title";
    private static final String KEY_year = "Year";
    private static final String KEY_mint = "Mint";
    private static final String KEY_count = "Count";
    private static final String KEY_nominal = "Nominal";
    //    private static final String KEY_quantity = "Quantity";
    private static final String KEY_grade = "Grade";
    private static final String KEY_description = "Description";
    private static final String KEY_note = "Note";
    private static final String KEY_imgA = "ImgResIdA";
    private static final String KEY_imgB = "ImgResIdB";

    final private static String CREATE_COIN_TABLE =
            "CREATE TABLE " + COIN_TBL + " ( "
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_FK_collection + " INTEGER, "
                    + KEY_title + " TEXT, "
                    + KEY_year + " TEXT, "
                    + KEY_mint + " NUMERIC, "
                    + KEY_count + " INTEGER, "
                    + KEY_nominal + " TEXT, "
                    + KEY_grade + " INTEGER, "
                    + KEY_description + " TEXT, "
                    + KEY_note + " TEXT, "
                    + KEY_imgA + " TEXT, "
                    + KEY_imgB + " TEXT )";

    public void onCreate(SQLiteDatabase db) {
        if (db.getVersion() != VERSION)
            db.execSQL(CREATE_COIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS " + COIN_TBL);
        // create fresh books table
        this.onCreate(db);
    }

    public void addCoinToCatalog(Coin coin) {
        Log.d("Add Coin", coin.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(KEY_FK_collection, coin.getFK_collection());
        values.put(KEY_title, coin.getTitle());
        values.put(KEY_year, coin.getYear());
        values.put(KEY_mint, coin.getMint());
        values.put(KEY_count, coin.getCount());
        values.put(KEY_nominal, coin.getNominal());
        values.put(KEY_grade, coin.getGrade());
        values.put(KEY_description, coin.getDescription());
        values.put(KEY_note, coin.getNote());
        values.put(KEY_imgA, coin.getImgA());
        values.put(KEY_imgB, coin.getImgB());
        db.insert(COIN_TBL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        db.close();
    }

    private ArrayList<Coin> setCoinProperty(String query) {
        ArrayList<Coin> coins = new ArrayList<Coin>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Coin coin = null;
        if (cursor.moveToFirst()) {
            do {
                coin = new Coin(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11)
                );
                coins.add(coin);
            } while (cursor.moveToNext());
        }
        db.close();
        return coins;
    }

    public ArrayList<Coin> getCoinsArrayListFromCatalogByCollectionID(int ID) {
        Log.d("Get Coin from DB by ID", String.valueOf(ID));
        String query = "SELECT * FROM " + COIN_TBL + " where " + KEY_FK_collection + "='" + ID + "'";
        ArrayList<Coin> collectionArrayList = setCoinProperty(query);
        return collectionArrayList;
    }

    public void addNewCollectionToDB(String collectionName, int CollectionId) {
        addCollectionToDB(collectionName, CollectionId);
    }

    public void addCollectionToDB(String collectionName, int CollectionId) {
        ArrayList<Coin> collectionArrayList;
        int r = context.getResources().getIdentifier(collectionName, "raw", context.getPackageName());
        InputStream inputStream = context.getResources().openRawResource(r);
        CSVFile csvFile = new CSVFile(inputStream);
        collectionArrayList = csvFile.getCoinsFromFile();
        for (int i = 0; i < collectionArrayList.size(); i++) {
            addCoinToCatalog(new Coin(collectionArrayList.get(i).getId(),
                    CollectionId,
                    collectionArrayList.get(i).getTitle(),
                    collectionArrayList.get(i).getYear(),
                    collectionArrayList.get(i).getMint(),
                    collectionArrayList.get(i).getCount(),
                    collectionArrayList.get(i).getNominal(),
                    collectionArrayList.get(i).getGrade(),
                    collectionArrayList.get(i).getDescription(),
                    collectionArrayList.get(i).getNote(),
                    collectionArrayList.get(i).getImgA(),
                    collectionArrayList.get(i).getImgB()));
        }
    }

    public void changeCoinState(int CoinId, int count) {
        Log.d("change Coin State by coin ID  = ", String.valueOf(CoinId));
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues row = new ContentValues();
        row.put(KEY_count, count);
        db.update(COIN_TBL, row, KEY_ID + " = ?", new String[]{String.valueOf(CoinId)});
        db.close();
    }

    public void deleteDatabase() {
        context.deleteDatabase(COIN_TBL);
    }

    public ArrayList<Coin> getCoinsArrayListFromCatalogByYear(int year) {
        Log.d("Get Coin from DB by Year ", String.valueOf(year));
        String query = "SELECT * FROM " + COIN_TBL + " where " + KEY_year + "='" + year + "'";
        ArrayList<Coin> collectionArrayList = setCoinProperty(query);
        return collectionArrayList;
    }

    public ArrayList<Coin> getCoinsArrayListFromCatalogByNominal(float nominal) {
        Log.d("Get Coin from DB by Year ", String.valueOf(nominal));
        String query = "SELECT * FROM " + COIN_TBL + " where " + KEY_nominal + "='" + nominal + "'";
        ArrayList<Coin> collectionArrayList = setCoinProperty(query);
        return collectionArrayList;
    }

}
























