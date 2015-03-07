package com.example.leoniddushin.mint2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.leoniddushin.mint2.Objects.Coin;

/**
 * Created by leoniddushin on 15-02-28.
 */
public class CoinDBHelper extends SQLiteOpenHelper {
    final private static Integer VERSION = 1;
    private static final String COIN_TBL = "Coin";
    final private Context context;

    public CoinDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, COIN_TBL, null, VERSION);
        this.context = context;
    }

    private static final String KEY_ID = "id";
    private static final String KEY_FK_collection = "FK_collection_id";
    private static final String KEY_title = "title";
    private static final String KEY_year = "year";
    private static final String KEY_mint = "mint";
    private static final String KEY_count = "mint";
    private static final String KEY_nominal = "nominal";
    private static final String KEY_quantity = "quantity";
    private static final String KEY_grade = "grade";
    private static final String KEY_description = "description";
    private static final String KEY_note = "note";
    private static final String KEY_imgA = "imgResId";
    private static final String KEY_imgB = "imgResId";

    final private static String CREATE_COIN_TABLE =
            "CREATE TABLE " + COIN_TBL + " ( "
                    + KEY_ID + "	INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_FK_collection + "	INTEGER, "
                    + KEY_title + " TEXT, "
                    + KEY_year + " TEXT, "
                    + KEY_mint + " NUMERIC, "
                    + KEY_count + " INTEGER, "
                    + KEY_nominal + " TEXT, "
                    + KEY_grade + " INTEGER, "
                    + KEY_description + "TEXT, "
                    + KEY_note + "	TEXT, "
                    + KEY_imgA + " TEXT "
                    + KEY_imgA + " TEXT "
                    + ")";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COIN_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ COIN_TBL);
        // create fresh books table
        this.onCreate(db);
    }

    public void addCoinToCatalog(Coin coin){
        Log.d("Add Coin", coin.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FK_collection, coin.getFK_collection());
        values.put(KEY_title,coin.getTitle());
        values.put(KEY_year,coin.getYear());
        values.put(KEY_mint,coin.getMint());
        values.put(KEY_count,coin.getCount());
        values.put(KEY_nominal,coin.getNominal());
        values.put(KEY_grade,coin.getGrade());
        values.put(KEY_description,coin.getDescription());
        values.put(KEY_note,coin.getNote());
        values.put(KEY_imgA,coin.getImgA());
        values.put(KEY_imgB,coin.getImgB());
        db.insert(COIN_TBL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        db.close();
    }

    public void addCoinToCallection(){}


}
