package com.wei.wimagepreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.wei.wimagepreviewlib.WImagePreviewBuilder;
import com.wei.wimagepreviewlib.entity.WMenuItemInfo;
import com.wei.wimagepreviewlib.listener.OnMenuItemListener;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.transformer.PageTransformer;
import com.wei.wimagepreviewlib.utils.WAnim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.wei.wimagepreview.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatImageView imageView = findViewById(R.id.image_view);
        Glide.with(this)
                .load(getString(R.string.banner_image_1))
                .into(imageView);
        imageView.setOnClickListener(v -> {
            String[] imageList = getResources().getStringArray(R.array.image_list);

            List<WMenuItemInfo> wMenuItemInfoList = new ArrayList<>();
            WMenuItemInfo wMenuItemInfo1 = new WMenuItemInfo();
            wMenuItemInfo1.setName("保存");
            wMenuItemInfo1.setIcon(getDrawable(com.wei.wimagepreviewlib.R.drawable.ic_close_30));
            wMenuItemInfo1.setOnMenuItemListener(new OnMenuItemListener() {
                @Override
                public void onClick(View v, Object imgObj, int position) {
                    super.onClick(v, imgObj, position);

                    Log.i(TAG, "onClick: " + imgObj + ", " + position);
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