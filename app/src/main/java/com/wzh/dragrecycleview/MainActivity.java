package com.wzh.dragrecycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wzh.dragrecycleview.utils.DisplayUtil;
import com.wzh.dragrecycleview.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private RecyclerView rv;
    private Button bt;
    private List<Integer> mList = new ArrayList<>();
    private SelectedPhotoAdapter selectedPhotoAdapter;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mList.add(R.drawable.ic_circleoffriends);
        mList.add(R.drawable.ic_copy);
        mList.add(R.drawable.ic_dynamic);
        mList.add(R.drawable.ic_qq);
        mList.add(R.drawable.ic_qqzone);
    }

    private void initView() {
        et = findViewById(R.id.et);
        rv = findViewById(R.id.rv);
        bt = findViewById(R.id.bt);

        selectedPhotoAdapter = new SelectedPhotoAdapter(this, mList);
        selectedPhotoAdapter.setMaxSize(9);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 12);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return selectedPhotoAdapter.getSpanSize(position);
            }
        });
        rv.setLayoutManager(mLayoutManager);
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(3, DisplayUtil.dp2px(6), DisplayUtil.dp2px(6), false);
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(selectedPhotoAdapter);

        //初始化ItemTouchHelper实例
        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(selectedPhotoAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        //mItemTouchHelper关联RecyclerView
        mItemTouchHelper.attachToRecyclerView(rv);
    }
}
