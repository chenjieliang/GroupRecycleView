package com.jarvis.groupview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jarvis.groupview.R;


/**
 * @author jarvis
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.header_title);
    }

    public void render(String title){
        titleView.setText(title);
    }
}
