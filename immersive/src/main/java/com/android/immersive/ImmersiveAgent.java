package com.android.immersive;

import android.app.Activity;

import com.android.immersive.interfaces.ImmersiveAssist;

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
