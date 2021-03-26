package com.android.immersive.utils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
public class OSUtils {

  private static boolean initialized = false;
  private static String romName;
  private static String romVersion;
  private static String romTime; // rom发布时间
  private static long romTimeVersion = -1; // 一般rom发布时间会是rom具体版本号

  public static boolean isEmui() {
    return check(OS.Name.ROM_EMUI);
  }

  public static boolean isMiui() {
    return check(OS.Name.ROM_MIUI);
  }

  public static boolean isVivo() {
    return check(OS.Name.ROM_VIVO);
  }

  public static boolean isOppo() {
    return check(OS.Name.ROM_OPPO);
  }

  public static boolean isFlyme() {
    return check(OS.Name.ROM_FLYME);
  }

  public static boolean isSmartisan() {
    return check(OS.Name.ROM_SMARTISAN);
  }

  public static long getRomTime() {
    if (romTimeVersion != - 1) {
      return romTimeVersion;
    }

    if (TextUtils.isEmpty(romTime)) {
      romTimeVersion = 0L;
      return romTimeVersion;
    }

    try {
      romTimeVersion = Long.valueOf(romTime);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return romTimeVersion;
  }

  private static String getName() {
    if (TextUtils.isEmpty(romName)) {
      check("");
    }
    return romName;
  }

  private static String getVersion() {
    if (TextUtils.isEmpty(romVersion)) {
      check("");
    }
    return romVersion;
  }

  private static boolean check(String rom) {
    if (!TextUtils.isEmpty(romName)) {
      return TextUtils.equals(romName, rom);
    }

    if (!initialized) {
      initRomName();
      initialized = true;
    }

    return TextUtils.equals(romName, rom);
  }

  private static void initRomName() {
    Iterator<Map.Entry<String, String>> entryIterator = OS.OS_MAP.entrySet().iterator();
    while (entryIterator.hasNext()) {
      Map.Entry<String, String> entry = entryIterator.next();
      romVersion = getProp(entry.getKey());
      if (!TextUtils.isEmpty(romVersion)) {
        romName = entry.getValue();
        String versionTimeKey = OS.OS_TIME_MAP.get(romName);
        if (!TextUtils.isEmpty(versionTimeKey)) {
          romTime = getProp(versionTimeKey);
        }
        return;
      }
    }

    romVersion = Build.DISPLAY;
    if (romVersion.toUpperCase().contains(OS.Name.ROM_FLYME)) {
      romName = OS.Name.ROM_FLYME;
    } else {
      romVersion = Build.UNKNOWN;
      romName = Build.MANUFACTURER.toUpperCase();
    }
  }

  @SuppressLint("PrivateApi")
  public static String getProp(String key) {
    Class<?> clazz;
    try {
      clazz = Class.forName("android.os.SystemProperties");
      Method method = clazz.getDeclaredMethod("get", String.class);
      return (String) method.invoke(clazz, key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
