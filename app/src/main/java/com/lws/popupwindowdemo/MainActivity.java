package com.lws.popupwindowdemo;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPopDown;
    private CardView cvMain;
    private RelativeLayout rlMain;
    private FloatingActionButton fabStart;
    private FloatingActionButton fadEnd;

    private PopupWindow myPop;
    private View view, picView01, picView02, picView03, picView04;
    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlMain = findViewById(R.id.rl_main);
        Button btnPopPicSelect = findViewById(R.id.btn_pop_pic_select);
        Button btnPopQq = findViewById(R.id.btn_pop_qq);
        Button btnPagerCenter = findViewById(R.id.btn_pager_center);
        btnPopDown = findViewById(R.id.btn_pop_down);
        fabStart = findViewById(R.id.flb_pop_start);
        fadEnd = findViewById(R.id.flb_pop_end);
        cvMain = findViewById(R.id.cv_pop);

        btnPopPicSelect.setOnClickListener(this);
        btnPopQq.setOnClickListener(this);
        btnPagerCenter.setOnClickListener(this);
        fabStart.setOnClickListener(this);
        fadEnd.setOnClickListener(this);
        btnPopDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_pop_pic_select:
                showPicSelect();
                break;
            case R.id.btn_pop_qq:
                showQq();
                break;
            case R.id.btn_pager_center:
                showPager();
                break;
            case R.id.btn_pop_down:
                showDown();
                break;
            case R.id.ll_pic://照片选择 灰色背景
            case R.id.btn_pic_cancel://取消
                myPop.dismiss();
                break;
            case R.id.btn_pic_camera://相机进行拍摄
                Toast.makeText(this, "相机拍照", Toast.LENGTH_SHORT).show();
                myPop.dismiss();
                break;
            case R.id.btn_pic_photo://相册选择
                Toast.makeText(this, "相册选择", Toast.LENGTH_SHORT).show();
                myPop.dismiss();
                break;
            case R.id.tv_be_top:
                Toast.makeText(this, "置顶成功", Toast.LENGTH_SHORT).show();
                myPop.dismiss();
                break;
            case R.id.tv_delete:
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                myPop.dismiss();
                break;
            case R.id.flb_pop_start:
                showStart();
                break;
            case R.id.flb_pop_end:
                showEnd();
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        if (myPop.isShowing()) {
            myPop.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (views != null) {
            views.remove(picView01);
            views.remove(picView02);
            views.remove(picView03);
            views.remove(picView04);
        }
        if (myPop.isShowing()) {
            myPop.dismiss();
        }
    }

    /**
     * 照片选择器
     * 实现阴影效果的思路:
     * 为 PopupWindow 设置一个半透明的背景色，然后监听背景 layout 的点击事件和物理键的返回事件，否则会出现点击无效果的现象。
     */
    private void showPicSelect() {
        view = LayoutInflater.from(this).inflate(R.layout.item_pic_select, null, false);
        LinearLayout llPop = view.findViewById(R.id.ll_pic);
        Button btnCamera = view.findViewById(R.id.btn_pic_camera);
        Button btnPhoto = view.findViewById(R.id.btn_pic_photo);
        Button btnCancel = view.findViewById(R.id.btn_pic_cancel);

        btnCamera.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        llPop.setOnClickListener(this);

        myPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // 是否可以点击 PopupWindow 外部进行隐藏 PopupWindow，必须设置该方法
        myPop.setBackgroundDrawable(new ColorDrawable());
        myPop.showAtLocation(rlMain, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 仿qq 产生水滴按钮
     */
    private void showQq() {
        view = LayoutInflater.from(this).inflate(R.layout.item_qq, null, false);
        TextView tvTop = view.findViewById(R.id.tv_be_top);
        TextView tvDelete = view.findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(this);
        tvTop.setOnClickListener(this);

        myPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 是否可以点击 PopupWindow 外部进行隐藏 PopupWindow，必须设置该方法
        myPop.setBackgroundDrawable(new ColorDrawable());
        myPop.setOutsideTouchable(true);
        // 获取 PopupWindow 的长宽
        myPop.getContentView().measure(0, 0);
        int xOffset = (cvMain.getWidth() - myPop.getContentView().getMeasuredWidth()) / 2;
        int yOffset = -(cvMain.getHeight() + myPop.getContentView().getMeasuredHeight());
        myPop.showAsDropDown(cvMain, xOffset, yOffset);
    }

    /**
     * 轮播效果
     */
    private void showPager() {
        views = new ArrayList<>();
        view = LayoutInflater.from(this).inflate(R.layout.item_pager, null, false);
        ViewPager vpPop = view.findViewById(R.id.vp_pop);
        picView01 = LayoutInflater.from(this).inflate(R.layout.item_pop_vp_01, null, false);
        picView02 = LayoutInflater.from(this).inflate(R.layout.item_pop_vp_02, null, false);
        picView03 = LayoutInflater.from(this).inflate(R.layout.item_pop_vp_03, null, false);
        picView04 = LayoutInflater.from(this).inflate(R.layout.item_pop_vp_04, null, false);

        views.add(picView01);
        views.add(picView02);
        views.add(picView03);
        views.add(picView04);
        vpPop.setAdapter(new MyPopAdapter());

        myPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myPop.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myPop.setElevation(5);
        }
        myPop.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        myPop.showAtLocation(rlMain, Gravity.CENTER, 0, 0);
    }

    /**
     * 向下弹出
     */
    private void showDown() {
        view = LayoutInflater.from(this).inflate(R.layout.item_anywhere, null, false);

        myPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myPop.setBackgroundDrawable(new ColorDrawable());
        myPop.setOutsideTouchable(true);
        myPop.getContentView().measure(0, 0);
        myPop.showAsDropDown(btnPopDown, -((myPop.getContentView().getMeasuredWidth() - btnPopDown.getWidth()) / 2), 0);
    }

    /**
     * 向左弹出
     */
    private void showStart() {
        view = LayoutInflater.from(this).inflate(R.layout.item_pop_start, null, false);

        myPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myPop.setBackgroundDrawable(new ColorDrawable());
        myPop.setOutsideTouchable(true);
        myPop.getContentView().measure(0, 0);
        int xOffset = -(myPop.getContentView().getMeasuredWidth());
        int yOffset = -(fabStart.getHeight() / 2 + myPop.getContentView().getMeasuredHeight());
        myPop.showAsDropDown(fabStart, xOffset, yOffset);
    }

    private void showEnd() {
        view = LayoutInflater.from(this).inflate(R.layout.item_end_input, null, false);

        myPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myPop.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myPop.setElevation(10);
        }
        myPop.setOutsideTouchable(true);
        // 设置该方法，否则 在切换EditText 的时候只是光标进行了移动，但是 无法召唤软键盘。
        myPop.setFocusable(true);
        myPop.getContentView().measure(0, 0);
        myPop.showAsDropDown(fadEnd, (int) (fadEnd.getWidth() * 1.3), -((fadEnd.getHeight() + myPop.getContentView().getMeasuredHeight()) / 2));
    }

    /**
     * 配置  adapter
     */
    class MyPopAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    }
}
