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

public class FriendAcceptListAdapter extends RecyclerView.Adapter<FriendAcceptListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<FriendAcceptItem> items = new ArrayList<>();

    interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

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
    ImageView person_iv;
    TextView person_name;
    TextView person_phonNumber;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        person_iv = itemView.findViewById(R.id.imageView4);
        person_name = itemView.findViewById(R.id.textView29);
        person_phonNumber = itemView.findViewById(R.id.textView27);

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




    public void setItem(FriendAcceptItem item){
        person_iv.setImageResource(item.resId);
        person_name.setText(item.Name);
        person_phonNumber.setText(item.PhonNumber);
    }
    }
}