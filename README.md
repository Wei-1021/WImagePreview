# WImagePreview
本项目是基于ViewPager2构建的一个Android图片预览组件\
[![](https://jitpack.io/v/Wei-1021/WImagePreview.svg)](https://jitpack.io/#Wei-1021/WImagePreview)

# 如何导入
## 1.在项目根目录下的build.gradle添加（高版本的Gradle的在settings.gradle中添加）
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
    implementation 'com.github.Wei-1021:WImagePreview:v1.0.1'
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

# 功能
### 属性
| 方法                                                | 是否必须     | 描述                                                                                                                       |
|---------------------------------------------------|----------|--------------------------------------------------------------------------------------------------------------------------|
| load(Context context)<br/>load(Fragment fragment) | <b>是</b> | 加载图片预览组件                                                                                                                 |
| setData(List\<T\> imgList)                        | <b>是</b> | 设置图片集合数据(目前仅支持Uri和String类型的)                                                                                             |
| setPosition(int position)                         | <b>是</b> | 设置图片下标定位                                                                                                                 |
| start()                                           | <b>是</b> | 启动组件                                                                                                                     |
| setOrientation(int orientation)                   | 否        | 设置滚动方向<br/>水平滚动：ViewPager2.ORIENTATION_HORIZONTAL（默认）；<br/>垂直滚动：ViewPager2.ORIENTATION_VERTICAL。                         |
| setAllowMove(boolean isAllowImage)                | 否        | 设置是否允许滑动，设置禁止滑动后，与之相关的三个监听器（onPageScrolled、onPageSelected、onPageScrollStateChanged）将无法生效。<br/>true：允许（默认）；<br/>false：不允许 |
| setFullscreen(boolean isFullscreen)               | 否        | 设置是否全屏<br/>true：全屏（默认）；<br/>false：非全屏                                                                                    |
| setShowClose(boolean isShowClose)                 | 否        | 是否显示关闭按钮<br/>true：显示（默认）；<br/>false：不显示                                                                                  |
| setPageTransformer(int pageTransformer)           | 否        | 设置ViewPager2页面间距（默认为10）                                                                                                  |
| setOnPageListener(OnPageListener listener)           | 否        | 监听器，对应下方监听方法                                                                                               |

### 监听方法 
以下所有方法均在OnPageListener类中
| 方法                                                                               | 描述                                                               |
|----------------------------------------------------------------------------------|------------------------------------------------------------------|
| onOpen(int position)                                                             | 页面打开事件监听器<br/> position：打开页面时，显示的第一张图片的定位                        |
| onClick(Object o, int position)                                                  | 页面点击事件监听器<br/>o：图片对象（Uri或String类型） <br/> position：点击页面时，所显示图片的定位 |
| onClose(Object o, int position)                                                  | 页面关闭事件监听器<br/>o：图片对象（Uri或String类型） <br/> position：关闭页面时，所显示图片的定位 |
| onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) | 页面滑动事件监听器。请参照ViewPager2的onPageScrolled                           |
| onPageSelected(int position)                                                     | 页面选择事件监听器，打开页面，切换图片时会触发。请参照ViewPager2的onPageSelected             |
| onPageScrollStateChanged(@ViewPager2.ScrollState int state)                      | 页面滑动状态更改事件监听器。请参照ViewPager2的onPageScrollStateChanged             |
