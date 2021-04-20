package com.ualr.recyclerviewassignment.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox obj, int position);

    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public AdapterListBasic(Context context, List<Inbox> items) {
        this.mItems = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list, parent,false);
        vh = new ItemViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Inbox inbox = mItems.get(position);

        // Color resources
        int selectedColor = mContext.getResources().getColor(R.color.grey_20);
        int selectedIconColor = mContext.getResources().getColor(R.color.blue_300);
        int defaultIconColor = mContext.getResources().getColor(R.color.blue_grey_600);

        // Drawable resources and initializations
        Drawable defaultCircle = mContext.getDrawable(R.drawable.shape_circle);
        Drawable selectedIndicator = mContext.getDrawable(R.drawable.ic_baseline_check_24);
        Drawable selectedCircle = mContext.getDrawable(R.drawable.shape_circle);
        defaultCircle.mutate().setColorFilter(defaultIconColor, PorterDuff.Mode.SRC_IN);
        selectedCircle.mutate().setColorFilter(selectedIconColor, PorterDuff.Mode.SRC_IN);
        selectedCircle.setBounds(0, 0, 24, 24);

        //Set standard content
        itemViewHolder.senderIcon.setText(inbox.getFrom().substring(0,1));
        itemViewHolder.senderName.setText(inbox.getFrom());
        itemViewHolder.senderEmail.setText(inbox.getEmail());
        itemViewHolder.emailPreview.setText(R.string.lorem_ipsum);
        itemViewHolder.emailTimestamp.setText(inbox.getDate());

        if (inbox.isSelected()) {
            itemViewHolder.lyt_parent.setBackgroundColor(selectedColor);
            itemViewHolder.senderIcon.setBackground(selectedCircle);
            itemViewHolder.senderIcon.setText("");
            itemViewHolder.senderIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(selectedIndicator, null,null,null);
        }
        else {
            itemViewHolder.lyt_parent.setBackgroundColor(Color.TRANSPARENT);
            itemViewHolder.senderIcon.setBackground(defaultCircle);
            itemViewHolder.senderIcon.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

        }
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public void addItem(int position, Inbox item){

        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        if (position >= mItems.size()){
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void clearAllSelections() {
        for (Inbox mItem : mItems) {
            mItem.setSelected(false);
        }
        notifyDataSetChanged();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView senderIcon;
        public TextView senderName;
        public TextView senderEmail;
        public TextView emailPreview;
        public TextView emailTimestamp;
        public View lyt_parent;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            senderIcon = itemView.findViewById(R.id.sender_icon);
            senderName = itemView.findViewById(R.id.name);
            senderEmail = itemView.findViewById(R.id.email);
            emailPreview = itemView.findViewById(R.id.info);
            emailTimestamp = itemView.findViewById(R.id.date);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, mItems.get(getLayoutPosition()), getLayoutPosition());
                }
            });
//
        }
    }

}