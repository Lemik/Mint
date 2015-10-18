package com.example.leoniddushin.mint2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Adapters.CoinAdapter;
import com.example.leoniddushin.mint2.Objects.Collection;

import java.util.Collections;


public class CollectionActivity  extends ActionBarActivity {

    protected static final String EXTRA_RES_ID = "POSITION";
    protected static final String COLLECTION_NAME = "COLLECTION_NAME";
    protected static final String COLLECTION_ID= "COLLECTION_ID";
    protected static final String LOCK = "LOCK";

    GridView gridView;
    CoinAdapter adapter;
   // CollectionAdapter collectionAdapter;
    boolean loсk = true; // true -close false -open
    int collection_id;
    private Context context;
   // private CoinDBHelper coinDBHelper = new CoinDBHelper(context) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collection_view);

        // Get the Intent used to start this Activity
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        //set title
        String title = bundle.getString(COLLECTION_NAME);
        loсk = bundle.getBoolean(LOCK);
        Integer t =                  bundle.getInt(COLLECTION_ID);
        setTitle(intent.getStringExtra(title));//todo move to parametrs
        collection_id = t;



        //todo Add logic to accept collection from main screen
        adapter = new CoinAdapter(this, Collection.coinList);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(loсk){
                    Toast.makeText(CollectionActivity.this, "Update", Toast.LENGTH_SHORT).show();
                    adapter.change(position);
                    adapter.notifyDataSetChanged();
                }
                else{
                    //TODO add Image
                    Toast.makeText(CollectionActivity.this, "Please unlock Editing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                Toast.makeText(CollectionActivity.this, "Description", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CollectionActivity.this, DescriptionActivity.class);
                intent.putExtra("title",adapter.getTitle(position));
                intent.putExtra("mint",adapter.getMint(position));
                intent.putExtra("year",adapter.getYear(position));
                intent.putExtra("imgA",adapter.getImgA(position));
                intent.putExtra("imgB",adapter.getImgB(position));
                intent.putExtra("desc",adapter.getDescription(position));
                startActivity(intent);
                return true;
            }
        });
    }

//MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
       // menu.findItem(R.id.menu_refresh).setIcon()
        //MenuItem item =  (MenuItem) findViewById(R.id.menu_refresh);
        if (loсk) {
            menu.findItem(R.id.menu_refresh).setIcon(R.drawable.ic_action_lock_o);
        } else {
            menu.findItem(R.id.menu_refresh).setIcon(R.drawable.ic_action_lock_c);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if (loсk) {
                    item.setIcon(R.drawable.ic_action_lock_c);
                    loсk = false;
                } else {
                    item.setIcon(R.drawable.ic_action_lock_o);
                    loсk = true;
                }
                adapter.changeLock(collection_id,loсk);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}