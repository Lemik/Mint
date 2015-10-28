package com.example.leoniddushin.mint2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.leoniddushin.mint2.DB.CoinDBHelper;
import com.example.leoniddushin.mint2.DB.CollectionDBHelper;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//       CoinDBHelper coindb;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//My Collection
        final Button myCollection = (Button) findViewById(R.id.s_b_MyCollections);
        myCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //todo добавить возможность выбирать в настройках что бы показывать все мои коллекции организавав их по страннам
                Toast.makeText(StartActivity.this, "Loading collections", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, MyCollectionsActivity.class);
                startActivity(intent);
            }

        });
//New Collection
        final Button newCollection = (Button) findViewById(R.id.s_b_NewCollection);
        newCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(StartActivity.this, "Please select country", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, CountriesListActivity.class);
                startActivity(intent);
            }
        });
//Search
        final Button search = (Button) findViewById(R.id.s_b_Search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(StartActivity.this, "Search", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    private void deleteAllInDb() {
        CoinDBHelper coindb = new CoinDBHelper(this);
        coindb.deleteDatabase();
        CollectionDBHelper db = new CollectionDBHelper(this);
        db.deleteDatabase();
    }
    // BEGIN_INCLUDE(create_menu)

    /**
     * Use this method to instantiate your menu, and add your items to it. You
     * should return true if you have added items to it and want the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
//        MenuItem locationItem = menu.add(0, R.id.menu_location, 0, R.string.menu_location);
//        locationItem.setIcon(R.drawable.ic_action_settings);

        // Need to use MenuItemCompat methods to call any action item related methods
//        MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    // END_INCLUDE(create_menu)

    // BEGIN_INCLUDE(menu_item_selected)

    /**
     * This method is called when one of the menu items to selected. These items
     * can be on the Action Bar, the overflow menu, or the standard options menu. You
     * should return true if you handle the selection.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                // Here we might start a background refresh task
                deleteAllInDb();
                return true;

//            case R.id.menu_location:
//                // Here we might call LocationManager.requestLocationUpdates()
//                return true;

            case R.id.menu_settings:
                Toast.makeText(StartActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // END_INCLUDE(menu_item_selected)
}
