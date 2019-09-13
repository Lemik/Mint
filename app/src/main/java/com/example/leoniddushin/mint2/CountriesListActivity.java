package com.example.leoniddushin.mint2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Adapters.CountriesAdapter;


public class CountriesListActivity extends AppCompatActivity {

    CountriesAdapter countriesAdapter;
    protected static final String EXTRA_RES_COLLECTION_ID = "COLLECTION";
    protected static final String EXTRA_RES_COUNTRY_ID = "COUNTRY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_list);

        ListView listView = (ListView) findViewById(R.id.countrries_lv);
        countriesAdapter = new CountriesAdapter(this);
        listView.setAdapter(countriesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Show Toast message
                Toast.makeText(CountriesListActivity.this, "Open all Country available collections", Toast.LENGTH_SHORT).show();

                //Create an Intent to start the MyCollectionsActivity
                Intent intent = new Intent(CountriesListActivity.this, NewListOfCollectionsActivity.class);

                // Add the ID of the thumbnail to display as an Intent Extra
                //intent.putExtra(CountriesListActivity.EXTRA_RES_COLLECTION_ID, "NEW");
                intent.putExtra(CountriesListActivity.EXTRA_RES_COUNTRY_ID, countriesAdapter.countryList.get(position).getName());

                // Start the ImageViewActivity
                startActivity(intent);
            }
        });
    }
/////Menu///////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countries_list, menu);
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
            Toast.makeText(CountriesListActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CountriesListActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
