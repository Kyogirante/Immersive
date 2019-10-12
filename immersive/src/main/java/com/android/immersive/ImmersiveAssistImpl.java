package com.android.immersive;

import android.app.Activity;

import com.android.immersive.impl.ImmersiveFactory;
import com.android.immersive.interfaces.Immersive;
import com.android.immersive.interfaces.ImmersiveAssist;
import com.android.immersive.interfaces.ImmersiveAttribute;
import com.android.immersive.interfaces.ImmersiveBoolean;
import com.android.immersive.interfaces.ImmersiveFontStyle;
import com.android.immersive.interfaces.ImmersiveStatus;
import com.android.immersive.model.DefaultAttribute;

/**
 * 沉浸式处理统一都使用代码处理，不使用主题等xml处理.
 *
 * @author KyoWang
 * @since 2018/11/14
 */
final class ImmersiveAssistImpl implements ImmersiveAssist {

  private Immersive immersive;
  private ImmersiveAttribute attribute;

  public static ImmersiveAssistImpl bind(Activity activity) {
    return new ImmersiveAssistImpl(activity);
  }

  private ImmersiveAssistImpl(Activity activity) {
    attribute = new DefaultAttribute(this);
    immersive = ImmersiveFactory.createImmersiveAssist(activity);
  }

  @Override
  public ImmersiveAttribute.Refresher getAttributeRefresher() {
    return attribute.getRefresher();
  }

  @Override
  public void doImmersive() {
    if (attribute.getImmersiveStatus() == ImmersiveStatus.NO_IMMERSIVE) {
      return;
    }

    if (attribute.getFitsSystemWindows() == ImmersiveBoolean.TRUE) {
      immersive.setRootViewFitsSystemWindows(true);
    } else if (attribute.getFitsSystemWindows() == ImmersiveBoolean.FALSE) {
      immersive.setRootViewFitsSystemWindows(false);
    }

    if (attribute.getImmersiveStatus() == ImmersiveStatus.COLOR_IMMERSIVE) {
      int color = attribute.getStatusBarColor();
      if (color != 0) {
        immersive.setStatusBarColor(color);
      }
    } else if (attribute.getImmersiveStatus() == ImmersiveStatus.TRANSLUCENT_IMMERSIVE) {
      immersive.setStatusBarTranslucent();
    }

    if (attribute.getStatusBarVisible() == ImmersiveBoolean.TRUE) {
      immersive.setStatusBarVisible(true);
    } else if (attribute.getStatusBarVisible() == ImmersiveBoolean.FALSE) {
      immersive.setStatusBarVisible(false);
    }

    if (attribute.getNavigationBarVisible() == ImmersiveBoolean.TRUE) {
      immersive.setNavigationBarVisible(true);
    } else if (attribute.getNavigationBarVisible() == ImmersiveBoolean.FALSE) {
      immersive.setNavigationBarVisible(false);
    }

    if (attribute.getStatusBarVisible() == ImmersiveBoolean.FALSE
        && attribute.getNavigationBarVisible() == ImmersiveBoolean.FALSE) {
      // 如果是全面沉浸，View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 会导致NAVIGATION BAR获取焦点，导致第一次点击失效
      // 需要添加 View.SYSTEM_UI_FLAG_IMMERSIVE 协助达到真正的沉浸式
      immersive.realFullScreen();
    }

    if (attribute.getStatusBarFontStyle() == ImmersiveFontStyle.WHITE) {
      immersive.setStatusBarFontStyle(false);
    } else if (attribute.getStatusBarFontStyle() == ImmersiveFontStyle.BLACK) {
      immersive.setStatusBarFontStyle(true);
    }

    immersive.marginViews(attribute.getMarginViews());

    immersive.paddingViews(attribute.getPaddingVies());

    attribute.recycle();
  }
}
