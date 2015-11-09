package com.example.leoniddushin.mint2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leoniddushin.mint2.DB.CoinDBHelper;
import com.example.leoniddushin.mint2.DB.CollectionDBHelper;
import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.R;

import java.util.ArrayList;

/**
 * Created by leoniddushin on 14-12-20.
 */
public class CoinAdapter extends BaseAdapter {
    private ArrayList<Coin> coinList = new ArrayList<Coin>();
    private Context context;
    //  private CoinDBHelper coinDBHelper = new CoinDBHelper(this.context) ;

    public CoinAdapter(Context context, ArrayList<Coin> coinList) {
        this.context = context;
        this.coinList = coinList;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return coinList.size();
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
        return coinList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return coinList.get(position).getId();
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

        View gridView;
        gridView = inflater.inflate(R.layout.coin_in_collection, null);
        TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

        // set value into textview
        String text = coinList.get(position).getTitle() + " " +
                coinList.get(position).getYear() + " " +
                coinList.get(position).getMint();

        textView.setText(text);
        if (text.length() > 8) {
            textView.setPadding(0, 0, 0, 0);
        }
        if (text.length() > 10) {
            textView.setTextSize(10);
        }
        // set image based on selected text
        int icon = context.getResources().getIdentifier(coinList.get(position).getImgA(), "drawable", context.getPackageName());
        imageView.setImageResource(icon);
        if (coinList.get(position).getCount() == 0)
            imageView.setAlpha((float) 0.2);
        else
            imageView.setAlpha((float) 1);

        return gridView;
    }

    public void change(int position,int collection_id) {
        int i = coinList.get(position).getCount();
        CoinDBHelper coinDBHelper = new CoinDBHelper(this.context);

        if (i == 0) {
            coinList.get(position).setCount(1);
            changeCollected(collection_id,1);
            coinDBHelper.changeCoinState(coinList.get(position).getId(), 1);

        } else {
            coinList.get(position).setCount(0);
            changeCollected(collection_id,-1);
            coinDBHelper.changeCoinState(coinList.get(position).getId(), 0);
        }
    }
    public void changeLock(int collection,boolean lock) {
        CollectionDBHelper helper = new CollectionDBHelper(this.context);
        helper.changeCollectionLock(collection,lock);
    }
    public void changeCollected(int collection,int collected) {
        CollectionDBHelper helper = new CollectionDBHelper(this.context);
        helper.changeCollectedCount(collection,collected);
    }

    public int getId(int position) {
        return coinList.get(position).getId();
    }

    public String getTitle(int position) {
        return coinList.get(position).getTitle();
    }

    public String getYear(int position) {
        return coinList.get(position).getYear();
    }

    public String getMint(int position) {
        return coinList.get(position).getMint();
    }

    public String getGrade(int position) {
        return coinList.get(position).getGrade();
    }

    public String getImgA(int position) {
        return coinList.get(position).getImgA();
    }

    public String getImgB(int position) {
        return coinList.get(position).getImgB();
    }

    public String getDescription(int position) {
        return coinList.get(position).getDescription();
    }

    public String getNote(int position) {
        return coinList.get(position).getNote();
    }


// TODO set this up insted tha is above
//    private class MyViewHolder {
//        TextView tvTitle, tvDesc;
//        ImageView ivIcon;
//    }
//
//    private TextView detail(View v, int resId, String text) {
//        TextView tv = (TextView) v.findViewById(resId);
//        tv.setText(text);
//        return tv;
//    }
//
//    private ImageView detail(View v, int resId, int icon) {
//        ImageView iv = (ImageView) v.findViewById(resId);
//        iv.setImageResource(icon); //
//
//        return iv;
//    }
}
