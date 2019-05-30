package com.wzh.dragrecycleview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;


/**
 * 该类工作与ItemTouchHelper和你的app之间，起一个桥梁的作用
 * 主要负责，定义用户drag和swipe的方向，以及当户产生了指定手势会收到相应的回调方法
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnItemPositionChangeListener mListener;
    private RecyclerView.ViewHolder lastDragViewHolder;

    public ItemTouchHelperCallback(OnItemPositionChangeListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //如果状态为拖拽，说明选中了,需要把当前的视图放大一下
        if (viewHolder != null && actionState ==  ACTION_STATE_DRAG){
            lastDragViewHolder = viewHolder;
            viewHolder.itemView.setScaleX(1.2f);
            viewHolder.itemView.setScaleY(1.2f);
        }

        // ACTION_STATE_IDLE就是松开了，把大小改为原状
        if (lastDragViewHolder != null && actionState == ACTION_STATE_IDLE){
            lastDragViewHolder.itemView.setScaleX(1);
            lastDragViewHolder.itemView.setScaleY(1);
            lastDragViewHolder = null;
        }
    }

    /**
     * 设置运动方向
     * return a composite flag which defines the enabled move directions in each state
     * (idle, swiping, dragging).
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        if (viewHolder instanceof SelectedPhotoAdapter.DefaultViewHolder){
            dragFlags = 0; // 加号不让拖拽
        } else {
            dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                    | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        //can be dragged, can not be swiped
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mListener != null) {
            return mListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return false;
    }

    /**
     * 滑动的回调
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 用来判断 target是否可以被替换
     * @return  true :target可以被current替换
     *          false：不可以
     */
    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        if (target instanceof SelectedPhotoAdapter.DefaultViewHolder){
            return false;
        }
        return super.canDropOver(recyclerView, current, target);
    }

    public interface OnItemPositionChangeListener {
        boolean onItemMove(int fromPos, int toPos);
    }
}
