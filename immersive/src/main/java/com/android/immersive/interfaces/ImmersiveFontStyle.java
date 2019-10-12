package com.acfun.immersive.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;

/**
 * @author KyoWang
 * @since 2018/11/21
 */
@IntDef({ImmersiveFontStyle.NULL, ImmersiveFontStyle.WHITE, ImmersiveFontStyle.BLACK})
@Retention(RetentionPolicy.SOURCE)
public @interface ImmersiveFontStyle {
  /**
   * 保持原状.
   */
  int NULL = 0;
  /**
   * 白色.
   */
  int WHITE = 1;
  /**
   * 黑色.
   */
  int BLACK = 2;
}
