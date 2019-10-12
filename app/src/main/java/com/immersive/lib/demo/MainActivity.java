package com.immersive.lib.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.immersive.ImmersiveAgent;
import com.android.immersive.interfaces.ImmersiveAssist;
import com.android.immersive.interfaces.ImmersiveBoolean;
import com.android.immersive.interfaces.ImmersiveFontStyle;
import com.android.immersive.interfaces.ImmersiveStatus;


public class MainActivity extends AppCompatActivity {

  private int statusBarStatus = ImmersiveBoolean.TRUE;

  private int fontColor = ImmersiveFontStyle.BLACK;

  private boolean immersive = false;


  private ImmersiveAssist immersiveAssist;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    immersiveAssist = ImmersiveAgent.bind(this);
    immersiveAssist.getAttributeRefresher()
        .setStatusBarVisible(statusBarStatus)
        .setStatusBarFontStyle(fontColor)
        .commit();
    initView();
  }

  private void initView() {
    findViewById(R.id.status_bar_hide).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int fit = ImmersiveBoolean.TRUE;
        if (statusBarStatus == ImmersiveBoolean.FALSE) {
          statusBarStatus = ImmersiveBoolean.TRUE;
          fit = ImmersiveBoolean.FALSE;
        } else {
          statusBarStatus = ImmersiveBoolean.FALSE;
          fit = ImmersiveBoolean.TRUE;
        }
        immersiveAssist.getAttributeRefresher()
            .setImmersiveStatus(ImmersiveStatus.OTHER_IMMERSIVE)
            .setStatusBarVisible(statusBarStatus)
            .setFitsSystemWindows(fit)
            .commit();
      }
    });

    findViewById(R.id.status_bar_font_color).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (fontColor == ImmersiveFontStyle.BLACK) {
          fontColor = ImmersiveFontStyle.WHITE;
        } else {
          fontColor = ImmersiveFontStyle.BLACK;
        }

        immersiveAssist.getAttributeRefresher()
            .setImmersiveStatus(ImmersiveStatus.OTHER_IMMERSIVE)
            .setStatusBarFontStyle(fontColor)
            .commit();
      }
    });

    findViewById(R.id.full_immersive).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (immersive) {
          immersive = false;
          immersiveAssist.getAttributeRefresher()
              .setImmersiveStatus(ImmersiveStatus.OTHER_IMMERSIVE)
              .setStatusBarVisible(ImmersiveBoolean.TRUE)
              .setFitsSystemWindows(ImmersiveBoolean.TRUE)
              .commit();
        } else {
          immersive = true;
          immersiveAssist.getAttributeRefresher()
              .setImmersiveStatus(ImmersiveStatus.OTHER_IMMERSIVE)
              .setStatusBarVisible(ImmersiveBoolean.FALSE)
              .setFitsSystemWindows(ImmersiveBoolean.FALSE)
              .commit();
        }
      }
    });

    findViewById(R.id.status_bar_color).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        immersiveAssist.getAttributeRefresher()
            .setImmersiveStatus(ImmersiveStatus.COLOR_IMMERSIVE)
            .setStatusBarColor(getColorRes())
            .commit();
      }
    });
  }

  private int getColorRes() {
    long now = System.currentTimeMillis();
    long num = now % 3;
    if (num == 0) {
      return R.color.colorPrimary;
    }

    if (num == 1) {
      return R.color.colorPrimaryDark;
    }

    return R.color.colorAccent;
  }


}
