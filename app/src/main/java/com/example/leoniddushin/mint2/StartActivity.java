package com.example.leoniddushin.mint2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//My Collection
        final Button button = (Button) findViewById(R.id.s_b_MyCollections);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //todo добавить возможность выбирать в настройках что бы показывать все мои коллекции организавав их по страннам
                // Show Toast message
                Toast.makeText(StartActivity.this, "Loading collections", Toast.LENGTH_SHORT).show();

                //Create an Intent to start the ListOfCollectionsActivity
                Intent intent = new Intent(StartActivity.this, ListOfCollectionsActivity.class);

                // Add the ID of the thumbnail to display as an Intent Extra
                intent.putExtra(CountriesListActivity.EXTRA_RES_COLLECTION_ID, "MY_COLLECCTIONS");

                // Start the ImageViewActivity
                startActivity(intent);
            }

        });
//New Collection
        final Button button2 = (Button) findViewById(R.id.s_b_NewCollection);
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(StartActivity.this, "Please select country", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, CountriesListActivity.class);
                startActivity(intent);

            }
        });

        final Button button3 = (Button) findViewById(R.id.s_b_ManageCollections);
        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(StartActivity.this, "Manage Collections", Toast.LENGTH_SHORT).show();

            }
        });
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
                return true;

//            case R.id.menu_location:
//                // Here we might call LocationManager.requestLocationUpdates()
//                return true;

            case R.id.menu_settings:
                Toast.makeText(StartActivity.this, "Settings", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
                startActivity(intent);

                // not working (((
               //Create an Intent to start the ImageViewActivity
               // Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
               // startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // END_INCLUDE(menu_item_selected)
}
