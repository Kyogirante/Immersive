package com.android.immersive.model;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.ColorRes;
import android.view.View;

import com.android.immersive.interfaces.Immersive;
import com.android.immersive.interfaces.ImmersiveAssist;
import com.android.immersive.interfaces.ImmersiveAttribute;
import com.android.immersive.interfaces.ImmersiveBoolean;
import com.android.immersive.interfaces.ImmersiveFontStyle;
import com.android.immersive.interfaces.ImmersiveStatus;

/**
 * @author KyoWang
 * @since 2018/11/15
 */
public class DefaultAttribute implements ImmersiveAttribute {

  @ImmersiveStatus
  int immersiveStatus = ImmersiveStatus.OTHER_IMMERSIVE;

  @ColorRes
  int statusBarColor = 0;

  @ImmersiveBoolean
  int statusBarVisible = ImmersiveBoolean.NULL;

  @ColorRes
  int navigationBarColor = 0;

  @ImmersiveBoolean
  int navigationBarVisible = ImmersiveBoolean.NULL;

  @ImmersiveFontStyle
  int navigationBarFontStyle = ImmersiveFontStyle.NULL;

  @ImmersiveFontStyle
  int statusBarFontStyle = ImmersiveFontStyle.NULL;

  @ImmersiveBoolean
  int fitsSystemWindows = ImmersiveBoolean.NULL;

  List<View> marginViews = new ArrayList<>();

  List<View> paddingViews = new ArrayList<>();

  Refresher refresher;

  public DefaultAttribute(ImmersiveAssist handler) {
    refresher = new AttributeRefresher(this, handler);
  }

  @Override
  public int getImmersiveStatus() {
    return immersiveStatus;
  }

  @Override
  public int getStatusBarColor() {
    return statusBarColor;
  }

  @Override
  public int getNavigationBarColor() {
    return navigationBarColor;
  }

  @Override
  @ImmersiveBoolean
  public int getFitsSystemWindows() {
    return fitsSystemWindows;
  }

  @Override
  @ImmersiveBoolean
  public int getStatusBarVisible() {
    return statusBarVisible;
  }

  @Override
  @ImmersiveBoolean
  public int getNavigationBarVisible() {
    return navigationBarVisible;
  }

  @Override
  public int getNavigationBarFontStyle() {
    return navigationBarFontStyle;
  }

  @ImmersiveFontStyle
  public int getStatusBarFontStyle() {
    return statusBarFontStyle;
  }

  @Override
  public List<View> getMarginViews() {
    return marginViews;
  }

  @Override
  public List<View> getPaddingVies() {
    return paddingViews;
  }

  @Override
  public Refresher getRefresher() {
    return refresher;
  }

  @Override
  public void recycle() {
    immersiveStatus = ImmersiveStatus.NO_IMMERSIVE;
    statusBarColor = 0;
    statusBarVisible = ImmersiveBoolean.NULL;
    navigationBarColor = 0;
    fitsSystemWindows = ImmersiveBoolean.NULL;
    navigationBarVisible = ImmersiveBoolean.NULL;
    navigationBarFontStyle = ImmersiveFontStyle.NULL;
    statusBarFontStyle = ImmersiveFontStyle.NULL;
    marginViews.clear();
    paddingViews.clear();
  }

  public static class AttributeRefresher implements Refresher {

    private ImmersiveAssist handler;
    private DefaultAttribute attribute;

    public AttributeRefresher(DefaultAttribute attribute, ImmersiveAssist handler) {
      this.handler = handler;
      this.attribute = attribute;
    }

    @Override
    public Refresher setImmersiveStatus(int status) {
      attribute.immersiveStatus = status;
      return this;
    }

    @Override
    public Refresher setStatusBarColor(int color) {
      attribute.statusBarColor = color;
      return this;
    }

    @Override
    public Refresher setStatusBarVisible(@ImmersiveBoolean int visible) {
      attribute.statusBarVisible = visible;
      return this;
    }

    @Override
    public Refresher setNavigationBarColor(int colorRes) {
      attribute.navigationBarColor = colorRes;
      return this;
    }

    @Override
    public Refresher setNavigationBarVisible(@ImmersiveBoolean int visible) {
      attribute.navigationBarVisible = visible;
      return this;
    }

    @Override
    public Refresher setNavigationBarIconStyle(@ImmersiveFontStyle int style) {
      attribute.navigationBarFontStyle = style;
      return this;
    }

    @Override
    public Refresher setStatusBarFontStyle(@ImmersiveFontStyle int style) {
      attribute.statusBarFontStyle = style;
      return this;
    }

    @Override
    public Refresher setFitsSystemWindows(@ImmersiveBoolean int fitsSystemWindows) {
      attribute.fitsSystemWindows = fitsSystemWindows;
      return this;
    }

    @Override
    public Refresher setMarginViews(View... views) {
      attribute.marginViews.clear();

      if (views == null || views.length == 0) {
        return this;
      }

      for (View view : views) {
        attribute.marginViews.add(view);
      }
      return this;
    }

    @Override
    public Refresher setPaddingViews(View... views) {
      attribute.paddingViews.clear();

      if (views == null || views.length == 0) {
        return this;
      }

      for (View view : views) {
        attribute.paddingViews.add(view);
      }

      return this;
    }

    @Override
    public void commit() {
      handler.doImmersive();
    }
  }

}
