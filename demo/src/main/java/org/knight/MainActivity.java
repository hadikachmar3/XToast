/*
 * Copyright (C) 2014 The XToast Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.knight;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
        XToast.create(this, "Background Resource").withBackgroundResource(R.drawable.xtoast_custom_bg).show();
    }

    private void showGravity() {
        XToast.create(this, "Gravity").withGravity(Gravity.TOP, 60, 60).show();
    }

    private void showAnim() {
        XToast.create(this, "Animation").withAnimation(XToast.Anim.POPUP).show();
    }

    private void showCover() {
        XToast.create(this, "Cover Previous").withCover(true).show();
    }

    private void showButton() {
        Drawable d = getResources().getDrawable(R.drawable.icon_undo);
        XToast.create(this, "Button").withButton("撤销", d, new XToast.ButtonClickListener() {
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
