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

public class ShareFriendListAdapter extends RecyclerView.Adapter<ShareFriendListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ShareFriendListItem> items = new ArrayList<>();


    interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View v, int position);
    }
    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) { this.mListener = listener; }
    public void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        this.mLongListener = listener;
    }


    public ShareFriendListAdapter(Context mContext){
    this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.sharefriendlist,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ShareFriendListItem item = items.get(position);
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

    public void addItem(ShareFriendListItem item){
    items.add(item);
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView friendname;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
        image = itemView.findViewById(R.id.imageView51);
        friendname = itemView.findViewById(R.id.textView18);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition ();

                        if (position != RecyclerView.NO_POSITION){
                            mLongListener.onItemLongClick(v,position);

                        }
                        return true;



                }
            });

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

        public void setItem(ShareFriendListItem item){
            image.setImageResource(item.resid);
            friendname.setText(item.friendname);

        }
    }


}
