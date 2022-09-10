package com.example.capstonproject1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<PersonRecycleItem> items = new ArrayList<>();

    public PersonListAdapter(Context mContext){
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.addpersorlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonRecycleItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(PersonRecycleItem item){
        items.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
    ImageView person_iv;
    TextView person_name;
    TextView person_phonNumber;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        person_iv = itemView.findViewById(R.id.imageView2);
        person_name = itemView.findViewById(R.id.textView22);
        person_phonNumber = itemView.findViewById(R.id.textView21);
    }

    public void setItem(PersonRecycleItem item){
        person_iv.setImageResource(item.resId);
        person_name.setText(item.Name);
        person_phonNumber.setText(item.PhonNumber);
    }
    }
}
