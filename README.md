# WImagePreview
本项目是基于ViewPager2构建的一个Android图片预览组件\
[![](https://jitpack.io/v/Wei-1021/WImagePreview.svg)](https://jitpack.io/#Wei-1021/WImagePreview)

# 如何导入
## 1.在项目根目录下的build.gradle添加（7.0以上的在settings.gradle中添加）
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
## 2.引入依赖
```
dependencies {
    implementation 'com.github.Wei-1021:WImagePreview:v1.0.0'
}
```

# 使用方式
#### 以下为使用示例，不代表所有功能：
```
WImagePreviewBuilder
    .load(MainActivity.this)
    .setData(imageList)
    .setPosition(position)
    .setOnPageListener(new OnPageListener() {
        @Override
        public void onClick(Object o, int position) {
            super.onClick(o, position);
        }
    })
    .start();
```
