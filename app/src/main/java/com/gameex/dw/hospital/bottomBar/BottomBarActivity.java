package com.gameex.dw.hospital.bottomBar;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gameex.dw.hospital.R;
import com.gameex.dw.hospital.entity.Information;
import com.gameex.dw.hospital.updateAndRegnum.UpdateRegnumActivity;
import com.gameex.dw.hospital.dao.RegistrationDao;
import com.gameex.dw.hospital.daoImpl.RegistrationDaoImpl;
import com.gameex.dw.hospital.managePack.BaseActivity;
import com.gameex.dw.hospital.titleBar.OnViewClick;
import com.gameex.dw.hospital.titleBar.TitleBarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BottomBarActivity extends BaseActivity implements View.OnClickListener {
    public static BottomBarActivity sBottomBarActivity;

    private long exitTime;
    private ConstraintLayout mLayout;
    public static int[] ints;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private String mTabTwoTitle = "挂号信息管理";
    private List<Fragment> mFragments;
    private SelectItemReceiver mReceiver;
    private TitleBarView mTitleBV;
    private ImageView rightIV;
    private PopupWindow mPopupWindow;
    private LinearLayout searchLL, registerLL, deleteLL;
    private PopupWindow mSearchPop;
    private EditText hosR_idET, d_nameET, d_keshiET;
    private Button searchBtn, cleanBtn;

    public String getTabTwoTitle() {
        return mTabTwoTitle;
    }

    public void setTabTwoTitle(String tabTwoTitle) {
        mTabTwoTitle = tabTwoTitle;
    }

    private ViewPager.OnPageChangeListener mPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.navigation_notifications);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTitle.setText(R.string.title_home);
