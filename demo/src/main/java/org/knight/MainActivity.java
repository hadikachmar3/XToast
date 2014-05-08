package org.knight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    private void showNormal() {

    }

    private void showDuration() {

    }

    private void showBackground() {

    }

    private void showAnim() {

    }

    private void showText() {

    }

    private void showNoWait() {

    }

    public void show(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                showNormal();
                break;

            case R.id.btn_duration:
                showDuration();
                break;

            case R.id.btn_background:
                showBackground();
                break;

            case R.id.btn_anim:
                showAnim();
                break;

            case R.id.btn_text:
                showText();
                break;

            case R.id.btn_nowait:
                showNoWait();
                break;

            default:
                break;
        }
    }
}
