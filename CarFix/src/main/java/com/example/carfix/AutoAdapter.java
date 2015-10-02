package com.example.carfix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by anton on 13-11-28.
 */
public class AutoAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<CarItem> objects;
    ArrayList<CarItem> filteredData;

    AutoCompleteTextView autoCompleteTextView;
    //TextView txtUnit;

    public static CarItem carItem = null;

    public AutoAdapter(Context cont, ArrayList<CarItem> array, AutoCompleteTextView textView){
        context = cont;
        objects = array;
        filteredData = objects;
        autoCompleteTextView = textView;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.category_adapter, null);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        //final TextView txtView = (TextView)convertView.findViewById(R.id.txtCategory);
        final TextView txtView = (TextView)convertView.findViewById(android.R.id.text1);
        txtView.setText(filteredData.get(position).getRegNumber());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText(txtView.getText().toString());
                autoCompleteTextView.dismissDropDown();
                carItem = filteredData.get(position);
            }
        });

        return convertView;
        //}

        //return null;
    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();
                //prodID=null;
                carItem = null;

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = objects;
                    results.count = objects.size();
                }
                else
                {
                    ArrayList<CarItem> filterResultsData = new ArrayList<CarItem>();

                    for(CarItem data : objects)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        if(data.getRegNumber().contains(charSequence.toString()))
                        {
                            if(carItem==null)
                                carItem = data;
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<CarItem>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}