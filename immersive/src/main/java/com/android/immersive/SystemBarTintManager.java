package com.android.immersive;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;

/**
 * Copy from {@see https://github.com/jgilfelt/SystemBarTint}
 *
 * Class to manage status and navigation bar tint effects when using KitKat
 * translucent system UI modes.
 */
public class SystemBarTintManager {

  /**
   * The default system bar tint color value.
   */
  public static final int DEFAULT_TINT_COLOR = 0x99000000;

  private final SystemBarConfig config;
  private boolean statusBarAvailable;
  private boolean navBarAvailable;
  private boolean statusBarTintEnabled;
  private boolean navBarTintEnabled;
  private View statusBarTintView;
  private View navBarTintView;
  private static boolean isMiuiV6;

  static {
    try {
      Class<?> sysClass = Class.forName("android.os.SystemProperties");
      Method getStringMethod = sysClass.getDeclaredMethod("get", String.class);
      isMiuiV6 = "V6".equals((String) getStringMethod.invoke(sysClass, "ro.miui.ui.version.name"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Constructor. Call this in the host activity onCreate method after its
   * content view has been set. You should always create new instances when
   * the host activity is recreated.
   *
   * @param activity The host activity.
   */
  @TargetApi(19)
  public SystemBarTintManager(Activity activity) {

    Window win = activity.getWindow();
    ViewGroup decorViewGroup = (ViewGroup) win.getDecorView();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // check theme attrs
      int[] attrs = {android.R.attr.windowTranslucentStatus,
          android.R.attr.windowTranslucentNavigation};
      TypedArray a = activity.obtainStyledAttributes(attrs);
      try {
        statusBarAvailable = a.getBoolean(0, false);
        //noinspection ResourceType
        navBarAvailable = a.getBoolean(1, false);
      } finally {
        a.recycle();
      }

      // check window flags
      WindowManager.LayoutParams winParams = win.getAttributes();
      int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
      if ((winParams.flags & bits) != 0) {
        statusBarAvailable = true;
      }
      bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
      if ((winParams.flags & bits) != 0) {
        navBarAvailable = true;
      }
    }

    config = new SystemBarConfig(activity, statusBarAvailable, navBarAvailable);
    // device might not have virtual navigation keys
    if (!config.hasNavigtionBar()) {
      navBarAvailable = false;
    }

    if (statusBarAvailable) {
      setupStatusBarView(activity, decorViewGroup);
    }
    if (navBarAvailable) {
      setupNavBarView(activity, decorViewGroup);
    }

  }

  /**
   * Enable tinting of the system status bar.
   * <p>
   * If the platform is running Jelly Bean or earlier, or translucent system
   * UI modes have not been enabled in either the theme or via window flags,
   * then this method does nothing.
   *
   * @param enabled True to enable tinting, false to disable it (default).
   */
  public void setStatusBarTintEnabled(boolean enabled) {
    statusBarTintEnabled = enabled;
    if (statusBarAvailable) {
      statusBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * set status bar darkmode
   *
   * @param darkmode
   * @param activity
   */
  public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
    if (isMiuiV6) {
      Class<? extends Window> clazz = activity.getWindow().getClass();
      try {
        int darkModeFlag = 0;
        Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Enable tinting of the system navigation bar.
   * <p>
   * If the platform does not have soft navigation keys, is running Jelly Bean
   * or earlier, or translucent system UI modes have not been enabled in either
   * the theme or via window flags, then this method does nothing.
   *
   * @param enabled True to enable tinting, false to disable it (default).
   */
  public void setNavigationBarTintEnabled(boolean enabled) {
    navBarTintEnabled = enabled;
    if (navBarAvailable) {
      navBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * Apply the specified color tint to all system UI bars.
   *
   * @param color The color of the background tint.
   */
  public void setTintColor(int color) {
    setStatusBarTintColor(color);
    setNavigationBarTintColor(color);
  }

  /**
   * Apply the specified drawable or color resource to all system UI bars.
   *
   * @param res The identifier of the resource.
   */
  public void setTintResource(int res) {
    setStatusBarTintResource(res);
    setNavigationBarTintResource(res);
  }

  /**
   * Apply the specified drawable to all system UI bars.
   *
   * @param drawable The drawable to use as the background, or null to remove it.
   */
  public void setTintDrawable(Drawable drawable) {
    setStatusBarTintDrawable(drawable);
    setNavigationBarTintDrawable(drawable);
  }

  /**
   * Apply the specified alpha to all system UI bars.
   *
   * @param alpha The alpha to use
   */
  public void setTintAlpha(float alpha) {
    setStatusBarAlpha(alpha);
    setNavigationBarAlpha(alpha);
  }

  /**
   * Apply the specified color tint to the system status bar.
   *
   * @param color The color of the background tint.
   */
  public void setStatusBarTintColor(int color) {
    if (statusBarAvailable) {
      statusBarTintView.setBackgroundColor(color);
    }
  }

  /**
   * Apply the specified drawable or color resource to the system status bar.
   *
   * @param res The identifier of the resource.
   */
  public void setStatusBarTintResource(int res) {
    if (statusBarAvailable) {
      statusBarTintView.setBackgroundResource(res);
    }
  }

  /**
   * Apply the specified drawable to the system status bar.
   *
   * @param drawable The drawable to use as the background, or null to remove it.
   */
  @SuppressWarnings("deprecation")
  public void setStatusBarTintDrawable(Drawable drawable) {
    if (statusBarAvailable) {
      statusBarTintView.setBackgroundDrawable(drawable);
    }
  }

  /**
   * Apply the specified alpha to the system status bar.
   *
   * @param alpha The alpha to use
   */
  @TargetApi(11)
  public void setStatusBarAlpha(float alpha) {
    if (statusBarAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      statusBarTintView.setAlpha(alpha);
    }
  }

  /**
   * Apply the specified color tint to the system navigation bar.
   *
   * @param color The color of the background tint.
   */
  public void setNavigationBarTintColor(int color) {
    if (navBarAvailable) {
      navBarTintView.setBackgroundColor(color);
    }
  }

  /**
   * Apply the specified drawable or color resource to the system navigation bar.
   *
   * @param res The identifier of the resource.
   */
  public void setNavigationBarTintResource(int res) {
    if (navBarAvailable) {
      navBarTintView.setBackgroundResource(res);
    }
  }

  /**
   * Apply the specified drawable to the system navigation bar.
   *
   * @param drawable The drawable to use as the background, or null to remove it.
   */
  @SuppressWarnings("deprecation")
  public void setNavigationBarTintDrawable(Drawable drawable) {
    if (navBarAvailable) {
      navBarTintView.setBackgroundDrawable(drawable);
    }
  }

  /**
   * Apply the specified alpha to the system navigation bar.
   *
   * @param alpha The alpha to use
   */
  @TargetApi(11)
  public void setNavigationBarAlpha(float alpha) {
    if (navBarAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      navBarTintView.setAlpha(alpha);
    }
  }

  /**
   * Get the system bar configuration.
   *
   * @return The system bar configuration for the current device configuration.
   */
  public SystemBarConfig getConfig() {
    return config;
  }

  /**
   * Is tinting enabled for the system status bar?
   *
   * @return True if enabled, False otherwise.
   */
  public boolean isStatusBarTintEnabled() {
    return statusBarTintEnabled;
  }

  /**
   * Is tinting enabled for the system navigation bar?
   *
   * @return True if enabled, False otherwise.
   */
  public boolean isNavBarTintEnabled() {
    return navBarTintEnabled;
  }

  public View getStatusBarTintView() {
    return statusBarTintView;
  }

  private void setupStatusBarView(Context context, ViewGroup decorViewGroup) {
    statusBarTintView = new View(context);
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, config.getStatusBarHeight());
    params.gravity = Gravity.TOP;
    if (navBarAvailable && !config.isNavigationAtBottom()) {
      params.rightMargin = config.getNavigationBarWidth();
    }
    statusBarTintView.setLayoutParams(params);
    statusBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
    statusBarTintView.setVisibility(View.GONE);
    decorViewGroup.addView(statusBarTintView);
  }

  private void setupNavBarView(Context context, ViewGroup decorViewGroup) {
    navBarTintView = new View(context);
    LayoutParams params;
    if (config.isNavigationAtBottom()) {
      params = new LayoutParams(LayoutParams.MATCH_PARENT, config.getNavigationBarHeight());
      params.gravity = Gravity.BOTTOM;
    } else {
      params = new LayoutParams(config.getNavigationBarWidth(), LayoutParams.MATCH_PARENT);
      params.gravity = Gravity.RIGHT;
    }
    navBarTintView.setLayoutParams(params);
    navBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
    navBarTintView.setVisibility(View.GONE);
    decorViewGroup.addView(navBarTintView);
  }

  /**
   * Class which describes system bar sizing and other characteristics for the current
   * device configuration.
   */
  public static class SystemBarConfig {

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";

    private final boolean mTranslucentStatusBar;
    private final boolean mTranslucentNavBar;
    private final int mStatusBarHeight;
    private final int mActionBarHeight;
    private final boolean mHasNavigationBar;
    private final int mNavigationBarHeight;
    private final int mNavigationBarWidth;
    private final boolean mInPortrait;
    private final float mSmallestWidthDp;

    private SystemBarConfig(Activity activity, boolean translucentStatusBar, boolean traslucentNavBar) {
      Resources res = activity.getResources();
      mInPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
      mSmallestWidthDp = getSmallestWidthDp(activity);
      mStatusBarHeight = getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);
      mActionBarHeight = getActionBarHeight(activity);
      mNavigationBarHeight = getNavigationBarHeight(activity);
      mNavigationBarWidth = getNavigationBarWidth(activity);
      mHasNavigationBar = (mNavigationBarHeight > 0);
      mTranslucentStatusBar = translucentStatusBar;
      mTranslucentNavBar = traslucentNavBar;
    }

    @TargetApi(14)
    private int getActionBarHeight(Context context) {
      int result = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        result = context.getResources().getDimensionPixelSize(tv.resourceId);
      }
      return result;
    }

    @TargetApi(14)
    private int getNavigationBarHeight(Context context) {
      Resources res = context.getResources();
      int result = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
          String key;
          if (mInPortrait) {
            key = NAV_BAR_HEIGHT_RES_NAME;
          } else {
            key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
          }
          return getInternalDimensionSize(res, key);
        }
      }
      return result;
    }

    @TargetApi(14)
    private int getNavigationBarWidth(Context context) {
      Resources res = context.getResources();
      int result = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
          return getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME);
        }
      }
      return result;
    }

    private int getInternalDimensionSize(Resources res, String key) {
      int result = 0;
      int resourceId = res.getIdentifier(key, "dimen", "android");
      if (resourceId > 0) {
        result = res.getDimensionPixelSize(resourceId);
      }
      return result;
    }

    @SuppressLint("NewApi")
    private float getSmallestWidthDp(Activity activity) {
      DisplayMetrics metrics = new DisplayMetrics();
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
      } else {
        // TODO this is not correct, but we don't really care pre-kitkat
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
      }
      float widthDp = metrics.widthPixels / metrics.density;
      float heightDp = metrics.heightPixels / metrics.density;
      return Math.min(widthDp, heightDp);
    }

    /**
     * Should a navigation bar appear at the bottom of the screen in the current
     * device configuration? A navigation bar may appear on the right side of
     * the screen in certain configurations.
     *
     * @return True if navigation should appear at the bottom of the screen, False otherwise.
     */
    public boolean isNavigationAtBottom() {
      return (mSmallestWidthDp >= 600 || mInPortrait);
    }

    /**
     * Get the height of the system status bar.
     *
     * @return The height of the status bar (in pixels).
     */
    public int getStatusBarHeight() {
      return mStatusBarHeight;
    }

    /**
     * Get the height of the action bar.
     *
     * @return The height of the action bar (in pixels).
     */
    public int getActionBarHeight() {
      return mActionBarHeight;
    }

    /**
     * Does this device have a system navigation bar?
     *
     * @return True if this device uses soft key navigation, False otherwise.
     */
    public boolean hasNavigtionBar() {
      return mHasNavigationBar;
    }

    /**
     * Get the height of the system navigation bar.
     *
     * @return The height of the navigation bar (in pixels). If the device does not have
     * soft navigation keys, this will always return 0.
     */
    public int getNavigationBarHeight() {
      return mNavigationBarHeight;
    }

    /**
     * Get the width of the system navigation bar when it is placed vertically on the screen.
     *
     * @return The width of the navigation bar (in pixels). If the device does not have
     * soft navigation keys, this will always return 0.
     */
    public int getNavigationBarWidth() {
      return mNavigationBarWidth;
    }

    /**
     * Get the layout inset for any system UI that appears at the top of the screen.
     *
     * @param withActionBar True to include the height of the action bar, False otherwise.
     * @return The layout inset (in pixels).
     */
    public int getPixelInsetTop(boolean withActionBar) {
      return (mTranslucentStatusBar ? mStatusBarHeight : 0) + (withActionBar ? mActionBarHeight : 0);
    }

    /**
     * Get the layout inset for any system UI that appears at the bottom of the screen.
     *
     * @return The layout inset (in pixels).
     */
    public int getPixelInsetBottom() {
      if (mTranslucentNavBar && isNavigationAtBottom()) {
        return mNavigationBarHeight;
      } else {
        return 0;
      }
    }

    /**
     * Get the layout inset for any system UI that appears at the right of the screen.
     *
     * @return The layout inset (in pixels).
     */
    public int getPixelInsetRight() {
      if (mTranslucentNavBar && !isNavigationAtBottom()) {
        return mNavigationBarWidth;
      } else {
        return 0;
      }
    }

  }

}
