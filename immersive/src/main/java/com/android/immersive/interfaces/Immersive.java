package com.acfun.immersive.interfaces;

import java.util.List;

import android.support.annotation.ColorRes;
import android.view.View;

/**
 * 暂时只实现了StatusBarb背景色设置.
 * <br/>
 * NavigationBar设置未实现 & StatusBar字体颜色设置暂未实现 & 刘海屏判断未实现
 * <br/>
 * 字体变色
 *
 * FLYME {@see http://open-wiki.flyme.cn/index.php
 * ?title=%E7%8A%B6%E6%80%81%E6%A0%8F%E5%8F%98%E8%89%B2}
 *
 * MIUI {@see https://dev.mi.com/console/doc/detail?pId=1159}
 *
 * <br/>
 * Build.VERSION_CODES >= 19 && Build.VERSION_CODES < 21 添加傀儡View将主View内容顶出
 * Build.VERSION_CODES > 21 直接调用系统API
 *
 * @author KyoWang
 * @since 2018/11/14
 */
public interface Immersive {

  /**
   * 设置状态栏颜色.
   *
   * @param colorRes
   * @return {@link Immersive}
   */
  Immersive setStatusBarColor(@ColorRes int colorRes);

  /**
   * 设置顶部状态栏透明.
   *
   * @return {@link Immersive}
   */
  Immersive setStatusBarTranslucent();

  /**
   * 顶部状态栏是否可见.
   *
   * @param visible
   * @return {@link Immersive}
   */
  Immersive setStatusBarVisible(boolean visible);

  /**
   * 底部导航栏是否可见.
   *
   * @param visible
   * @return {@link Immersive}
   */
  Immersive setNavigationBarVisible(boolean visible);

  /**
   * @return {@link Immersive}
   */
  Immersive realFullScreen();

  /**
   * 设置StatusBar字体颜色
   * <br/>
   * 系统23版本之后才支持，miui & flyme 单独适配
   *
   * @return
   */
  Immersive setStatusBarFontStyle(boolean isDark);

  /**
   * 代码实现android:fitsSystemWindows.
   * <br/>
   * fitsSystemWindows属性可以让view根据系统窗口来调整自己的布局
   * 简单点说就是我们在设置应用布局时是否考虑系统窗口布局，这里系统窗口包括系统状态栏、导航栏、输入法等
   * 包括一些手机系统带有的底部虚拟按键。
   *
   * android:fitsSystemWindows="true"（触发View的padding属性来给系统窗口留出空间）
   * 这个属性可以给任何view设置,只要设置了这个属性此view的其他所有padding属性失效
   * 同时该属性的生效条件是只有在设置了透明状态栏(StatusBar)或者导航栏
   * (NavigationBar)此属性才会生效。
   *
   * @param fitSystemWindows
   * @return {@link Immersive}
   */
  Immersive setRootViewFitsSystemWindows(boolean fitSystemWindows);

  /**
   * 设置 Views 的 margin
   *
   * @param views
   * @return
   */
  Immersive marginViews(List<View> views);

  /**
   * 设置 Views 的 padding
   *
   * @param views
   * @return
   */
  Immersive paddingViews(List<View> views);

  /**
   * 空实现.
   */
  Immersive EMPTY = new Immersive() {

    @Override
    public Immersive setStatusBarColor(int colorRes) {
      return this;
    }

    @Override
    public Immersive setStatusBarTranslucent() {
      return this;
    }

    @Override
    public Immersive setStatusBarVisible(boolean visible) {
      return this;
    }

    @Override
    public Immersive setNavigationBarVisible(boolean visible) {
      return this;
    }

    @Override
    public Immersive realFullScreen() {
      return this;
    }

    @Override
    public Immersive setStatusBarFontStyle(boolean isDark) {
      return this;
    }

    @Override
    public Immersive setRootViewFitsSystemWindows(boolean fitSystemWindows) {
      return this;
    }

    @Override
    public Immersive marginViews(List<View> views) {
      return this;
    }

    @Override
    public Immersive paddingViews(List<View> views) {
      return this;
    }
  };
}
