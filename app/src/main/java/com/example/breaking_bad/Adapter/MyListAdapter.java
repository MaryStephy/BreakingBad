package com.example.breaking_bad.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.breaking_bad.Data.Post;
import com.example.breaking_bad.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private List<Post> listdata;
    private Filter mFilter;
    private List<Post> mCompleteList,mList;
    private ArrayList<Post> arraylist;


    // RecyclerView recyclerView;
    public MyListAdapter(List<Post> listdata) {
        this.listdata = listdata;
        this.arraylist = new ArrayList<Post>();
        this.arraylist.addAll(listdata);
        Log.d("Size1", this.listdata.size() + "");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post myListData = listdata.get(position);
        Log.d("data1111111111111111111", listdata.size() + "");
        holder.textView_name.setText(listdata.get(position).getName());
       // holder.textView_description.setText(listdata.get(position).getImg());
        Picasso.get().load(listdata.get(position).getImg()).into(holder.imageview);

    }


    @Override
    public int getItemCount() {
        Log.d("Size", listdata.size() + "");
        return listdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_name;
        public ImageView imageview;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView_name = (TextView) itemView.findViewById(R.id.textViewname);
            this.imageview = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listdata.clear();

        if (charText.length() == 0) {
            listdata.addAll(arraylist);
        } else {
            for (Post wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listdata.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
