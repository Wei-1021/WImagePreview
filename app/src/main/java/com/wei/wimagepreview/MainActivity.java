package com.wei.wimagepreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.wei.wimagepreviewlib.WImagePreviewBuilder;
import com.wei.wimagepreviewlib.utils.WAnim;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

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
                    .setAnim(WAnim.ALL_OUTSIDE_SCALE)
                    .start();
        });
    }

}