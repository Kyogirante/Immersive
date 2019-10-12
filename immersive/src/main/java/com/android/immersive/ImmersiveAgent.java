package com.acfun.immersive;

import android.app.Activity;

import com.acfun.immersive.interfaces.ImmersiveAssist;

/**
 * @author KyoWang
 * @since 2018/11/15
 */
public final class ImmersiveAgent {

  private ImmersiveAgent() {
    // no instance
  }

  public static ImmersiveAssist bind(Activity activity) {
    return ImmersiveAssistImpl.bind(activity);
  }

}
