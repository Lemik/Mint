package com.example.leoniddushin.mint2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Adapters.CollectionAdapter;
import com.example.leoniddushin.mint2.DB.CoinDBHelper;
import com.example.leoniddushin.mint2.DB.MySQLiteHelper;
import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.Objects.Collection;

import java.util.ArrayList;

public class NewListOfCollectionsActivity extends ActionBarActivity {

    private MySQLiteHelper db;
    private CoinDBHelper coindb;
    private String collectionid;

    ArrayList<Collection> collections = new ArrayList<Collection>();
    CollectionAdapter collectionsAdapter;
    String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collections_list);
        // Create a new DatabaseHelper

        coindb = new CoinDBHelper(this);
        db = new MySQLiteHelper(this);

//get parameter
        Intent intent = getIntent();
        // String collectionName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COLLECTION_ID);
        //  collectionid =          intent.getStringExtra(CountriesListActivity.EXTRA_RES_COLLECTION_ID);
        countryName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COUNTRY_ID);

//        if(collectionName.equals("NEW")) {
        //todo Load all collections from file
        Collection.setCollections(db.getCollectionsByCountry(countryName));
//        }
//        else if(collectionName.equals("MY_COLLECCTIONS")){
//            //todo Load all collections from DB with belongins = 1
//            Collection.setCollections(db.getAllCollection());
//        }

        if (db.getNumberOfCollections() == 0) {
            // Show Toast message
            Toast.makeText(NewListOfCollectionsActivity.this, "You don't have any collections", Toast.LENGTH_SHORT).show();
            //todo add button to add new collection
        }
        collectionsAdapter = new CollectionAdapter(this, Collection.collectionList);
        ListView listView = (ListView) findViewById(R.id.cl_listView);
        listView.setAdapter(collectionsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                if(collectionid.equals("NEW")) {
                //add new collection
                String collectionName = Collection.collectionList.get(position).getName();
                int count = Collection.collectionList.get(position).getCount();
                String country = Collection.collectionList.get(position).getCountry();
                String icon = Collection.collectionList.get(position).getImg();
                int newcollection = db.getNumberOfCollections() + 1;
                db.addCollection(new Collection(newcollection, collectionName, count, country, 1, icon));

                addCollectiontoDB(collectionName, newcollection);
                //int i = getResources().getIdentifier(Collection.collectionList.get(position).getName(), "raw", getPackageName());
                //InputStream inputStream = getResources().openRawResource(i);

                // CSVFile csvFile = new CSVFile(inputStream);
                // ArrayList<Coin> coinList = csvFile.getCoinsFromFile();

//                    Collection.setColectionfromCointList(coinList);

//                    //Open Collection Activity
//                    Intent intent = new Intent(ListOfCollectionsActivity.this, CollectionActivity.class);
//                    String name = Collection.collectionList.get(position).getName();
//                    intent.putExtra("COLLECTION_NAME", name);
//                    startActivity(intent);
            }
//                else {
//                    //loading Collections
////                    int i = getResources().getIdentifier(Collection.collectionList.get(position).getName(), "raw", getPackageName());
////                    InputStream inputStream = getResources().openRawResource(i);
////
////                    CSVFile csvFile = new CSVFile(inputStream);
////                    ArrayList<Coin> coinList = csvFile.getCoinsFromFile();
////                    Collection.setColectionfromCointList(coinList);
/////
//                    int i = Collection.collectionList.get(position).getId();
//                    Collection.coinList = loadCoinList(i);
//
//                    //Open Collection Activity
//                    Intent intent = new Intent(NewListOfCollectionsActivity.this, CollectionActivity.class);
//                    String name = Collection.collectionList.get(position).getName();
//                    intent.putExtra("COLLECTION_NAME", name);
//                    startActivity(intent);
//                }
//            }
        });
    }

    private ArrayList<Coin> loadCoinList(int id) {
        coindb = new CoinDBHelper(this);
        ArrayList<Coin> coinList = coindb.getCoinsArrayListFromCatalogByCollectionID(id);
        return coinList;

    }

    private void addCollectiontoDB(String CppeCollectionName, int CollectionId) {
        coindb = new CoinDBHelper(this);
        coindb.addNewCollectionToDB(CppeCollectionName, CollectionId);
        coindb.getCoinsArrayListFromCatalogByCollectionID(1);//set ID
        //todo Change Toast message
        //todo Add spiner if it taking long
        Toast.makeText(NewListOfCollectionsActivity.this, "collections have been added to collection", Toast.LENGTH_LONG).show();
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_list_of_collections, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(NewListOfCollectionsActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewListOfCollectionsActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//DB
// Close database
//@Override
//protected void onDestroy() {
//    super.onDestroy();
//}
}
