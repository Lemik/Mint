package com.example.leoniddushin.mint2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Adapters.SearchAdapter;
import com.example.leoniddushin.mint2.DB.CoinDBHelper;
import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.Objects.Collection;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    private ListView listView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.search_lv);
        searchAdapter = new SearchAdapter(this);
        listView.setAdapter(searchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Show Toast message
                Toast.makeText(SearchActivity.this, "Search by " + searchAdapter.getTitle(position), Toast.LENGTH_SHORT).show();
                switch (searchAdapter.getTitle(position)) {
                    case "Year":
                        searchByYear();
                        break;
                    case "Nominal":
                        searchByNominal();
                        break;
                }
            }
        });
    }

    private void searchByYear() {
        View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.seach_by_year, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(SearchActivity.this);
        ab.setView(view);
        final EditText year = (EditText) view.findViewById(R.id.search_by_year);
        ab.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SearchActivity.this, "Year = " + year.getText(), Toast.LENGTH_SHORT).show();
//todo _year cheack for Integer
                        int _year = Integer.parseInt(year.getText().toString());
                        Collection.coinList = loadCoinListByYear(_year);

                        //Open Collection Activity
                        Intent intent = new Intent(SearchActivity.this, CollectionActivity.class);
                        String name = "All Coins by " + _year + " Year";
                        intent.putExtra("COLLECTION_NAME", name);
                        startActivity(intent);
                    }
                });
        Dialog dialog = ab.create();
        dialog.show();
    }

    private void searchByNominal() {
        View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.seach_by_nominal, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(SearchActivity.this);
        ab.setView(view);
        final EditText nominal = (EditText) view.findViewById(R.id.search_by_nominal);
        ab.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SearchActivity.this, "Nominal = " + nominal.getText(), Toast.LENGTH_SHORT).show();
//todo _year cheack for Integer
                        float _nominal = Float.parseFloat(nominal.getText().toString());
                        Collection.coinList = loadCoinListByNominal(_nominal);

                        //Open Collection Activity
                        Intent intent = new Intent(SearchActivity.this, CollectionActivity.class);
                        String name = "All Coins by " + _nominal + " in Nominal";
                        intent.putExtra("COLLECTION_NAME", name);
                        startActivity(intent);
                    }
                });
        Dialog dialog = ab.create();
        dialog.show();
    }

    private ArrayList<Coin> loadCoinListByYear(int year) {
        CoinDBHelper coindb = new CoinDBHelper(this);
        ArrayList<Coin> coinList = coindb.getCoinsArrayListFromCatalogByYear(year);
        return coinList;

    }

    private ArrayList<Coin> loadCoinListByNominal(float nominal) {
        CoinDBHelper coindb = new CoinDBHelper(this);
        ArrayList<Coin> coinList = coindb.getCoinsArrayListFromCatalogByNominal(nominal);
        return coinList;

    }

    /////Menu///////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
