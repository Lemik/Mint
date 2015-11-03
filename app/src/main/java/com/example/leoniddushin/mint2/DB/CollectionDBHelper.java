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


public class CollectionDBHelper extends SQLiteOpenHelper{
    final private static Integer VERSION = 1;
    final private Context context;
    private SQLiteDatabase _db = null;

    public CollectionDBHelper(Context context) {
        super(context, COLLECTION_TBL, null, VERSION);
        this.context = context;
    }
    /////////////////Collections TBL
    static final String COLLECTION_TBL = "Collections";
    static final String KEY_ID_COLLECTION = "_ID_COLLECTION";
    static final String KEY_COLLECTION_NAME = "CollectionName";
    static final String KEY_COLLECTION_TITLE = "Title";
    static final String KEY_COUNT = "Count";
    static final String KEY_Country = "Country";
    static final String KEY_Belongs = "Belongs";
    static final String KEY_IMG ="img";
    static final String KEY_LOCK ="lock";

    private static final String[] COLLECTION_COLUMNS = {KEY_ID_COLLECTION,KEY_COLLECTION_NAME,KEY_COUNT};

    final private static String CREATE_COLLECTION_TABLE =
            "CREATE TABLE " + COLLECTION_TBL + " ( "
                    + KEY_ID_COLLECTION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_COLLECTION_TITLE + " TEXT, "
                    + KEY_COLLECTION_NAME + " TEXT, "
                    + KEY_COUNT + " INTEGER, "
                    + KEY_Country + " TEXT, "
                    + KEY_Belongs + " INTEGER DEFAULT 0, "
                    + KEY_IMG + " TEXT, "
                    + KEY_LOCK+ " INTEGER )";

    ////////////////Version TBL
    static final String VERSION_TBL = "VershenTBL";
    final private static String CREATE_MINT_VERSION =
        "CREATE TABLE "  + VERSION_TBL + "(Version INTEGER)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.getVersion() != VERSION)
            db.execSQL(CREATE_COLLECTION_TABLE);
        _db = db;
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
        values.put(KEY_COLLECTION_TITLE, collection.getTitle());
        values.put(KEY_COLLECTION_NAME, collection.getName());
        values.put(KEY_COUNT, collection.getCount());
        values.put(KEY_Country, collection.getCountry());
        values.put(KEY_Belongs, collection.getBelongings());
        values.put(KEY_IMG,collection.getImg());
        values.put(KEY_LOCK,collection.getLock());
        // 3. insert
        db.insert(COLLECTION_TBL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    public ArrayList<Collection> getAllCollection() {
        String query = "SELECT * FROM " + COLLECTION_TBL +" WHERE "+KEY_Belongs+"=1";
        return setCollectionProperty(query);
    }

    public ArrayList<Collection> getNewCollectionsByCountry(String countryName){
        String query = "SELECT * FROM " + COLLECTION_TBL + " where " + KEY_Country + " = '"+countryName+"'" +
                " AND " + KEY_Belongs+" =0 ";
        ArrayList<Collection> collectionArrayList = setCollectionProperty(query);
        //region if there is no collections we going to load them from file
        if(collectionArrayList.size()==0) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.collections);
            CSVFile csvFile = new CSVFile(inputStream);

            collectionArrayList = csvFile.getCollectionsFromFile();

            for(int i=0; i<collectionArrayList.size(); i++){
                addCollection(new Collection(collectionArrayList.get(i).getId(),
                                             collectionArrayList.get(i).getTitle(),
                                             collectionArrayList.get(i).getName(),
                                             collectionArrayList.get(i).getCount(),
                                             collectionArrayList.get(i).getCountry(),
                                             collectionArrayList.get(i).getBelongings(),
                                             collectionArrayList.get(i).getImg(),
                                             collectionArrayList.get(i).getLock()));
            }
        }
        //endregion
        collectionArrayList = setCollectionProperty(query);
        return  collectionArrayList;
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
                                            cursor.getString(2),
                           Integer.parseInt(cursor.getString(3)),
                                            cursor.getString(4),
                           Integer.parseInt(cursor.getString(5)),
                                            cursor.getString(6),
                       Boolean.parseBoolean(cursor.getString(7))
                                            );
                collections.add(collection);
            } while (cursor.moveToNext());
        }
        return collections;
    }

    public ArrayList<Collection> getAllCollectionByCountry(String countryName, String belong) {
        String query = "SELECT * FROM " + COLLECTION_TBL +" WHERE "+KEY_Belongs+"="+belong+" AND "
                +KEY_COUNT+"="+countryName;
        return setCollectionProperty(query);
    }
    public void changeCollectionLock(int CollectionId, boolean lock){
        Log.d("change collection Lock by coin ID  = ", String.valueOf(CollectionId));
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {return;}
            ContentValues row = new ContentValues();
        int lockI;
        if(lock) lockI=1; else lockI=0;
        row.put(KEY_LOCK, lockI);
        db.update(COLLECTION_TBL, row, KEY_ID_COLLECTION + " = ?", new String[] { String.valueOf(CollectionId) } );
        db.close();
        boolean l= getCollectionLockById(CollectionId);
    }
    public boolean getCollectionLockById(int collectionid) {
        String query = "SELECT " + KEY_LOCK + " FROM " + COLLECTION_TBL + " WHERE " + KEY_ID_COLLECTION + "=" + collectionid;
        boolean lock = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                lock = (cursor.getInt(0) != 0);
            } while (cursor.moveToNext());
        }
        return lock;
    }
    public int getNumberOfCollections(){
        String query = "SELECT * FROM " + COLLECTION_TBL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
//Delete
    public void deleteDatabase() {
        context.deleteDatabase(COLLECTION_TBL);
    }
    public boolean deleteCollectionByID(int collectionid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COLLECTION_TBL, KEY_ID_COLLECTION + "=" + collectionid, null) > 0;
    }

}
