package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

/**
 * Created by irconde on 2019-09-25.
 * Updated for Assignment 6 by zpborromeo on 2022-10-23.
 */

public class AdapterListBasic extends RecyclerView.Adapter{
    private static final String ID = AdapterListBasic.class.getSimpleName();
    private int selectedInboxItem = -1;
    private List<Inbox> mInbox;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public AdapterListBasic(Context context, List<Inbox> items) {
        this.mInbox = items;
        this.mContext = context;
    }

    public int getSelectedInboxItem(){
        return selectedInboxItem;
    }

    public void setSelectedInboxItem(int selectedInboxItem){
        this.selectedInboxItem = selectedInboxItem;
    }

    //added updateInbox function to update the display and an item is added
    public void updateInbox(List<Inbox> inboxList) {
        this.mInbox = inboxList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox inbox, int position);

        void onIconClick(View view, Inbox inbox, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void addItem(int position, Inbox item) {
        mInbox.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (position >= mInbox.size()){
            return;
        }
        mInbox.remove(position);
        notifyItemRemoved(position);
    }

    public void clearSelected(){
        for (Inbox email : mInbox){
            email.setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mInbox.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        InboxViewHolder vh = (InboxViewHolder) holder;
        vh.mailIcon.setText(mInbox.get(position).getFrom().substring(0,1));
        vh.mailSender.setText(mInbox.get(position).getFrom());
        vh.mailTitle.setText(mInbox.get(position).getEmail());
        vh.mailContent.setText(mInbox.get(position).getMessage());
        vh.mailTimeSent.setText(mInbox.get(position).getDate());

        int mailSelectedColor = mContext.getResources().getColor(R.color.grey_20);
        int iconSelectedColor = mContext.getResources().getColor(R.color.colorAccentLight);
        int iconDefaultColor = mContext.getResources().getColor(R.color.colorPrimary);

        //changed drawn icon to a checkmark
        Drawable defaultIcon = mContext.getDrawable(R.drawable.shape_circle);
        Drawable checkmarkIcon = mContext.getDrawable(R.drawable.checkmark);
        Drawable selectedIcon = mContext.getDrawable(R.drawable.shape_circle);
        selectedIcon.setBounds(0, 0, 24, 24);
        defaultIcon.mutate().setColorFilter(iconDefaultColor, PorterDuff.Mode.SRC_IN);
        selectedIcon.mutate().setColorFilter(iconSelectedColor, PorterDuff.Mode.SRC_IN);

        Inbox mainInbox = mInbox.get(position);

        if (mainInbox.isSelected()){
            vh.mainInboxView.setBackgroundColor(mailSelectedColor);
            vh.mailIcon.setBackground(selectedIcon);
            vh.mailIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(checkmarkIcon, null, null, null);
        }else{
            vh.mainInboxView.setBackgroundColor(Color.TRANSPARENT);
            vh.mailIcon.setBackground(defaultIcon);
            vh.mailIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        }
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {
        public TextView mailIcon;
        public TextView mailSender;
        public TextView mailTitle;
        public TextView mailContent;
        public TextView mailTimeSent;
        public View mainInboxView;

        public InboxViewHolder(View itemView) {
            super(itemView);

            mailIcon = itemView.findViewById(R.id.tvIcon);
            mailSender = itemView.findViewById(R.id.emailSender);
            mailTitle = itemView.findViewById(R.id.emailTitle);
            mailContent = itemView.findViewById(R.id.emailContent);
            mailTimeSent = itemView.findViewById(R.id.emailTimeSent);
            mainInboxView = itemView.findViewById(R.id.inboxLayout);

            mailIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onIconClick(view, mInbox.get(getLayoutPosition()), getLayoutPosition());
                }
            });
            mainInboxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, mInbox.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }

}
