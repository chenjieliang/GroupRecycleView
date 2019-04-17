package com.jarvis.grouprecycler;

import android.support.v7.widget.GridLayoutManager;

/**
 * @author jarvis
 */
public class SectionSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private GroupRecycleViewAdapter groupRecycleViewAdapter;
    private GridLayoutManager layoutManager = null;

    public SectionSpanSizeLookup(GroupRecycleViewAdapter groupRecycleViewAdapter, GridLayoutManager layoutManager) {
        this.groupRecycleViewAdapter = groupRecycleViewAdapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        if(groupRecycleViewAdapter.isSectionHeaderOrFooterPosition(position)){
            return layoutManager.getSpanCount();
        }else{
            return 1;
        }
        //return groupRecycleViewAdapter.getGridLookUpSpanSize(position);
    }
}
