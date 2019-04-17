package com.jarvis.grouprecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author jarvis
 */
public abstract class SectionDelegateAdapter implements IDelegateAdapter {

    public static final int ITEM_TYPE_HEADER = 1001;
    public static final int ITEM_TYPE_FOOTER = 1002;
    public static final int ITEM_TYPE_CONTENT = 1003;

    protected Object headerObject;
    protected Object footerObject;
    protected List<Object> contentData;

    public void setHeader(Object object){
        headerObject = object;
    }

    public Object getHeader(){
        return headerObject;
    }

    public void setFooter(Object footerData) {
        this.footerObject = footerData;
    }

    public Object getFooter() {
        return footerObject;
    }

    public boolean hasHeader() {
        return headerObject != null;
    }

    public boolean hasFooter() {
        return footerObject != null;
    }

    public void setContentData(List data){
        contentData = data;
    }

    public Object getContentItemObject(int position){
        if (position>0 && position<contentData.size()) {
            return contentData.get(position);
        } else {
            return null;
        }
    }

    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(@NonNull ViewGroup viewGroup);

    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(@NonNull ViewGroup viewGroup);

    public abstract RecyclerView.ViewHolder onCreateContentViewHolder(@NonNull ViewGroup viewGroup);

    public abstract void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,Object headerObject);

    public abstract void onBindFooterViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,Object footerObject);

    public abstract void onBindContentViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int contentPostion, Object contentObject);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (type == ITEM_TYPE_HEADER) {
            return onCreateHeaderViewHolder(viewGroup);
        } else if (type == ITEM_TYPE_FOOTER) {
            return onCreateFooterViewHolder(viewGroup);
        } else {
            return onCreateContentViewHolder(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
         int type = getItemViewType(position);
        if (type == ITEM_TYPE_HEADER) {
            onBindHeaderViewHolder(viewHolder,headerObject);
        } else if (type == ITEM_TYPE_FOOTER) {
            onBindFooterViewHolder(viewHolder,footerObject);
        }else {
            int contentPosition = hasHeader()? position-1 : position;
            onBindContentViewHolder(viewHolder,contentPosition,contentData.get(contentPosition));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader()&& position==0) {
            return ITEM_TYPE_HEADER;
        } else if (hasFooter() && position == getItemCount() -1) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        count += hasHeader() ? 1 : 0;
        count += hasFooter() ? 1 : 0;
        count += contentData==null ? 0 : contentData.size();
        return count;
    }
}
