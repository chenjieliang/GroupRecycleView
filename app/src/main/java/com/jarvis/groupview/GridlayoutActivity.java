package com.jarvis.groupview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jarvis.grouprecycler.GroupRecycleViewAdapter;
import com.jarvis.grouprecycler.SectionSpanSizeLookup;
import com.jarvis.grouprecycler.StickyHeaderItemDecoration;
import com.jarvis.groupview.adapter.GridInfoSectionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jarvis
 */
public class GridlayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        GroupRecycleViewAdapter groupAdapter = new GroupRecycleViewAdapter();
        GridInfoSectionAdapter sectionAdapter1 = new GridInfoSectionAdapter();
        List<String> labelList1 = new ArrayList<>();
        for (int i = 1 ;i < 5; i++) {
            labelList1.add(String.valueOf(i));
        }
        sectionAdapter1.setHeader("标题1");
        sectionAdapter1.setContentData(labelList1);

        GridInfoSectionAdapter sectionAdapter2 = new GridInfoSectionAdapter();
        List<String> labelList2 = new ArrayList<>();
        for (int i = 5 ;i < 10; i++) {
            labelList2.add(String.valueOf(i));
        }
        sectionAdapter2.setHeader("标题2");
        sectionAdapter2.setContentData(labelList2);

        GridInfoSectionAdapter sectionAdapter3 = new GridInfoSectionAdapter();
        List<String> labelList3 = new ArrayList<>();
        for (int i = 10 ;i < 20; i++) {
            labelList3.add(String.valueOf(i));
        }
        sectionAdapter3.setHeader("标题3");
        sectionAdapter3.setContentData(labelList3);

        GridInfoSectionAdapter sectionAdapter4 = new GridInfoSectionAdapter();
        List<String> labelList4 = new ArrayList<>();
        for (int i = 20 ;i < 100; i++) {
            labelList4.add(String.valueOf(i));
        }
        sectionAdapter4.setHeader("标题4");
        sectionAdapter4.setContentData(labelList4);

        groupAdapter.addSectionAdapter(sectionAdapter1);
        groupAdapter.addSectionAdapter(sectionAdapter2);
        groupAdapter.addSectionAdapter(sectionAdapter3);
        groupAdapter.addSectionAdapter(sectionAdapter4);

        recyclerView.setAdapter(groupAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new SectionSpanSizeLookup(groupAdapter,manager));
        recyclerView.setLayoutManager(manager);
        //标题悬浮吸顶
        recyclerView.addItemDecoration(new StickyHeaderItemDecoration());
    }
}
