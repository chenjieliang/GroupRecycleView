package com.jarvis.grouprecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jarvis
 */
public class GroupRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SectionDelegateAdapter> sectionAdaptes = new ArrayList<>();
    private List<Integer> sectionForPosition = new ArrayList<>();;
    private List<Integer> positionWithinSection = new ArrayList<>();
    private int itemCount = 0;
    private int currentItemPosition = 0;

    public GroupRecycleViewAdapter() {
        registerAdapterDataObserver(new SectionDataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        setupIndices();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void addSectionAdapter(SectionDelegateAdapter adapter){
        sectionAdaptes.add(adapter);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        int section = sectionForPosition.get(currentItemPosition);
        SectionDelegateAdapter adapter = sectionAdaptes.get(section);
        return adapter.onCreateViewHolder(viewGroup,type);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int section = sectionForPosition.get(position);
        SectionDelegateAdapter adapter = sectionAdaptes.get(section);
        int itemPositionInSection = positionWithinSection.get(position);
        adapter.onBindViewHolder(viewHolder,itemPositionInSection);
    }

    @Override
    public int getItemViewType(int position) {
        currentItemPosition = position;
        return getItemViewTypeInSection(position);
    }

    public int getItemViewTypeInSection(int position){
        int section = sectionForPosition.get(position);
        SectionDelegateAdapter adapter = sectionAdaptes.get(section);
        int itemPositionInSection = positionWithinSection.get(position);
        return adapter.getItemViewType(itemPositionInSection);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public int getItemCountForSection(int section){
        if (section>=0 && section<sectionAdaptes.size()) {
            return sectionAdaptes.size();
        } else {
            return 0;
        }
    }

    public boolean isSectionHeaderPosition(int position) {
        return  getItemViewTypeInSection(position) == SectionDelegateAdapter.ITEM_TYPE_HEADER;
    }

    public boolean isSectionFooterPosition(int position) {
        return getItemViewTypeInSection(position) == SectionDelegateAdapter.ITEM_TYPE_FOOTER;
    }

    public boolean isSectionHeaderOrFooterPosition(int position) {
        int type = getItemViewTypeInSection(position);
        return  type == SectionDelegateAdapter.ITEM_TYPE_HEADER
                      || type == SectionDelegateAdapter.ITEM_TYPE_FOOTER;
    }

    /**
     * 判断该position对应的位置是要固定
     *
     * @param position adapter position
     * @return true or false
     */
    public boolean isStickyPosition(int position) {
        return isSectionHeaderPosition(position);
    }

    private void setupIndices(){
        if (sectionForPosition.size()>0) {
            sectionForPosition.clear();
        }
        if (positionWithinSection.size()>0) {
            positionWithinSection.clear();
        }
        itemCount = 0;
        precomputeIndices();
    }

    private void precomputeIndices(){
        for (int sectionIndex = 0;sectionIndex < sectionAdaptes.size();sectionIndex++) {
             SectionDelegateAdapter adapter = sectionAdaptes.get(sectionIndex);
             for (int itemPositionInSection = 0;itemPositionInSection < adapter.getItemCount();itemPositionInSection++) {
                 sectionForPosition.add(sectionIndex);
                 positionWithinSection.add(itemPositionInSection);
                 itemCount++;
             }
        }
    }

    class SectionDataObserver extends RecyclerView.AdapterDataObserver{
        @Override
        public void onChanged() {
            setupIndices();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            setupIndices();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            setupIndices();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            setupIndices();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            setupIndices();
        }
    }
}
