package org.knight;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import org.knight.widget.XToast;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    private void showNormal() {
        XToast.create(this, "Normal").show();
    }

    private void showCustomText() {
        XToast.create(this, "CustomText").withTextColor(Color.parseColor("#00FF00")).withTextSize(20).show();
    }

    private void showDuration() {
        XToast.create(this, "Duration").withDuration(XToast.Duration.LONG).show();
    }

    private void showBackgroundColor() {
        XToast.create(this, "Background Color").withBackgroundColor(Color.parseColor("#FF0000")).show();
    }

    private void showBackgroundResource() {
        XToast.create(this, "Background Resource").withBackgroundResource(R.drawable.xtoast_background).show();
    }

    private void showGravity() {
        XToast.create(this, "Gravity").withGravity(Gravity.TOP, 60, 60).show();
    }

    private void showAnim() {
        XToast.create(this, "Animation").withAnimation(XToast.Anim.POPUP).show();
    }

    private void showCover() {
        XToast.create(this, "Cover Previou").withCover(true).show();
    }

    private void showButton() {
        XToast.create(this, "Button").withButton("Click", new XToast.ButtonClickListener() {
            @Override
            public void onClick(XToast xtoast) {
                xtoast.dismiss();
                XToast.create(MainActivity.this, "Click Event").show();
            }
        }).show();
    }

    public void show(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                showNormal();
                break;

            case R.id.btn_text:
                showCustomText();
                break;

            case R.id.btn_duration:
                showDuration();
                break;

            case R.id.btn_gravity:
                showGravity();
                break;

            case R.id.btn_background_color:
                showBackgroundColor();
                break;

            case R.id.btn_background_res:
                showBackgroundResource();
                break;

            case R.id.btn_anim:
                showAnim();
                break;

            case R.id.btn_cover:
                showCover();
                break;

            case R.id.btn_button:
                showButton();
                break;

            default:
                break;
        }
    }
}
