package com.example.leoniddushin.mint2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Adapters.CollectionAdapter;
import com.example.leoniddushin.mint2.File.CSVFile;
import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.Objects.Collection;
import com.example.leoniddushin.mint2.DB.MySQLiteHelper;

import java.io.InputStream;
import java.util.ArrayList;

public class ListOfCollectionsActivity extends ActionBarActivity {

    private MySQLiteHelper db;
    private SimpleCursorAdapter mAdapter;

    ArrayList<Collection> collections = new ArrayList<Collection>();
    CollectionAdapter collectionsAdapter;
    String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collections_list);
        // Create a new DatabaseHelper

        db = new MySQLiteHelper(this);
        db.deleteDatabase();
//          if(db.getNumberOfCollections()==0){
//                db.addCollection(new Collection(1,"canada_cent",100,"Canada",0,"ic_c1"));
//                db.addCollection(new Collection(1,"canada_cent",100,"Canada",0,"ic_c1"));
//                db.addCollection(new Collection(1,"canada_cent",100,"Canada",0,"ic_c1"));
//          }
//get parameter
        Intent intent = getIntent();
        String collectionName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COLLECTION_ID);
        countryName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COUNTRY_ID);

        if(collectionName.equals("NEW")) {
            //todo Load all collections from file
            Collection.setCollections(db.getCollectinsByCountry(countryName));
        }
        else if(collectionName.equals("MY_COLLECCTIONS")){
            //todo Load all collections from DB with belongins = 1
            Collection.setCollections(db.getAllCollection());
        }

        if(Collection.collectionList.size()==0){
            // Show Toast message
            Toast.makeText(ListOfCollectionsActivity.this, "You don't have any collections", Toast.LENGTH_SHORT).show();
            //todo add button to add new collection
        }
        collectionsAdapter = new CollectionAdapter(this,Collection.collectionList);
        ListView listView =(ListView) findViewById(R.id.cl_listView);
        listView.setAdapter(collectionsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //loading Collections
                int i = getResources().getIdentifier(Collection.collectionList.get(position).getName(),"raw",getPackageName());
                InputStream inputStream = getResources().openRawResource(i);

                CSVFile csvFile = new CSVFile(inputStream);
                ArrayList<Coin> coinList = csvFile.getCoins();
                Collection.loadCollectionFromFile(coinList);

                //Open Collection Activity
                Intent intent = new Intent(ListOfCollectionsActivity.this, CollectionActivity.class);
                String name = Collection.collectionList.get(position).getName();
                intent.putExtra("COLLECTION_NAME",name);
                startActivity(intent);
            }
        });
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


//DB
// Close database
@Override
protected void onDestroy() {

   // db.getWritableDatabase().close();
   // db.deleteDatabase();

    super.onDestroy();

}
}
