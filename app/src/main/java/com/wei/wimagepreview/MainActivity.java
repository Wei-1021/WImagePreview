package com.wei.wimagepreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.WindowCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.wei.wimagepreviewlib.WImagePreviewBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        // 透明状态栏
        window.setStatusBarColor(Color.TRANSPARENT);
        // 沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false);
        WindowCompat.getInsetsController(window, window.getDecorView())
                .setAppearanceLightNavigationBars(false);

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
                    .start();
        });
    }

}