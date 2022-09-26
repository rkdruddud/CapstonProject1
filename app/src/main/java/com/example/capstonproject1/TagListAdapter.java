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

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<TagListItem> items = new ArrayList<>();


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

    public void remove(int position){
        try {
            items.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void addItem(TagListItem item){
    items.add(item);
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tagname;
        TextView share_v;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
        image = itemView.findViewById(R.id.imageView33);
        tagname = itemView.findViewById(R.id.tagnametextView23);
        share_v = itemView.findViewById(R.id.textView24);

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

        public void setItem(TagListItem item){
            image.setImageResource(item.resid);
            tagname.setText(item.tagname);
            share_v.setText(item.share);
        }
    }


}
