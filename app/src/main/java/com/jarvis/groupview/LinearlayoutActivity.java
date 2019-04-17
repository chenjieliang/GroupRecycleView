package com.jarvis.groupview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jarvis.grouprecycler.GroupRecycleViewAdapter;
import com.jarvis.grouprecycler.StickyHeaderItemDecoration;
import com.jarvis.groupview.adapter.LinearInfoSectionAdapter;
import com.jarvis.groupview.entity.ColumnField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jarvis
 */
public class LinearlayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        GroupRecycleViewAdapter groupAdapter = new GroupRecycleViewAdapter();
        LinearInfoSectionAdapter sectionAdapter1 = new LinearInfoSectionAdapter();
        sectionAdapter1.setHeader("基本信息");
        List<ColumnField> columnFields1 = new ArrayList<>();
        columnFields1.add(new ColumnField("标题","测试"));
        columnFields1.add(new ColumnField("记录时间","2019-04-16 15：36"));
        columnFields1.add(new ColumnField("记录人员","陈小"));
        columnFields1.add(new ColumnField("信息内容","卫生检查不合格"));
        sectionAdapter1.setContentData(columnFields1);
        sectionAdapter1.setFooter("结束");
        LinearInfoSectionAdapter sectionAdapter2 = new LinearInfoSectionAdapter();
        sectionAdapter2.setHeader("工作票信息");
        List<ColumnField> columnFields2 = new ArrayList<>();
        for (int i = 0;i<10;i++) {
            columnFields2.add(new ColumnField("工作票信息" + i, "工作票" + i));
        }
        sectionAdapter2.setContentData(columnFields2);
        sectionAdapter2.setFooter("结束");
        LinearInfoSectionAdapter sectionAdapter3 = new LinearInfoSectionAdapter();
        sectionAdapter3.setHeader("多媒体信息");
        List<ColumnField> columnFields3 = new ArrayList<>();
        for (int i = 0;i<10;i++) {
            columnFields3.add(new ColumnField("多媒体文件" + i, "多媒体" + i));
        }
        sectionAdapter3.setContentData(columnFields3);

        groupAdapter.addSectionAdapter(sectionAdapter1);
        groupAdapter.addSectionAdapter(sectionAdapter2);
        groupAdapter.addSectionAdapter(sectionAdapter3);
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //标题悬浮吸顶 + 分割线
        recyclerView.addItemDecoration(new StickyHeaderItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
