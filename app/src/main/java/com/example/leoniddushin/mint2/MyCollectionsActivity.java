package com.example.leoniddushin.mint2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Adapters.CollectionAdapter;
import com.example.leoniddushin.mint2.DB.CoinDBHelper;
import com.example.leoniddushin.mint2.DB.CollectionDBHelper;
import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.Objects.Collection;

import java.util.ArrayList;

public class MyCollectionsActivity extends ActionBarActivity {

    private CollectionDBHelper dbCollection;
    private CoinDBHelper dbCoin;
    private MyCollectionsActivity context = MyCollectionsActivity.this;

    CollectionAdapter collectionsAdapter;
    String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        activity();
    }
    private void activity() {
        setContentView(R.layout.my_collections_list);
//verify that there is atleast one collection
        dbCollection = new CollectionDBHelper(this);
        dbCoin = new CoinDBHelper(this);
        if (dbCollection.getNumberOfCollections() == 0) {
            Toast.makeText(MyCollectionsActivity.this, "You don't have any collections", Toast.LENGTH_SHORT).show();
            //todo add button to add new collection
        }
       //get parameter
        Intent intent = getIntent();
        countryName = intent.getStringExtra(CountriesListActivity.EXTRA_RES_COUNTRY_ID);
        //todo Load all collections from DB with belongins = 1
        Collection.setCollections(dbCollection.getAllCollection());
        collectionsAdapter = new CollectionAdapter(this, Collection.collectionList);
        ListView listView = (ListView) findViewById(R.id.cl_listView);
        listView.setAdapter(collectionsAdapter);
        //region OnItemClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int i = Collection.collectionList.get(position).getId();
                Collection.setCollectionLock(dbCollection.getCollectionLockById(i));
                Collection.coinList = loadCoinList(i);

                //Open Collection Activity
                Intent intent = new Intent(MyCollectionsActivity.this, CollectionActivity.class);
                String name = Collection.collectionList.get(position).getTitle();
                boolean lock = Collection.collectionList.get(position).getLock();
                int collectionId = Collection.collectionList.get(position).getId();
                intent.putExtra(CollectionActivity.COLLECTION_NAME, name);
                intent.putExtra(CollectionActivity.LOCK,lock);
                intent.putExtra(CollectionActivity.COLLECTION_ID, collectionId);
                startActivity(intent);
            }
        });

        //endregion
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
  // Я сделал position final !!!!
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String name = Collection.collectionList.get(position).getTitle();
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_collection);
                dialog.setTitle("Options:");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                final EditText editText = (EditText) dialog.findViewById(R.id.editText);

                text.setText("Collection name:");
                editText.setText(name);
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                int icon = context.getResources().getIdentifier(Collection.collectionList.get(position).getImg(),"drawable",context.getPackageName());
                image.setImageResource(icon);

                Button deleteButton = (Button) dialog.findViewById(R.id.dialogButtonDelete);
                Button renameButton = (Button) dialog.findViewById(R.id.dialogButtonRename);
                Button settingsButton = (Button) dialog.findViewById(R.id.dialogButtonSettings);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//todo add error handling if something went wrong
                        if(dbCollection.deleteCollectionByID(Collection.collectionList.get(position).getId()) &&
                                 dbCoin.deleteCoinsByCollectionID(Collection.collectionList.get(position).getId()))
                            Toast.makeText(MyCollectionsActivity.this, "Collection deleted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        restartActivity();
                    }
                });
                renameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbCollection.changeCollectionNameById(Collection.collectionList.get(position).getId(),editText.getText().toString());
                        Toast.makeText(MyCollectionsActivity.this, "Rename", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        restartActivity();
                    }
                });
                settingsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyCollectionsActivity.this, "Settings", Toast.LENGTH_LONG).show();
                    }
                });

                dialog.show();
                return true;
            }
        });
    }

    private void restartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    private ArrayList<Coin> loadCoinList(int id) {
        dbCoin = new CoinDBHelper(this);
        return dbCoin.getCoinsArrayListFromCatalogByCollectionID(id);
    }

////////////////////MENU
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
            Toast.makeText(MyCollectionsActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyCollectionsActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
