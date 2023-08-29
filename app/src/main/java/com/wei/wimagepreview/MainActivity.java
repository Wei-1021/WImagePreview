package com.wei.wimagepreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.wei.wimagepreviewlib.WImagePreviewBuilder;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.transformer.PageTransformer;
import com.wei.wimagepreviewlib.utils.WAnim;

import java.util.Arrays;

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
            WImagePreviewBuilder.load(MainActivity.this)
                    .setData(Arrays.asList(imageList))
                    .setPosition(0)
                    .setOffscreenPageLimit(1)
                    .setInfiniteLoop(true)
                    .setPageTransformer(PageTransformer.PAGE_TRANSFORM_ZOOM_OUT)
                    .setAnim(WAnim.ALL_OUTSIDE_SCALE)
                    .setOnPageListener(new OnPageListener() {
                        @Override
                        public void onOpen(int position) {
                            super.onOpen(position);
                            Log.i(TAG, "onOpen: " + position);
                        }

                        @Override
                        public void onClick(Object o, int position) {
                            super.onClick(o, position);
                            Log.i(TAG, "onClick: " + position);
                        }

                        @Override
                        public void onClose(Object o, int position) {
                            super.onClose(o, position);
                            Log.i(TAG, "onClose: " + position);
                        }

                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                            Log.i(TAG, "onPageScrolled: " + position);
                        }

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