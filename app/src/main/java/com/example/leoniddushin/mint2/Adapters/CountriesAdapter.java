package com.example.leoniddushin.mint2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.leoniddushin.mint2.File.CSVFile;
import com.example.leoniddushin.mint2.Objects.Country;
import com.example.leoniddushin.mint2.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by leoniddushin on 14-12-27.
 */
public class CountriesAdapter extends BaseAdapter {

    public CountriesAdapter(Context context) {
        this.context = context;

        InputStream inputStream = context.getResources().openRawResource(R.raw.countrys);
        CSVFile csvFile = new CSVFile(inputStream);
        this.countryList = csvFile.getCountryFromFile();
    }

    private Context context;
    public ArrayList<Country> countryList = new ArrayList<Country>();

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView;
        listView = inflater.inflate(R.layout.my_collections_coin, null);
        ImageView imageView = (ImageView) listView.findViewById(R.id.cl_iv_image);
        TextView title = (TextView) listView.findViewById(R.id.cl_tv_title);
        TextView count = (TextView) listView.findViewById(R.id.cl_tv_count);
        int i = context.getResources().getIdentifier(countryList.get(position).getImageName(),"drawable",context.getPackageName());
        imageView.setImageResource(i);//todo change image based on Country
        title.setText(countryList.get(position).getName());
        int Count = countryList.get(position).getCount();
        count.setText(Count + " - Collections");
        return listView;
    }
}
