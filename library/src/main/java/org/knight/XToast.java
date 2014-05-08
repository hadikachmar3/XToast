package org.knight;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Custom toast, simplified version of SuperToasts.</br>
 *
 * Now supports the following configurations:</br>
 * 1. Text size/color</br>
 * 2. Background resource/color</br>
 * 3. Animation</br>
 * 4. Gravity and offset</br>
 * 5. No wait for previous toast</br>
 * 6. Integrate a button in the toast(text/icon/event)
 *
 * No support for the custom views.
 *
 * //TODO 在show里面显示，设置只用于收集配置信息
 *
 * @author knight
 */
public class XToast {
    private Context mContext;

    private View mXToastView;
    private LinearLayout mRootLayout;
    private TextView mTextView;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowManagerLayoutParams;

    private boolean mNoWait;

    public static XToast create(Context ctx, CharSequence text) {
        return new XToast(ctx).withText(text);
    }

    private XToast(Context ctx) {
        mContext = ctx;
        initViews();
    }

    private void inflate() {
        mXToastView = LayoutInflater.from(mContext).inflate(R.layout.xtoast, null);
    }

    private void initViews() {
        mRootLayout = (LinearLayout) mXToastView.findViewById(R.id.xtoast_layout);
        mTextView = (TextView) mXToastView.findViewById(R.id.xtoast_text);

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mWindowManagerLayoutParams = new LayoutParams();
        mWindowManagerLayoutParams.width = LayoutParams.WRAP_CONTENT;
        mWindowManagerLayoutParams.height = LayoutParams.WRAP_CONTENT;
        mWindowManagerLayoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE | LayoutParams.FLAG_KEEP_SCREEN_ON;
        mWindowManagerLayoutParams.type = LayoutParams.TYPE_TOAST;
        mWindowManagerLayoutParams.format = PixelFormat.TRANSLUCENT;
        mWindowManagerLayoutParams.windowAnimations = Anim.TOAST;
        mWindowManagerLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    }

    public void show() {
        XToastQueue.getInstance().add(this);
    }

    public void dismiss() {
        XToastQueue.getInstance().remove(this);
    }

    public XToast withText(CharSequence text) {
        mTextView.setText(text);
        return this;
    }

    public XToast withTextColor(int color) {
        mTextView.setTextColor(color);
        return this;
    }

    public XToast withTextSize(int size) {
        mTextView.setTextSize(size);
        return this;
    }

    public XToast withBackgroundResource(int res) {
        mRootLayout.setBackgroundResource(res);
        return this;
    }

    public XToast withBackgroundColor(int color) {
        mRootLayout.setBackgroundColor(color);
        return this;
    }

    public XToast withAnimation(int animation) {
        mWindowManagerLayoutParams.windowAnimations = animation;
        return this;
    }

    public XToast withGravity(int gravity, int xOffset, int yOffset) {
        mWindowManagerLayoutParams.gravity = gravity;
        mWindowManagerLayoutParams.x = xOffset;
        mWindowManagerLayoutParams.y = yOffset;
        return this;
    }

    public XToast withNoWait(boolean noWait) {
        mNoWait = noWait;
        return this;
    }

    public static class Duration {
        public static final int SHORT = 2000;
        public static final int MEDIUM = 4000;
        public static final int LONG = 6000;
    }

    public static class Anim {
        public static final int TOAST = android.R.style.Animation_Toast;
        public static final int SCALE = android.R.style.Animation_Dialog;
        public static final int POPUP = android.R.style.Animation_InputMethod;
        public static final int FLY = android.R.style.Animation_Translucent;
    }

    private static class XToastQueue {
        private static XToastQueue instance;
        private LinkedList<XToast> queue;

        private static XToastQueue getInstance() {
            if (instance == null) {
                synchronized (XToastQueue.class) {
                    if (instance == null) {
                        instance = new XToastQueue();
                    }
                }
            }
            return instance;
        }

        private void add(XToast xtoast) {

        }

        private void remove(XToast xtoast) {

        }

        private void show(XToast xtoast) {

        }

        private void showNext() {

        }
    }
}
