package com.acfun.immersive.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;

/**
 * 因为有时候仅仅只关心部分属性，布尔值只能设置两种状态，新增一种无关状态.
 *
 * @author KyoWang
 * @since 2018/11/19
 */
@IntDef({ImmersiveBoolean.NULL, ImmersiveBoolean.TRUE, ImmersiveBoolean.FALSE})
@Retention(RetentionPolicy.SOURCE)
public @interface ImmersiveBoolean {
  /**
   * 不进行任何操作.
   */
  int NULL = 0;
  /**
   * {@link Boolean#TRUE}
   */
  int TRUE = 1;
  /**
   * {@link Boolean#FALSE}
   */
  int FALSE = 2;
}
