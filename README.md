# WImagePreview
本项目是基于ViewPager2构建的一个Android图片预览组件\
[![](https://jitpack.io/v/Wei-1021/WImagePreview.svg)](https://jitpack.io/#Wei-1021/WImagePreview)\
仓库：\
[![](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Wei-1021/WImagePreview)
[![](https://img.shields.io/badge/Gitee-c71d23?style=for-the-badge&logo=gitee&logoColor=white)](https://gitee.com/weizhanjie/WImagePreview)

# 如何导入
## 1.在项目根目录下的build.gradle添加
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
## 2.引入依赖(最新的版本查看上面的jitpack标签，因为有时候会忘记更新文档)
```
dependencies {
    implementation 'com.github.Wei-1021:WImagePreview:1.4.3'
}
```

# 使用方式
### 使用示例：
以下为使用示例，不代表所有功能
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
| 方法                                                             | 是否必须     | 描述                                                                                                                                                                                                       |
|----------------------------------------------------------------|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| load(Context context)<br/>load(Fragment fragment)              | <b>是</b> | 加载图片预览组件                                                                                                                                                                                                 |
| setData(List\<T\> imgList)                                     | <b>是</b> | 设置图片集合数据                                                                                                                                                                                                 |
| setPosition(int position)                                      | <b>是</b> | 设置图片下标定位                                                                                                                                                                                                 |
| start()                                                        | <b>是</b> | 启动组件                                                                                                                                                                                                     |
| setOrientation(int orientation)                                | 否        | 设置滚动方向<br/>水平滚动：ViewPager2.ORIENTATION_HORIZONTAL（默认）；<br/>垂直滚动：ViewPager2.ORIENTATION_VERTICAL。                                                                                                         |
| setShowNumIndicator(boolean isShowNumIndicator)                | 否        | 设置是否显示数字指示器，默认显示                                                                                                                                                                                         |
| setAllowMove(boolean isAllowImage)                             | 否        | 设置是否允许滑动，设置禁止滑动后，与之相关的三个监听方法（onPageScrolled、onPageSelected、onPageScrollStateChanged）将无法生效。<br/>true：允许（默认）；<br/>false：不允许                                                                                |
| setFullscreen(boolean isFullscreen)                            | 否        | 设置是否全屏<br/>true：全屏（默认）；<br/>false：非全屏                                                                                                                                                                    |
| setShowClose(boolean isShowClose)                              | 否        | 是否显示关闭按钮<br/>true：显示（默认）；<br/>false：不显示                                                                                                                                                                  |
| setShowMenu(boolean isShowMenu)                                | 否        | 是否显示功能菜单按钮，默认不显示                                                                                                                                                                                         |
| setMoreMenu(List<WMenuItemInfo> menuItemInfoList)              | 否        | 设置功能菜单，具体内容查看<b>WMenuItemInfo</b>类                                                                                                                                                                       |
| setInfiniteLoop(boolean isInfiniteLoop)                        | 否        | 设置是否允许无限循环滑动，默认允许无限循环滑动                                                                                                                                                                                  |
| setOffscreenPageLimit(int offscreenPageLimit)                  | 否        | 设置预加载个数，默认1。<br/>offscreenPageLimit：预加载的个数                                                                                                                                                               |
| setAnim(int anim)                                              | 否        | 设置组件进出场动画。预设了八种进出场动画的组合（具体查看下方介绍）。<br/>anim：进出场动画;详见WAnim类中ALL_*格式的参数                                                                                                                                    |
| setInAnim(int enterAnim, int exitAnim)                         | 否        | 设置预览组件进场动画。<br/>enterAnim：目标页面的进场动画;详见WAnim类中IN_*格式的参数<br/>exitAnim：当前页面的退场动画;详见WAnim类中OUT_*格式的参数                                                                                                        |
| setOutAnim(int enterAnim, int exitAnim)                        | 否        | 设置预览组件退场动画。<br/>enterAnim：上一个页面的进场动画;详见WAnim类中IN_*格式的参数<br/>exitAnim：当前页面的退场动画;详见WAnim类中OUT_*格式的参数                                                                                                       |
| setPageMargin(int pageMargin)                                  | 否        | 设置ViewPager2页面间距（默认为10）<br/><b>setPageMargin()</b>和<b>setPageTransformer()</b>只能设置其中一个，如果两个都设置，则只有<b>setPageTransformer()</b>生效                                                                          |
| setPageTransformer(int pageTransformer)                        | 否        | 设置页面切换动画 <br/><b>setPageMargin()</b>和<b>setPageTransformer()</b>只能设置其中一个，如果两个都设置，则只有<b>setPageTransformer()</b>生效<br/>pageTransformer：动画类型;详见PageTransformer中的常量                                         |
| setPageTransformer(ViewPager2.PageTransformer pageTransformer) | 否        | 设置页面切换动画 <br/><b>setPageMargin()</b>和<b>setPageTransformer()</b>只能设置其中一个，如果两个都设置，则只有<b>setPageTransformer()</b>生效<br/>pageTransformer：动画类型;详见PageTransformer 或者可以通过实现ViewPager2.PageTransformer接口进行自定义动画 |
| setOnPageListener(OnPageListener listener)                     | 否        | 设置监听器，对应下方监听方法                                                                                                                                                                                           |

### 功能菜单类: WMenuItemInfo
#### 参数
| 名称                 | 介绍                                                     |
|--------------------|--------------------------------------------------------|
| name               | 名称                                                     |
| icon               | 字体图标，可以使用<b>WIcon</b>图标常量类，也可以使用自定义的图标库（必须是&#x***;的格式） |
| iconDraw           | 图片图标                                                   |
| textColor          | 字体颜色                                                   |
| background         | 背景Drawable                                             |
| backgroundColor    | 背景色                                                    |
| onMenuItemListener | 监听器                                                    |
#### 监听器：OnMenuItemListener
| 名称                                                            | 介绍   |
|---------------------------------------------------------------|------|
| onClick(WRecyclerView recyclerView, Object obj, int position) | 点击监听 |
#### 使用方法
```
List<WMenuItemInfo> wMenuItemInfoList = new ArrayList<>();
WMenuItemInfo wMenuItemInfo1 = new WMenuItemInfo("保存", WIcon.ANT_DOWNLOAD, new OnMenuItemListener() {
    @Override
    public void onClick(WRecyclerView recyclerView, Object obj, int position) {
        super.onClick(recyclerView, obj, position);
        Log.i(TAG, "onClick: " + obj + ", " + position);
        recyclerView.setInvisible();
    }
});
wMenuItemInfoList.add(wMenuItemInfo1);

WImagePreviewBuilder
    .load(MainActivity.this)
    .setData(imageList)
    .setPosition(0)
    .setMoreMenu(wMenuItemInfoList)
    .start();
```

### 进退场动画
#### 常量
| 名称                      | 介绍         |
|-------------------------|------------|
| ALL_BOTTOM_IN_TOP_OUT   | 下进上出       |
| ALL_TOP_IN_BOTTOM_OUT   | 上进下出       |
| ALL_LEFT_IN_RIGHT_OUT   | 左进右出       |
| ALL_RIGHT_IN_LEFT_OUT   | 右进左出       |
| ALL_CENTER_ZOOM         | 中间缩放       |
| ALL_OUTSIDE_SCALE       | 外围缩放（从外往里） |
| ALL_LTOP_IN_RBOTTOM_OUT | 左上进，右下出    |
| ALL_ROTATE_IN_FADE_OUT  | 旋转缩放进，透明淡出 |
##### 使用方式
```
WImagePreviewBuilder
    .load(MainActivity.this)
    .setData(imageList)
    .setPosition(position)
    .setAnim(WAnim.ALL_OUTSIDE_SCALE)
    .start();
```

### 翻页动画：PageTransformer
#### 常量 
| 名称                        | 描述     | 效果演示                    |
|---------------------------|--------|-------------------------|
| PAGE_TRANSFORM_ZOOM_OUT   | 景深     | ![景深](image/景深-min.gif) |
| PAGE_TRANSFORM_DEPTH      | 叠层     | ![叠层](image/叠层-min.gif) |
| PAGE_TRANSFORM_FLIP       | 翻转     | ![翻转](image/翻转-min.gif) |
| PAGE_TRANSFORM_PUSH       | 推压     | ![推压](image/推压-min.gif) |
| PAGE_TRANSFORM_ROTATE     | 旋转     | ![旋转](image/旋转-min.gif) |
| PAGE_TRANSFORM_SQUARE_BOX | 方块     | ![方块](image/方块-min.gif) |
| PAGE_TRANSFORM_WIND_MILL  | 风车     | ![风车](image/风车-min.gif) |
| 无                         | 无动画    | ![无动画](image/无动画.gif)   |

##### 使用方式
```
WImagePreviewBuilder
    .load(MainActivity.this)
    .setData(imageList)
    .setPosition(position)
    .setPageTransformer(PageTransformer.PAGE_TRANSFORM_DEPTH)
    .start();
```

#### 动画类
| 动画类                    | 描述  |
|------------------------|-----|
| ZoomOutPageTransformer | 景深  | 
| DepthPageTransformer   | 叠层  | 
| FlipTransformer        | 翻转  |  
| PushTransformer        | 推压  |  
| RotationTransformer    | 旋转  |  
| SquareBoxTransformer   | 方块  |  
| WindMillTransformer    | 风车  |  

也可以通过实现ViewPager2.PageTransformer接口自定义动画。如
```
public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
    @Override
        public void transformPage(View view, float position) {
            ...
        }
}
```

##### 使用方式
```
WImagePreviewBuilder
    .load(MainActivity.this)
    .setData(imageList)
    .setPosition(position)
    // 内置的动画类可以通过new PageTransformer.ZoomOutPageTransformer()方式使用
    .setPageTransformer(new ZoomOutPageTransformer()) 
    .start();
```

### 监听方法：OnPageListener
| 方法                                                                               | 描述                                                  |
|----------------------------------------------------------------------------------|-----------------------------------------------------|
| onOpen(int position)                                                             | 页面打开事件监听<br/> position：打开页面时，显示的第一张图片的定位            |
| onClick(Object o, int position)                                                  | 页面点击事件监听<br/>o：图片对象 <br/> position：点击页面时，所显示图片的定位   |
| onClose(Object o, int position)                                                  | 页面关闭事件监听<br/>o：图片对象 <br/> position：关闭页面时，所显示图片的定位   |
| onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) | 页面滑动事件监听。请参照ViewPager2的onPageScrolled               |
| onPageSelected(int position)                                                     | 页面选择事件监听，打开页面，切换图片时会触发。请参照ViewPager2的onPageSelected |
| onPageScrollStateChanged(@ViewPager2.ScrollState int state)                      | 页面滑动状态更改事件监听。请参照ViewPager2的onPageScrollStateChanged |

# 注意事项
本项目图片加载库依赖Glide，依赖版本为4.12.0，若要导入本组件，您项目中的Glide版本建议不要低于4.12.0，否则可能会因为版本冲突而出现问题。
