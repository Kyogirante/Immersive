package com.android.immersive.interfaces;

/**
 * @author KyoWang
 * @since 2018/11/15
 */
public interface ImmersiveAssist {

  /**
   * 获取属性刷新器.
   *
   * @return
   */
  ImmersiveAttribute.Refresher getAttributeRefresher();

  /**
   * 开始沉浸式处理.
   */
  void doImmersive();
  
}
