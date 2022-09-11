package com.example.capstonproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<TagListItem> items = new ArrayList<>();

    public TagListAdapter(Context mContext){
    this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.addtaglist,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    TagListItem item = items.get(position);
    holder.setItem(item);
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public void addItem(TagListItem item){
    items.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tagname;
        TextView share_v;
        Button delete;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
        image = itemView.findViewById(R.id.imageView3);
        tagname = itemView.findViewById(R.id.tagnametextView23);
        share_v = itemView.findViewById(R.id.textView24);
        delete = itemView.findViewById(R.id.deleteTagbtn);
        }

        public void setItem(TagListItem item){
            image.setImageResource(item.resid);
            tagname.setText(item.tagname);
            share_v.setText(item.share);
        }
    }


}