//                    mViewPager.setCurrentItem(0);
                    clickTabOne();
                    return true;
                case R.id.navigation_dashboard:
                    clickTabTwo();
                    return true;
                case R.id.navigation_notifications:
                    clickTabThree();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        sBottomBarActivity = this;

        mReceiver = new SelectItemReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.gameex.dw.hoapital.selectItemTitle");
        filter.addAction("com.gameex.dw.hospital.bottomBar.GO_UPDATE");
        registerReceiver(mReceiver, filter);

        getWH();
        initView();
        initListener();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(sBottomBarActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                BottomBarActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 绑定组件id
     */
    private void initView() {
        mLayout = findViewById(R.id.container);
        mTitleBV = findViewById(R.id.tile_bar_view);
        rightIV = findViewById(R.id.iv_right);
        mViewPager = findViewById(R.id.view_page);
        navigation = findViewById(R.id.navigation);
    }

    /**
     * 加载监听器
     */
    private void initListener() {
        mTitleBV.setOnViewClick(new OnViewClick() {
            @Override
            public void leftClick() {
                //Waiting to do something...
            }

            @Override
            public void rightClick() {
                if (mPopupWindow != null) {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    } else {
                        mPopupWindow.showAsDropDown(rightIV, -165, 10);
                    }
                }
            }
        });
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        initData();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    /**
     * 加载viewpage
     */
    private void initData() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            NavigationFragment fragment = new NavigationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("flag", String.valueOf(i));
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        getSearchPW();
        getTitlePW();
    }

    /**
     * 标题栏弹出框
     */
    private void getTitlePW() {
        View view = sBottomBarActivity.getLayoutInflater().inflate(R.layout.title_bar_menu_layout, null);
        searchLL = view.findViewById(R.id.search_ll);
        searchLL.setOnClickListener(this);
        registerLL = view.findViewById(R.id.hos_register_ll);
        registerLL.setOnClickListener(this);
        deleteLL = view.findViewById(R.id.delete_ll);
        deleteLL.setOnClickListener(this);
        mPopupWindow = new PopupWindow(view, 277, 287);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.pop_anim);
        mPopupWindow.update();
    }

    /**
     * 搜索item弹出框
     */
    private void getSearchPW() {
        View view = sBottomBarActivity.getLayoutInflater().inflate(R.layout.title_menu_search_layout, null);

        hosR_idET = view.findViewById(R.id.hosR_id_et);
        d_nameET = view.findViewById(R.id.d_name_et);
        d_keshiET = view.findViewById(R.id.d_keshi_et);
        searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
        cleanBtn = view.findViewById(R.id.clean_btn);
        cleanBtn.setOnClickListener(this);

        mSearchPop = new PopupWindow(view, ints[0] - 54, ints[1] - 522);
        mSearchPop.setFocusable(true);
        mSearchPop.setOutsideTouchable(true);
        mSearchPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowBackgroundAlpha(1f);
            }
        });
        mSearchPop.setAnimationStyle(R.style.pop_anim);
        mSearchPop.update();
    }

    /**
     * 获得屏幕的宽高
     */
    private void getWH() {
        ints = new int[2];
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        ints[0] = metrics.widthPixels;
        ints[1] = metrics.heightPixels;
    }

    /**
     * 控制窗口背景的不透明度
     */
    private void setWindowBackgroundAlpha(float alpha) {
        if (sBottomBarActivity == null) {
            return;
        }
        if (sBottomBarActivity instanceof AppCompatActivity) {
            Window window = ((AppCompatActivity) sBottomBarActivity).getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.alpha = alpha;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画
     */
    private void showBackgroundAnimator(float mAlpha) {
        if (mAlpha >= 1f) {
            return;
        }
        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, mAlpha);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float alpha = (float) animator.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private void clickTabOne() {
        //为防止隔页切换时，滑过中间页面的问题，去除页面切换缓慢滑动的动画效果
        mViewPager.setCurrentItem(0);
        mTitleBV.setTitle("功能选择");
        mTitleBV.setTitleSize(23);
        mTitleBV.setRightIVVisible(View.GONE);
        mTitleBV.setLeftIVVisible(View.GONE);
        navigation.getMenu().getItem(0).setIcon(R.drawable.home_select);
        navigation.getMenu().getItem(1).setIcon(R.drawable.mid_normal);
        navigation.getMenu().getItem(2).setIcon(R.drawable.mine_normal);
    }

    private void clickTabTwo() {
        mViewPager.setCurrentItem(1);
        if (getTabTwoTitle() != null) {
            mTitleBV.setTitle(getTabTwoTitle());
        }
        mTitleBV.setTitleSize(23);
        mTitleBV.setRightIVVisible(View.VISIBLE);
        mTitleBV.setLeftIVVisible(View.GONE);
        navigation.getMenu().getItem(1).setIcon(R.drawable.mid_select);
        navigation.getMenu().getItem(0).setIcon(R.drawable.home_normal);
        navigation.getMenu().getItem(2).setIcon(R.drawable.mine_normal);
    }

    private void clickTabThree() {
        mViewPager.setCurrentItem(2);
        mTitleBV.setTitle("敬请期待");
        mTitleBV.setTitleSize(23);
        mTitleBV.setRightIVVisible(View.GONE);
        mTitleBV.setLeftIVVisible(View.GONE);
        navigation.getMenu().getItem(2).setIcon(R.drawable.mine_select);
        navigation.getMenu().getItem(0).setIcon(R.drawable.home_normal);
        navigation.getMenu().getItem(1).setIcon(R.drawable.mid_normal);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_ll:
                if (mSearchPop != null) {
                    mSearchPop.showAtLocation(mLayout, Gravity.CENTER, 0, 0);
                    showBackgroundAnimator(0.5f);
                }
                mPopupWindow.dismiss();
                break;
            case R.id.hos_register_ll:
                mPopupWindow.dismiss();
                Intent intent = new Intent(this, UpdateRegnumActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_ll:
                Toast.makeText(sBottomBarActivity, "退号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_btn:
                Toast.makeText(sBottomBarActivity, "正在查询……", Toast.LENGTH_SHORT).show();
                RegistrationDao dao = new RegistrationDaoImpl();
                String hosR_idStr = hosR_idET.getText().toString();
                Integer hosR_id = null;
                if (!hosR_idStr.isEmpty()) {
                    hosR_id = Integer.parseInt(hosR_idStr);
                }
                List<Information> list = dao.selectInformation(hosR_id,
                        d_nameET.getText().toString(), d_keshiET.getText().toString());
                if (list.size() > 0) {
                    Intent intentSearch = new Intent("com.gameex.dw.hospital.bottomBar.NOTIFY_SEARCH_DATA");
                    intentSearch.putExtra("search", (Serializable) list);
                    sendBroadcast(intentSearch);
                    mSearchPop.dismiss();
                } else {
                    Toast.makeText(sBottomBarActivity, "病人不存在", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clean_btn:
                hosR_idET.setText("");
                d_nameET.setText("");
                d_keshiET.setText("");
                break;
            default:
                break;
        }
    }

    class SelectItemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "com.gameex.dw.hoapital.selectItemTitle":
                    navigation.getMenu().getItem(1).setTitle(intent.getStringExtra("title"));
                    setTabTwoTitle(intent.getStringExtra("title"));
                    navigation.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case "com.gameex.dw.hospital.bottomBar.GO_UPDATE":
//                    getUpdatePop();
//                    mUpdatePop.showAtLocation(mLayout, Gravity.CENTER, 0, 0);
                default:
                    break;
            }
        }
    }

}
