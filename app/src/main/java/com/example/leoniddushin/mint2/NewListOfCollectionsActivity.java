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
import com.example.leoniddushin.mint2.DB.CollectionDBHelper;
import com.example.leoniddushin.mint2.File.CSVFile;
import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.Objects.Collection;

import java.io.InputStream;
import java.util.ArrayList;

public class NewListOfCollectionsActivity extends ActionBarActivity {

    ArrayList<Collection> collections = new ArrayList<Collection>();
    CollectionAdapter collectionsAdapter;
    //    private String collectionid;
    String countryName;
    private CollectionDBHelper db;
    private CoinDBHelper coindb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collections_list);
        // Create a new DatabaseHelper

        coindb = new CoinDBHelper(this);
        db = new CollectionDBHelper(this);

//get parameter
        Intent intent = getIntent();
        countryName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COUNTRY_ID);

        //todo Load all collections from file
        Collection.setCollections(db.getNewCollectionsByCountry(countryName));

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
                //add new collection
                int newcollection = db.getNumberOfCollections() + 1;
                String title = Collection.collectionList.get(position).getTitle();
                String collectionName = Collection.collectionList.get(position).getName();
                int count = Collection.collectionList.get(position).getCount();
                int collected = Collection.collectionList.get(position).getCollected();

                String country = Collection.collectionList.get(position).getCountry();
                String icon = Collection.collectionList.get(position).getImg();
                boolean lock = Collection.collectionList.get(position).getLock();
                db.addCollection(new Collection(newcollection,title, collectionName, count,collected, country, 1, icon, lock));

                addCollectiontoDB(collectionName, newcollection);
                //int i = getResources().getIdentifier(Collection.collectionList.get(position).getName(), "raw", getPackageName());
                //InputStream inputStream = getResources().openRawResource(i);

                // CSVFile csvFile = new CSVFile(inputStream);
                // ArrayList<Coin> coinList = csvFile.getCoinsFromFile();

//                    Collection.setColectionfromCointList(coinList);

//                    //Open Collection Activity
//                    Intent intent = new Intent(MyCollectionsActivity.this, CollectionActivity.class);
//                    String name = Collection.collectionList.get(position).getName();
//                    intent.putExtra("COLLECTION_NAME", name);
//                    startActivity(intent);
            }
//                else {
//
//                }
//            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             * Callback method to be invoked when an item in this view has been
             * clicked and held.
             * <p/>
             * Implementers can call getItemAtPosition(position) if they need to access
             * the data associated with the selected item.
             *
             * @param parent   The AbsListView where the click happened
             * @param view     The view within the AbsListView that was clicked
             * @param position The position of the view in the list
             * @param id       The row id of the item that was clicked
             * @return true if the callback consumed the long click, false otherwise
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            loading Collections
                int i = getResources().getIdentifier(Collection.collectionList.get(position).getName(), "raw", getPackageName());
                InputStream inputStream = getResources().openRawResource(i);

                CSVFile csvFile = new CSVFile(inputStream);
                ArrayList<Coin> coinList = csvFile.getCoinsFromFile();
                Collection.setColectionfromCointList(coinList);

                //Open Collection Activity
                Intent intent = new Intent(NewListOfCollectionsActivity.this, CollectionActivity.class);
                String name = Collection.collectionList.get(position).getTitle();
                boolean lock = Collection.collectionList.get(position).getLock();
                int collectionId = Collection.collectionList.get(position).getId();
                intent.putExtra(CollectionActivity.COLLECTION_NAME, name);
                intent.putExtra(CollectionActivity.LOCK,lock);
                intent.putExtra(CollectionActivity.COLLECTION_ID,collectionId);
                startActivity(intent);
                return true;
            }
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
}
