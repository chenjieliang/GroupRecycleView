package com.jarvis.groupview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jarvis.groupview.R;
import com.jarvis.groupview.entity.ColumnField;

/**
 * @author jarvis
 */
public class LinearInfoViewHolder extends RecyclerView.ViewHolder {

    public TextView labelView;
    public TextView valueView;
    public LinearInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        labelView = itemView.findViewById(R.id.label);
        valueView = itemView.findViewById(R.id.editValue);
    }

    public void render(ColumnField field){
        labelView.setText(field.label);
        valueView.setText(field.value);
    }
}
