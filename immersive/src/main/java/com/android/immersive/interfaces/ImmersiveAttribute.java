package com.android.immersive.interfaces;

import java.util.List;

import android.support.annotation.ColorRes;
import android.view.View;

/**
 * @author KyoWang
 * @since 2018/11/15
 */
public interface ImmersiveAttribute {

  /**
   * 沉浸式状态
   *
   * @return {@link ImmersiveStatus}
   */
  int getImmersiveStatus();

  /**
   * 状态栏颜色.
   *
   * @return 返回色值
   */
  int getStatusBarColor();

  /**
   * 导航栏颜色.
   *
   * @return 返回色值
   */
  int getNavigationBarColor();

  /**
   * fitsSystemWindows属性.
   *
   * @return {@link ImmersiveBoolean}
   */
  @ImmersiveBoolean
  int getFitsSystemWindows();

  /**
   * 状态栏是否可见.
   *
   * @return {@link ImmersiveBoolean}
   */
  @ImmersiveBoolean
  int getStatusBarVisible();

  /**
   * 导航栏是否可见.
   *
   * @return {@link ImmersiveBoolean}
   */
  @ImmersiveBoolean
  int getNavigationBarVisible();

  /**
   * 导航栏图标风格.
   *
   * @return {@link ImmersiveFontStyle}
   */
  @ImmersiveFontStyle
  int getNavigationBarFontStyle();

  /**
   * 状态栏字体颜色
   *
   * @return {@link ImmersiveFontStyle}
   */
  @ImmersiveFontStyle
  int getStatusBarFontStyle();

  /**
   * 获取需要margin的views
   *
   * @return views
   */
  List<View> getMarginViews();

  /**
   * 获取需要padding的views
   *
   * @return views
   */
  List<View> getPaddingVies();
  /**
   * 获取刷新器.
   *
   * @return 刷新器
   */
  Refresher getRefresher();

  /**
   * 重置各种属性.
   */
  void recycle();

  /**
   * 属性刷新器.
   */
  interface Refresher {

    /**
     * 设置沉浸式状态
     * {@link ImmersiveStatus}
     *
     * @param status {@link ImmersiveStatus}
     * @return {@link Refresher}
     */
    Refresher setImmersiveStatus(@ImmersiveStatus int status);

    /**
     * 设置状态栏颜色
     *
     * @param color 色值
     * @return {@link Refresher}
     */
    Refresher setStatusBarColor(@ColorRes int color);

    /**
     * 设置状态栏是否可见，此方法可能会导致页面新
     *
     * @param visible {@link ImmersiveBoolean}
     * @return {@link Refresher}
     */
    Refresher setStatusBarVisible(@ImmersiveBoolean int visible);

    /**
     * 设置底部导航栏颜色.
     *
     * @param colorRes 色值
     * @return {@link Immersive}
     */
    Refresher setNavigationBarColor(@ColorRes int colorRes);

    /**
     * 设置导航栏是否可见，此方法可能会导致页面新
     *
     * @param visible {@link ImmersiveBoolean}
     * @return {@link Refresher}
     */
    Refresher setNavigationBarVisible(@ImmersiveBoolean int visible);

    /**
     * 底部导航栏图标颜色.
     *
     * @param style {@link ImmersiveFontStyle}.
     * @return {@link Immersive}
     */
    Refresher setNavigationBarIconStyle(@ImmersiveFontStyle int style);

    /**
     * 设置 StatusBar 状态栏字体颜色
     *
     * @param style {@link ImmersiveFontStyle}
     * @return {@link Refresher}
     */
    Refresher setStatusBarFontStyle(@ImmersiveFontStyle int style);

    /**
     * 代码实现android:fitsSystemWindows.
     *
     * fitsSystemWindows属性可以让view根据系统窗口来调整自己的布局
     * 简单点说就是我们在设置应用布局时是否考虑系统窗口布局，这里系统窗口包括系统状态栏、导航栏、输入法等
     * 包括一些手机系统带有的底部虚拟按键。
     *
     * android:fitsSystemWindows="true"（触发View的padding属性来给系统窗口留出空间）
     * 这个属性可以给任何view设置,只要设置了这个属性此view的其他所有padding属性失效
     * 同时该属性的生效条件是只有在设置了透明状态栏(StatusBar)或者导航栏
     * (NavigationBar)此属性才会生效。
     *
     * @param fitsSystemWindows {@link ImmersiveBoolean}
     * @return {@link Refresher}
     */
    Refresher setFitsSystemWindows(@ImmersiveBoolean int fitsSystemWindows);

    /**
     * 设置图片沉浸式需要单独设置margin的View.
     *
     * 只有在 {@link ImmersiveStatus#TRANSLUCENT_IMMERSIVE} 时候才会生效.
     *
     * @param views views集合
     * @return {@link Refresher}
     */
    Refresher setMarginViews(View... views);

    /**
     * 设置图片沉浸式需要单独设置padding的View.
     *
     * 只有在 {@link ImmersiveStatus#TRANSLUCENT_IMMERSIVE} 时候才会生效.
     *
     * @param views views集合
     * @return {@link Refresher}
     */
    Refresher setPaddingViews(View... views);

    /**
     * 提交修改属性.
     */
    void commit();
  }
}
