package com.jarvis.groupview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jarvis.groupview.R;

/**
 * @author jarvis
 */
public class GridInfoViewHolder extends RecyclerView.ViewHolder {

    public TextView labelView;
    public GridInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        labelView = itemView.findViewById(R.id.label);
    }

    public void render(String title){
        labelView.setText(title);
    }

}
