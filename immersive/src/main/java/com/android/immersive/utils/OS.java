package com.acfun.immersive.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

import android.support.annotation.StringDef;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
public final class OS {

  public static final Map<String, String> OS_MAP = new HashMap<>();

  public static final Map<String, String> OS_TIME_MAP = new HashMap<>();

  static {
    OS_MAP.put(VersionKey.KEY_VERSION_MIUI, Name.ROM_MIUI);
    OS_MAP.put(VersionKey.KEY_VERSION_EMUI, Name.ROM_EMUI);
    OS_MAP.put(VersionKey.KEY_VERSION_OPPO, Name.ROM_OPPO);
    OS_MAP.put(VersionKey.KEY_VERSION_SMARTISAN, Name.ROM_SMARTISAN);
    OS_MAP.put(VersionKey.KEY_VERSION_VIVO, Name.ROM_VIVO);
  }

  static {
    OS_TIME_MAP.put(Name.ROM_MIUI, VersionTime.KEY_VERSION_TIME_MIUI);
  }

  private OS() {
    // no instance
  }

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({Name.ROM_MIUI, Name.ROM_EMUI, Name.ROM_FLYME, Name.ROM_OPPO, Name.ROM_SMARTISAN,
      Name.ROM_VIVO})
  public @interface Name {
    String ROM_MIUI = "MIUI"; // 小米
    String ROM_EMUI = "EMUI"; // 华为
    String ROM_FLYME = "FLYME"; // 魅族
    String ROM_OPPO = "OPPO"; // oppo
    String ROM_SMARTISAN = "SMARTISAN"; // 锤子
    String ROM_VIVO = "VIVO"; // vivo
  }

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({VersionKey.KEY_VERSION_MIUI, VersionKey.KEY_VERSION_EMUI, VersionKey.KEY_VERSION_OPPO,
      VersionKey.KEY_VERSION_SMARTISAN, VersionKey.KEY_VERSION_VIVO})
  public @interface VersionKey {
    String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    String KEY_VERSION_EMUI = "ro.build.version.emui";
    String KEY_VERSION_OPPO = "ro.build.version.opporom";
    String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    String KEY_VERSION_VIVO = "ro.vivo.os.version";
  }

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({VersionTime.KEY_VERSION_TIME_MIUI})
  public @interface VersionTime {
    String KEY_VERSION_TIME_MIUI = "ro.miui.version.code_time";
  }
}
