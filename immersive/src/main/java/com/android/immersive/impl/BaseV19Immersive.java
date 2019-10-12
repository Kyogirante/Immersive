package com.acfun.immersive.impl;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.acfun.immersive.SystemBarTintManager;
import com.acfun.immersive.interfaces.Immersive;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
abstract class BaseV19Immersive extends AbstractImmersive {

  private SystemBarTintManager systemBarTintManager;

  public BaseV19Immersive(Activity activity) {
    super(activity);
  }

  @Override
  public Immersive setStatusBarColor(int colorRes) {
    setStatusBarTranslucent(); // 设置成透明

    if (systemBarTintManager == null) { // 里面有一段逻辑判断，将初始化延后，等后续优化自己实现
      systemBarTintManager = new SystemBarTintManager(activity);
    }

    systemBarTintManager.setStatusBarTintEnabled(true);
    systemBarTintManager.setStatusBarTintResource(colorRes);
    return this;
  }

  @Override
  public Immersive setStatusBarTranslucent() {
    Window window = activity.getWindow();
    WindowManager.LayoutParams attributes = window.getAttributes();
    int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    attributes.flags |= flagTranslucentStatus;
    window.setAttributes(attributes);
    return this;
  }

}
