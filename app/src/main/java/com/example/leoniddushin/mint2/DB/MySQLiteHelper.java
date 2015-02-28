package com.example.leoniddushin.mint2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.leoniddushin.mint2.File.CSVFile;
import com.example.leoniddushin.mint2.Objects.Collection;
import com.example.leoniddushin.mint2.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MySQLiteHelper extends SQLiteOpenHelper{
    final private static Integer VERSION = 1;
    final private Context context;

    public MySQLiteHelper(Context context) {
        super(context, COLLECTION_TBL, null, VERSION);
        this.context = context;
    }
    /////////////////Collections TBL
    static final String COLLECTION_TBL = "Collections";
    static final String KEY_ID_COLLECTION = "_ID_COLLECTION";
    static final String KEY_COLLECTION_NAME = "CollectionName";
    static final String KEY_COUNT = "Count";
    static final String KEY_Country = "Country";
    static final String KEY_Belongs = "Belongs";
    static final String KEY_IMG ="img";
    private static final String[] COLLECTION_COLUMNS = {KEY_ID_COLLECTION,KEY_COLLECTION_NAME,KEY_COUNT};

    final private static String CREATE_COLLECTION_TABLE =
            "CREATE TABLE " + COLLECTION_TBL + " ( "
                    + KEY_ID_COLLECTION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_COLLECTION_NAME + " TEXT, "
                    + KEY_COUNT + " INTEGER, "
                    + KEY_Country + " TEXT, "
                    + KEY_Belongs + " INTEGER DEFAULT 0, "
                    + KEY_IMG +" TEXT )";



    ////////////////Version TBL
    static final String VERSION_TBL = "VershenTBL";
    final private static String CREATE_MINT_VERSION =
        "CREATE TABLE "  + VERSION_TBL + "(Version INTEGER)";
        

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.getVersion() != VERSION)
            db.execSQL(CREATE_COLLECTION_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ COLLECTION_TBL);

        // create fresh books table
        this.onCreate(db);
    }

    public void addCollection(Collection collection){
        Log.d("Add Collection", collection.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_COLLECTION_NAME, collection.getName());
        values.put(KEY_COUNT, collection.getCount());
        values.put(KEY_Country, collection.getCountry());
        values.put(KEY_Belongs, collection.getBelongings());
        values.put(KEY_IMG,collection.getImg());
        // 3. insert
        db.insert(COLLECTION_TBL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    public ArrayList<Collection> getAllCollection() {
        String query = "SELECT * FROM " + COLLECTION_TBL;
        return setCollectionProperty(query);
    }

    private ArrayList<Collection> setCollectionProperty(String query) {
        ArrayList<Collection> collections = new ArrayList<Collection>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Collection collection = null;
        if (cursor.moveToFirst()) {
            do {
                collection = new Collection(
                           Integer.parseInt(cursor.getString(0)),
                                            cursor.getString(1),
                           Integer.parseInt(cursor.getString(2)),
                                            cursor.getString(3),
                           Integer.parseInt(cursor.getString(4)),
                                            cursor.getString(5)
                                            );
                collections.add(collection);
            } while (cursor.moveToNext());
        }
        return collections;
    }

    public ArrayList<Collection> getCollectinsByCountry(String countryName){
        String query = "SELECT * FROM " + COLLECTION_TBL + " where Country='"+countryName+"'";
        ArrayList<Collection> collectionArrayList = setCollectionProperty(query);
        if(collectionArrayList.size()==0) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.collections);
            CSVFile csvFile = new CSVFile(inputStream);
            //collectionArrayList = csvFile.getCollections();

            collectionArrayList = csvFile.putCollectionsToDB();
            Iterator<Collection> it = collectionArrayList.iterator();

            for(int i=0; i<collectionArrayList.size(); i++){
                addCollection(new Collection(collectionArrayList.get(i).getId(),
                        collectionArrayList.get(i).getName(),
                        collectionArrayList.get(i).getCount(),
                        collectionArrayList.get(i).getCountry(),
                        collectionArrayList.get(i).getBelongings(),
                        collectionArrayList.get(i).getImg()));
            }
        }
        collectionArrayList = setCollectionProperty(query);
    return  collectionArrayList;
    }

    public ArrayList<String> getListOfCountries(){
        String query = "SELECT distinct " + KEY_Country + " FROM " + COLLECTION_TBL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> country= new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String str = cursor.getString(0);
                country.add(str);
            } while (cursor.moveToNext());
        }
        return country ;
    }

    public int getNumberOfCollections(){
        String query = "SELECT * FROM " + COLLECTION_TBL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public void deleteDatabase() {
        context.deleteDatabase(COLLECTION_TBL);
    }

}
