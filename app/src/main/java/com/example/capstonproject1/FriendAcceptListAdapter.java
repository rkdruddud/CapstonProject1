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

public class FriendAcceptListAdapter extends RecyclerView.Adapter<FriendAcceptListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<FriendAcceptItem> items = new ArrayList<>();


    //리스너 객체 참조 변수
    //리스너 객체 참조를 어댑터에 전달 메서드
    public FriendAcceptListAdapter(Context mContext){

        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.addfriendacceptlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendAcceptItem item = items.get(position);
        holder.setItem(item);



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(FriendAcceptItem item){
        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView friend_iv;
    TextView friend_name;
    TextView friend_phonNumber;
    Button accept_btn;
    Button deny_btn;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        friend_iv = itemView.findViewById(R.id.imageView4);
        friend_name = itemView.findViewById(R.id.textView29);
        friend_phonNumber = itemView.findViewById(R.id.textView27);
        accept_btn = itemView.findViewById(R.id.Acceptbutton);
        deny_btn = itemView.findViewById(R.id.deletePersonbtn);
    }




    public void setItem(FriendAcceptItem item){
        friend_iv.setImageResource(item.resId);
        friend_name.setText(item.Name);
        friend_phonNumber.setText(item.PhonNumber);

    }
    }
}
