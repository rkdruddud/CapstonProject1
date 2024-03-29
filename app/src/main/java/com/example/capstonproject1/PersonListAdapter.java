package com.example.capstonproject1;
import android.app.Activity;
import android.app.Dialog;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<PersonRecycleItem> items = new ArrayList<>();

    interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

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

    public void remove(int position){
        try {
            items.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void addItem(PersonRecycleItem item){
        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView person_iv;
    TextView person_name;
    TextView person_phonNumber;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        person_iv = itemView.findViewById(R.id.imageView2);
        person_name = itemView.findViewById(R.id.textView22);
        person_phonNumber = itemView.findViewById(R.id.textView21);

        itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition ();
                if (position!=RecyclerView.NO_POSITION){
                    if (mListener!=null){
                        mListener.onItemClick (view,position);
                    }
                }
            }
        });
    }




    public void setItem(PersonRecycleItem item){
        person_iv.setImageResource(item.resId);
        person_name.setText(item.Name);
        person_phonNumber.setText(item.PhonNumber);
    }
    }
}
