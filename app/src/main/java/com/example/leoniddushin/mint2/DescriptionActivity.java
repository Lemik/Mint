package com.example.leoniddushin.mint2;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leoniddushin.mint2.Objects.Collection;

import java.util.ArrayList;


public class DescriptionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_description);
        //Get Intent
        Intent intent = getIntent();
        String _title= intent.getStringExtra("title");
        String _mint = intent.getStringExtra("mint");
        String _year = intent.getStringExtra("year");
        String _imgA = intent.getStringExtra("imgA");
        String _imgB = intent.getStringExtra("imgB");
        String _desc = intent.getStringExtra("desc");


        //Set Descriptions
        //todo Image
        TextView Title = (TextView) findViewById(R.id.Desc_tv_CoinTitle);
        TextView Mint = (TextView) findViewById(R.id.Desc_tv_MintText);
        TextView Year = (TextView) findViewById(R.id.Desc_tv_YearText);
        TextView Description = (TextView) findViewById(R.id.Desc_tv_DescriptionText);
        ImageView imgA = (ImageView) findViewById(R.id.Desc_ib_A);
        ImageView imgB = (ImageView) findViewById(R.id.Desc_ib_B);

        //Set Default Settings
        Title.setText("NO Title");
        //Mint.setText("");
        Year.setText("????");
        Description.setText("No Description");

        //CoinName.setText("TEXT");
        if(!_title.isEmpty()) Title.setText(_title);
        if(!_mint.isEmpty())  Mint.setText(_mint);
        if(!_year.isEmpty())  Year.setText(_year);
        if(!_desc.isEmpty())  Description.setText(_desc);

        int iconA = getResources().getIdentifier(_imgA,"drawable",getPackageName());
        imgA.setImageResource(iconA);
        int iconB = getResources().getIdentifier(_imgB,"drawable",getPackageName());
        imgB.setImageResource(iconB);
    }

//Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description, menu);
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
            Toast.makeText(DescriptionActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DescriptionActivity.this, SettingsActivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
