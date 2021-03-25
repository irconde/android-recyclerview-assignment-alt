package com.ualr.recyclerviewassignment.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter {

    private List<Inbox> mItems;


    public interface OnItemClickListener {
        void onItemClick(View view, Inbox obj, int position);
    }

    private OnItemClickListener mListener;

    public AdapterListBasic(List<Inbox> items) {
        this.mItems = items;
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public void removeItem(int position){
        if (position >= mItems.size()){
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void addItem(int position, Inbox item){

        mItems.add(position, item);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lyInflater = LayoutInflater.from(parent.getContext());
        View itemView = lyInflater.inflate(R.layout.items_inbox, parent, false);
        RecyclerView.ViewHolder vh = new ItemViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int index) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Inbox i = mItems.get(index);


        itemViewHolder.name.setText(i.getFrom());
        itemViewHolder.email.setText(i.getEmail());
        itemViewHolder.date.setText(i.getDate());
        itemViewHolder.imageLetter.setText(Character.toString(i.getFrom().charAt(0)));
//        itemViewHolder.image.setImageResource();

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }



    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView imageLetter;
        public TextView name;
        public TextView email;
        public TextView date;
        public RelativeLayout lyt_image;
        public ImageView imageX;

        public View lyt_parent;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lyt_image = itemView.findViewById(R.id.lyt_image);
            imageLetter = itemView.findViewById(R.id.image_letter);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            date = itemView.findViewById(R.id.date);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            imageX = itemView.findViewById(R.id.image_X);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mListener.onItemClick(v, mItems.get(getLayoutPosition()), getLayoutPosition());

                }
            });
            lyt_image.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {

//                    mListener.onItemClick(v, mItems.get(getLayoutPosition()), getLayoutPosition());
                    imageX.setImageResource(R.drawable.ic_delete_24px);
                    imageLetter.setText("");

                }
            });
            imageX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getLayoutPosition());
                    imageX.setImageResource(R.drawable.shape_circle);

                }
            });
        }
    }

}