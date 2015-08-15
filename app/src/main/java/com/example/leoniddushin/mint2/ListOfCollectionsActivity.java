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

public class ListOfCollectionsActivity extends ActionBarActivity {

    private MySQLiteHelper db;
    private CoinDBHelper coindb;
//    private SimpleCursorAdapter mAdapter;
//    private String collectionid;

    ArrayList<Collection> collections = new ArrayList<Collection>();
    CollectionAdapter collectionsAdapter;
    String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collections_list);
//verify that there is atleast one collection
        db = new MySQLiteHelper(this);
        if (db.getNumberOfCollections() == 0) {
            Toast.makeText(ListOfCollectionsActivity.this, "You don't have any collections", Toast.LENGTH_SHORT).show();
            //todo add button to add new collection
        }

//get parameter
        Intent intent = getIntent();
        countryName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COUNTRY_ID);
        //todo Load all collections from DB with belongins = 1
        Collection.setCollections(db.getAllCollection());

        collectionsAdapter = new CollectionAdapter(this, Collection.collectionList);
        ListView listView = (ListView) findViewById(R.id.cl_listView);
        listView.setAdapter(collectionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int i = Collection.collectionList.get(position).getId();
                Collection.coinList = loadCoinList(i);

                //Open Collection Activity
                Intent intent = new Intent(ListOfCollectionsActivity.this, CollectionActivity.class);
                String name = Collection.collectionList.get(position).getName();
                intent.putExtra("COLLECTION_NAME", name);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Coin> loadCoinList(int id) {
        coindb = new CoinDBHelper(this);
        ArrayList<Coin> coinList = coindb.getCoinsArrayListFromCatalogByCollectionID(id);
        return coinList;
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_of_collections, menu);
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
            Toast.makeText(ListOfCollectionsActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListOfCollectionsActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
