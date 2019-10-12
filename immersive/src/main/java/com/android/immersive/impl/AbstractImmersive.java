package com.android.immersive.impl;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.android.immersive.interfaces.Immersive;
import com.android.immersive.utils.SystemDimenUtil;


/**
 * 从19版本适配，低版本忽略.
 *
 * @author KyoWang
 * @since 2018/11/14
 */
public abstract class AbstractImmersive implements Immersive {

  protected Activity activity;

  public AbstractImmersive(Activity activity) {
    this.activity = activity;
  }

  @Override
  public Immersive setStatusBarVisible(boolean visible) {
    if (visible) {
      showStatusBar();
    } else {
      hideStatusBar();
    }
    return this;
  }

  @Override
  public Immersive setNavigationBarVisible(boolean visible) {
    if (visible) {
      showNavigationBar();
    } else {
      hideNavigationBar();
    }
    return this;
  }

  @Override
  public Immersive setRootViewFitsSystemWindows(boolean fitSystemWindows) {
    ViewGroup content = activity.findViewById(android.R.id.content);
    if (content.getChildCount() > 0) {
      ViewGroup rootView = (ViewGroup) content.getChildAt(0);
      if (rootView != null) {
        rootView.setFitsSystemWindows(fitSystemWindows);
      }
    }
    return this;
  }

  @Override
  public Immersive marginViews(List<View> views) {
    if (views == null || views.isEmpty()) {
      return this;
    }

    int topMargin = SystemDimenUtil.getStatusBarHeight(activity);
    for (View view : views) {
      setViewTopMargin(view, topMargin);
    }
    return this;
  }

  @Override
  public Immersive paddingViews(List<View> views) {
    if (views == null || views.isEmpty()) {
      return this;
    }

    int topMargin = SystemDimenUtil.getStatusBarHeight(activity);

    for (View view : views) {
      view.setPadding(view.getPaddingLeft(), topMargin, view.getPaddingRight(),
          view.getPaddingBottom());
    }
    return this;
  }

  @Override
  public Immersive realFullScreen() {
    int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
    activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE);
    return this;
  }

  private void setViewTopMargin(View view, int topMargin) {
    if (view == null || topMargin == 0) {
      return;
    }

    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;
      lp.setMargins(lp.leftMargin, topMargin, lp.rightMargin, lp.bottomMargin);
    } else {
      view.setPadding(view.getPaddingLeft(), topMargin, view.getPaddingRight(),
          view.getPaddingBottom());
    }
  }

  private void hideStatusBar() {
    int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
    activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions
        | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  private void showStatusBar() {
    int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
    uiOptions &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
    activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
  }

  public void hideNavigationBar() {
    int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
    activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }

  public void showNavigationBar() {
    int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
    uiOptions &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
  }

}
