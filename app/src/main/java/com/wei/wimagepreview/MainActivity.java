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
                    .setPageTransformer(PageTransformer.PAGE_TRANSFORM_DEPTH)
                    .setAnim(WAnim.ALL_OUTSIDE_SCALE)
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