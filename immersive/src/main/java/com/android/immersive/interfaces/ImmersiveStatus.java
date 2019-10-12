package com.android.immersive.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author KyoWang
 * @since 2018/11/16
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({ImmersiveStatus.NO_IMMERSIVE,
    ImmersiveStatus.COLOR_IMMERSIVE,
    ImmersiveStatus.TRANSLUCENT_IMMERSIVE,
    ImmersiveStatus.OTHER_IMMERSIVE})
public @interface ImmersiveStatus {
  /**
   * 不使用此框架.
   */
  int NO_IMMERSIVE = 0;
  /**
   * 状态栏有颜色.
   */
  int COLOR_IMMERSIVE = 1;
  /**
   * 状态栏透明.
   */
  int TRANSLUCENT_IMMERSIVE = 2;
  /**
   * 其他属性设置.
   */
  int OTHER_IMMERSIVE = 3;
}
