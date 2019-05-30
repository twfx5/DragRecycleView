package com.wzh.dragrecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wzh.dragrecycleview.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemTouchHelperCallback.OnItemPositionChangeListener {

    private static final int IMAGE_ITEM = 0;
    private static final int ICON_ADD_ITEM = 1;

    private int maxSize; // 显示图片张数
    private List<Integer> mDatas = new ArrayList<>();
    private Context mContext;
    private OnAddListener mAddListener;
    private OnDeleteListener mDeleteListener;
    private OnItemClickListener mItemClickListener;
    private int width;

    public SelectedPhotoAdapter(Context context, List<Integer> datas) {
        mDatas = datas;
        mContext = context;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE));
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
            width = (metrics.widthPixels - DisplayUtil.dp2px(42)) / 3;
        }
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
        void onItemLongClickListener(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnDeleteListener {
        void onDelete(int pos);
    }

    public void setDeleteListener(OnDeleteListener listener) {
        mDeleteListener = listener;
    }

    public interface OnAddListener {
        void add(int pos);
    }

    public void setAddListener(OnAddListener mAddListener) {
        this.mAddListener = mAddListener;
    }

    public void setDatas(List<Integer> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IMAGE_ITEM) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_photo_image, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_photo_add, parent, false);
            return new DefaultViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.size() == 0) {
            return ICON_ADD_ITEM;
        } else if (mDatas.size() < maxSize) {
            if (position <= mDatas.size() - 1) {
                return IMAGE_ITEM;
            } else {
                return ICON_ADD_ITEM;
            }
        } else {
            return IMAGE_ITEM;
        }
    }

    public int getSpanSize(int pos) {
        return 4;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
        if (holder instanceof MyViewHolder) {
            int id = mDatas.get(position);
            ((MyViewHolder)(holder)).ivImage.setLayoutParams(layoutParams);
            ((MyViewHolder)(holder)).ivImage.setImageResource(id);
        } else if (holder instanceof DefaultViewHolder){
            ((DefaultViewHolder)(holder)).ivAdd.setLayoutParams(layoutParams);
            ((DefaultViewHolder)(holder)).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAddListener != null) {
                        mAddListener.add(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = mDatas.size();
        if (count >= maxSize) {
            return maxSize;
        } else {
            return count + 1;
        }
    }

    // 根据用户的手势，交换Adapter数据集中item的位置
    @Override
    public boolean onItemMove(int fromPos, int toPos) {
        Collections.swap(mDatas, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
        return true;
    }

//    public void addData(String data, int pos) {
//        mDatas.add(pos, data);
//        notifyItemInserted(pos);
//    }

//    public void removeData(int pos) {
//        mDatas.remove(pos);
//        notifyDataSetChanged();
//        notifyItemRemoved(pos);
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        MyViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAdd;

        DefaultViewHolder(View itemView) {
            super(itemView);
            ivAdd = itemView.findViewById(R.id.iv_add);
        }
    }
}
