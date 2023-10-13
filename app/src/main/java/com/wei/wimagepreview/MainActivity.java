package com.wei.wimagepreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.wei.wimagepreviewlib.WImagePreviewBuilder;
import com.wei.wimagepreviewlib.entity.WMenuItemInfo;
import com.wei.wimagepreviewlib.listener.OnMenuItemListener;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.transformer.PageTransformer;
import com.wei.wimagepreviewlib.utils.WAnim;
import com.wei.wimagepreviewlib.utils.WIcon;
import com.wei.wimagepreviewlib.wight.WIconText;
import com.wei.wimagepreviewlib.wight.WRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.wei.wimagepreview.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WIconText wIconText = findViewById(R.id.wicon_text_1);
        wIconText.setTextSize(30);
        wIconText.setTextColor(Color.WHITE);
        wIconText.setIconTextString(WIcon.ANT_FILE_TEXT);

        AppCompatImageView imageView = findViewById(R.id.image_view);
        Glide.with(this)
                .load(getString(R.string.banner_image_1))
                .into(imageView);
        imageView.setOnClickListener(v -> {
            String[] imageList = getResources().getStringArray(R.array.image_list);

            List<WMenuItemInfo> wMenuItemInfoList = new ArrayList<>();
            WMenuItemInfo wMenuItemInfo1 = new WMenuItemInfo();
            wMenuItemInfo1.setIcon(WIcon.ANT_DOWNLOAD);
            wMenuItemInfo1.setName("保存");
            wMenuItemInfo1.setOnMenuItemListener(new OnMenuItemListener() {
                @Override
                public void onClick(WRecyclerView recyclerView, Object obj, int position) {
                    super.onClick(recyclerView, obj, position);

                    Log.i(TAG, "onClick: " + obj + ", " + position);
                    recyclerView.setInvisible();
                }
            });
            wMenuItemInfoList.add(wMenuItemInfo1);

            WImagePreviewBuilder.load(MainActivity.this)
                    .setData(Arrays.asList(imageList))
                    .setPosition(0)
                    .setOffscreenPageLimit(1)
                    .setInfiniteLoop(true)
                    .setPageTransformer(PageTransformer.PAGE_TRANSFORM_DEPTH)
                    .setAnim(WAnim.ALL_OUTSIDE_SCALE)
                    .setMoreMenu(wMenuItemInfoList)
                    .setOnPageListener(new OnPageListener() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            Log.i(TAG, "onPageSelected: " + position);
                        }
                    })
                    .start();
        });
    }

}