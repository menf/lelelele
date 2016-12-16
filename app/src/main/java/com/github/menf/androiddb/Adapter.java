package com.github.menf.androiddb;

/**
 * Created by menf on 2016-11-04.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class Adapter extends ArrayAdapter<Item> {

    List<Item> mylist;

    //test
    public Adapter(Context _context, List<Item> _mylist) {
        super(_context, R.layout.list_item, _mylist);

        this.mylist = _mylist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item row = getItem(position);

        ProductViewHolder holder;

        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.list_item, parent, false);

            //
            holder = new ProductViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);


            //
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();

            //
            holder.populateFrom(row);

            //
            return convertView;
        }


        return convertView;
    }

    static class ProductViewHolder {
        public ImageView img;
        public TextView title;

        void populateFrom(Item p) {
            title.setText(p.get_name());
            img.setImageResource(p.get_image());
        }
    }
}