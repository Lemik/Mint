package com.example.leoniddushin.mint2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leoniddushin.mint2.Objects.Collection;
import com.example.leoniddushin.mint2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoniddushin on 14-12-26.
 */
//todo verify difference betving BaseAdapter and extendedListBaseAdapter
public class CollectionAdapter extends BaseAdapter {
    private ArrayList<Collection> collections = new ArrayList<Collection>();
    private Context context;

    public CollectionAdapter(Context context,ArrayList<Collection> collections) {
        this.collections = collections;
        this.context= context;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return collections.size();
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
        return collections.get(position);
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
        // todo I dont know where it's used
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

        listView = inflater.inflate(R.layout.my_collections_coin, null);
        ImageView imageView = (ImageView) listView.findViewById(R.id.cl_iv_image);

        TextView title = (TextView) listView.findViewById(R.id.cl_tv_title);
        TextView count = (TextView) listView.findViewById(R.id.cl_tv_count);

        int icon = context.getResources().getIdentifier(collections.get(position).getImg(),"drawable",context.getPackageName());
        imageView.setImageResource(icon);
        title.setText(collections.get(position).getName());
        int howManyCoinsInCollection = collections.get(position).getCount();
        count.setText("0 / " + howManyCoinsInCollection);//todo add how many coins in collection from DB

        return listView;
    }
}
