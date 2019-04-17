package com.jarvis.grouprecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author jarvis
 */
public interface IDelegateAdapter {

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type);

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position);

    public int getItemViewType(int position);

    public int getItemCount();
}
