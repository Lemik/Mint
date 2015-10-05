package com.example.leoniddushin.mint2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leoniddushin.mint2.Objects.Search;
import com.example.leoniddushin.mint2.R;

import java.util.ArrayList;

/**
 * Created by leoniddushin on 15-09-12.
 */
public class SearchAdapter extends BaseAdapter {
    private ArrayList<Search> SearchOpshens = new ArrayList<Search>();
    private Context context;


    public SearchAdapter(Context _context) {
        this.context = _context;
        SearchOpshens.add(new Search(R.drawable.canada_25cent_a, "Year", "Search by Year"));
        SearchOpshens.add(new Search(R.drawable.usa_2007_washington_25, "Nominal", "Search by Nominal"));
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return SearchOpshens.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return SearchOpshens.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView;

        listView = inflater.inflate(R.layout.search_element, null);
        ImageView iV = (ImageView) listView.findViewById(R.id.cl_iv_search_image);
        TextView title = (TextView) listView.findViewById(R.id.cl_tv_search_title);
        TextView des = (TextView) listView.findViewById(R.id.cl_tv_search_descreption);

        iV.setImageResource(SearchOpshens.get(position).getImageName());
        title.setText(SearchOpshens.get(position).getTitle());
        des.setText(SearchOpshens.get(position).getDesc());

        return listView;
    }

    public String getTitle(int position) {
        return SearchOpshens.get(position).getTitle();
    }
}
