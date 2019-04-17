package com.jarvis.grouprecycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * StickyHeader对应的ItemDecoration
 * @author jarvis
 */
public class StickyHeaderItemDecoration extends RecyclerView.ItemDecoration implements IStickyHeaderDecoration {

	public static final int HORIZONTAL = DividerItemDecoration.HORIZONTAL;
	public static final int VERTICAL = DividerItemDecoration.VERTICAL;
	private Rect mStickyHeaderRect     = null;
	private int  mStickyHeaderPosition = -1;
    private RecyclerView.ItemDecoration mInnerItemDecoration;

    public StickyHeaderItemDecoration(){
	}

	public StickyHeaderItemDecoration(Context context, int orientation) {
		mInnerItemDecoration = new DividerItemDecoration(context,orientation);
	}

	public StickyHeaderItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
		mInnerItemDecoration = itemDecoration;
	}

	@Override
	public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		if (mInnerItemDecoration!=null) {
			mInnerItemDecoration.onDraw(c,parent,state);
		} else {
			super.onDraw(c, parent, state);
		}
	}

	/**
	 * 把要固定的View绘制在上层
	 */
	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDrawOver(c, parent, state);
		//确保是PinnedHeaderAdapter的adapter,确保有View
		if (parent.getAdapter() instanceof GroupRecycleViewAdapter && parent.getChildCount() > 0) {
			GroupRecycleViewAdapter adapter = (GroupRecycleViewAdapter) parent.getAdapter();
			//找到要固定的pin view
			View firstView = parent.getChildAt(0);
			int firstAdapterPosition = parent.getChildAdapterPosition(firstView);
			int stickyHeaderPosition = getStickyHeaderViewPosition(firstAdapterPosition, adapter);
			mStickyHeaderPosition = stickyHeaderPosition;
			if (stickyHeaderPosition != -1) {
				RecyclerView.ViewHolder pinnedHeaderViewHolder = adapter.onCreateViewHolder(parent,
																							adapter.getItemViewTypeInSection(stickyHeaderPosition));
				adapter.onBindViewHolder(pinnedHeaderViewHolder, stickyHeaderPosition);
				//要固定的view
				View pinnedHeaderView = pinnedHeaderViewHolder.itemView;
				ensurePinnedHeaderViewLayout(pinnedHeaderView, parent);
				int sectionPinOffset = 0;
				for (int index = 0; index < parent.getChildCount(); index++) {
					if (adapter.isStickyPosition(parent.getChildAdapterPosition(parent.getChildAt(index)))) {
						View sectionView = parent.getChildAt(index);
						int sectionTop = sectionView.getTop();
						int pinViewHeight = pinnedHeaderView.getHeight();
						if (sectionTop < pinViewHeight && sectionTop > 0) {
							sectionPinOffset = sectionTop - pinViewHeight;
						}
					}
				}
				int saveCount = c.save();
				RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) pinnedHeaderView.getLayoutParams();
				if (layoutParams == null) {
					throw new NullPointerException("PinnedHeaderItemDecoration");
				}
				c.translate(layoutParams.leftMargin, sectionPinOffset);
				c.clipRect(0, 0, pinnedHeaderView.getMeasuredWidth(), pinnedHeaderView.getMeasuredHeight());
				pinnedHeaderView.draw(c);
				c.restoreToCount(saveCount);
				if (mStickyHeaderRect == null) {
					mStickyHeaderRect = new Rect();
				}
				mStickyHeaderRect.set(0, 0, pinnedHeaderView.getMeasuredWidth(), pinnedHeaderView.getMeasuredHeight() + sectionPinOffset);
			} else {
				mStickyHeaderRect = null;
			}

		}
	}

	/**
	 * 要给每个item设置间距主要靠这个函数来实现
	 */
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		if (mInnerItemDecoration!=null) {
			mInnerItemDecoration.getItemOffsets(outRect,view,parent,state);
		} else {
			super.getItemOffsets(outRect, view, parent, state);
		}
	}

	/**
	 * 根据第一个可见的adapter的位置去获取临近的一个要固定的position的位置
	 *
	 * @param adapterFirstVisible 第一个可见的adapter的位置
	 * @return -1：未找到 >=0 找到位置
	 */
	private int getStickyHeaderViewPosition(int adapterFirstVisible, GroupRecycleViewAdapter adapter) {
		for (int index = adapterFirstVisible; index >= 0; index--) {
			if (adapter.isStickyPosition(index)) {
				return index;
			}
		}
		return -1;
	}

	private void ensurePinnedHeaderViewLayout(View pinView, RecyclerView recyclerView) {
		if (pinView.isLayoutRequested()) {
			/**
			 * 用的是RecyclerView的宽度测量，和RecyclerView的宽度一样
			 */
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) pinView.getLayoutParams();
			if (layoutParams == null) {
				throw new NullPointerException("PinnedHeaderItemDecoration");
			}
			int widthSpec = View.MeasureSpec.makeMeasureSpec(
				recyclerView.getMeasuredWidth() - layoutParams.leftMargin - layoutParams.rightMargin, View.MeasureSpec.EXACTLY);
			/*int widthSpec;
			if (layoutParams.width > 0) {
				widthSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.width, View.MeasureSpec.EXACTLY);
			} else {
				widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			}*/
			int heightSpec;
			if (layoutParams.height > 0) {
				heightSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY);
			} else {
				heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			}
			pinView.measure(widthSpec, heightSpec);
			pinView.layout(0, 0, pinView.getMeasuredWidth(), pinView.getMeasuredHeight());
		}
	}

	@Override
	public Rect getStickyHeaderRect() {
		return mStickyHeaderRect;
	}

	@Override
	public int getStickyHeaderPosition() {
		return mStickyHeaderPosition;
	}
}
