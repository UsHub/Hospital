package com.gameex.dw.hospital.bottomBar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gameex.dw.hospital.R;
import com.gameex.dw.hospital.dao.RegistrationDao;
import com.gameex.dw.hospital.daoImpl.RegistrationDaoImpl;
import com.gameex.dw.hospital.entity.Information;
import com.gameex.dw.hospital.titleBar.TitleBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NavigationFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM = "flag";

    private RegistrationDao mDao;
    private String param;
    private TextView fragmentText;
    private GridView mGridView;
    private static RecyclerView mRecyclerView;
    private static DashAdapter mAdapter;
    private View mView;
    private Object[] mObjects = {R.drawable.reg_num_bg, R.drawable.reg_make_bg, R.drawable.reg_end_bg,
            R.drawable.reg_med_bg, R.drawable.mon_log_bg, R.drawable.med_bg,
            R.drawable.mon_bg, R.drawable.doc_bg, R.drawable.user_bg,
            R.drawable.rol_bg, R.drawable.res_bg, R.drawable.pwd_bg};
    private String[] mTitle = {"挂号信息管理", "住院办理", "住院结算",
            "在院发药", "检查收费项目登记", "药品管理",
            "收费项目管理", "门诊医生管理", "用户管理",
            "角色管理", "资源管理", "密码管理"};
    private List<Map<String, Object>> mapList;
    private List<Information> mList = new ArrayList<>();

    private boolean mShouldScroll;  //目标项是否在最后一个可见项之后
    private int mToPosition;    //记录目标项位置
    private SmoothScrollReceiver mReceiver;

    public NavigationFragment() {
    }

    public static NavigationFragment newInstance(String param) {
        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, param);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new SmoothScrollReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.gameex.dw.hospital.bottomBar.SMOOTH_SCROLL");
        filter.addAction("com.gameex.dw.hospital.bottomBar.NOTIFY_INSERT_DATA");
        filter.addAction("com.gameex.dw.hospital.bottomBar.NOTIFY_SEARCH_DATA");
        filter.addAction("com.gameex.dw.hospital.bottomBar.NOTIFY_UPDATE_DATA");
        BottomBarActivity.sBottomBarActivity.registerReceiver(mReceiver, filter);
        if (getArguments() != null) {
            param = getArguments().getString(ARG_PARAM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (param.equals("0")) {
            mView = inflater.inflate(R.layout.fragment_home_layout, container, false);
            return mView;
        } else if (param.equals("1")) {
            mView = inflater.inflate(R.layout.fragment_dash_layout, container, false);
            return mView;
        } else {
            mView = inflater.inflate(R.layout.fragment_other_layout, container, false);
            return mView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BottomBarActivity.sBottomBarActivity.unregisterReceiver(mReceiver);
    }

    private void initView() {
        switch (param) {
            case "0":
                mGridView = mView.findViewById(R.id.home_grid);
                mGridView.setAdapter(new SimpleAdapter(BottomBarActivity.sBottomBarActivity, getMapList(),
                        R.layout.fragment_home_item, new String[]{"icon", "title"},
                        new int[]{R.id.home_img, R.id.home_text}));
                mGridView.setOnItemClickListener(this);
                break;
            case "1":
                DefaultItemAnimator animator=new DefaultItemAnimator();
                animator.setAddDuration(500);
                animator.setChangeDuration(500);
                animator.setMoveDuration(500);
                animator.setRemoveDuration(500);
                mRecyclerView = mView.findViewById(R.id.dash_recycler);
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                            mShouldScroll = false;
                            smoothMovetoPosition(mRecyclerView, mToPosition);
                        }
                    }
                });
                mRecyclerView.setItemAnimator(animator);
                mList = getList();
                mAdapter = new DashAdapter(mList, BottomBarActivity.sBottomBarActivity);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(
                        BottomBarActivity.sBottomBarActivity));
                mRecyclerView.setAdapter(mAdapter);
                break;
            case "2":
                fragmentText = mView.findViewById(R.id.fragment_text);
                fragmentText.setText("敬请期待");
                break;
            default:
                break;
        }
    }

    private List<Map<String, Object>> getMapList() {
        mapList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icon", mObjects[i]);
            map.put("title", mTitle[i]);
            mapList.add(map);
        }
        return mapList;
    }

    private List<Information> getList() {
        mDao = new RegistrationDaoImpl();
        return mDao.selectAll();
    }

    /**
     * 滚动到指定item，并置顶
     */
    private void smoothMovetoPosition(RecyclerView view, final int position) {
        //第一个可见位置
        int firstItem = view.getChildLayoutPosition(view.getChildAt(0));
        //最后一个可见位置
        int lastItem = view.getChildLayoutPosition(view.getChildAt(view.getChildCount() - 1));
        if (position < firstItem) {
            //第一种可能：跳转位置在第一个可见位置之前
            view.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            //第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < view.getChildCount()) {
                int top = view.getChildAt(movePosition).getTop();
                view.smoothScrollBy(0, top);
            }
        } else {
            //第三种可能：跳转位置在最后可见项之后
            view.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    /**
     * 更新RecyclerView
     */
    private void updateRecView(List<Information> list, int position) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        smoothMovetoPosition(mRecyclerView, position);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent("com.gameex.dw.hoapital.selectItemTitle");
        intent.putExtra("title", mapList.get(i).get("title").toString());
        BottomBarActivity.sBottomBarActivity.sendBroadcast(intent);
        Toast.makeText(BottomBarActivity.sBottomBarActivity,
                mapList.get(i).get("title").toString(), Toast.LENGTH_SHORT).show();
    }

    public class SmoothScrollReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "com.gameex.dw.hospital.bottomBar.SMOOTH_SCROLL":
                    int pos = intent.getIntExtra("position", 0);
                    if (pos != -1) {
                        smoothMovetoPosition(mRecyclerView, pos);
                    } else {
                        smoothMovetoPosition(mRecyclerView, pos + 1);
                    }
                    break;
                case "com.gameex.dw.hospital.bottomBar.NOTIFY_INSERT_DATA":
                    updateRecView(getList(), mAdapter.getItemCount());
                    break;
                case "com.gameex.dw.hospital.bottomBar.NOTIFY_SEARCH_DATA":
                    updateRecView((List<Information>) intent.getSerializableExtra("search"), 0);
                    break;
                case "com.gameex.dw.hospital.bottomBar.NOTIFY_UPDATE_DATA":
                    updateRecView(getList(), intent.getIntExtra("update_to_position", 0));
                    break;
                default:
                    break;
            }
        }
    }
}
