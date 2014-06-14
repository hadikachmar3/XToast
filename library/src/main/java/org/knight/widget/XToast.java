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

package org.knight.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Custom toast, simplified version of SuperToasts.
 * <p/>
 * Now supports the following configurations:</br>
 * 1. Text size/color.</br>
 * 2. Background resource/color.</br>
 * 3. Animation.</br>
 * 4. Gravity and offset.</br>
 * 5. Cover previous toast.</br>
 * 6. Integrate a button in the toast(text/icon/event).</br>
 * 7. Relative position to a specified view.
 *
 * @author knight
 */
public class XToast {
    private Context mContext;

    private View mXToastView;
    private LinearLayout mRootLayout;
    private TextView mTextView;
    private Button mButton;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowManagerLayoutParams;

    private int mDuration;
    private boolean mCover;

    public static XToast create(Context ctx, CharSequence text) {
        if (ctx == null || text == null || text.length() == 0) {
            return null;
        }
        return new XToast(ctx).withText(text);
    }

    public static void cancelAll() {
        XToastQueue.getInstance().removeAll();
    }

    public static void cancelCurrent() {
        XToastQueue.getInstance().removeCurrent();
    }

    private XToast(Context ctx) {
        mContext = ctx;
        initViews();
    }

    private void initViews() {
        mXToastView = LayoutInflater.from(mContext).inflate(R.layout.xtoast, null);
        if (mXToastView == null) {
            return;
        }
        mRootLayout = (LinearLayout) mXToastView.findViewById(R.id.xtoast_root_layout);
        mTextView = (TextView) mXToastView.findViewById(R.id.xtoast_text);

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManagerLayoutParams = new LayoutParams();
    }

    private void configure() {
        if (mDuration == 0) {
            mDuration = Duration.SHORT;
        }
        mWindowManagerLayoutParams.width = LayoutParams.WRAP_CONTENT;
        mWindowManagerLayoutParams.height = LayoutParams.WRAP_CONTENT;
        mWindowManagerLayoutParams.type = LayoutParams.TYPE_TOAST;
        mWindowManagerLayoutParams.format = PixelFormat.TRANSLUCENT;
        if (mWindowManagerLayoutParams.windowAnimations == 0) {
            mWindowManagerLayoutParams.windowAnimations = Anim.TOAST;
        }
        if (mWindowManagerLayoutParams.gravity == 0 && mWindowManagerLayoutParams.x == 0 && mWindowManagerLayoutParams.y == 0) {
            mWindowManagerLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        }
        mWindowManagerLayoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        if (mButton == null) {
            mWindowManagerLayoutParams.flags |= LayoutParams.FLAG_NOT_TOUCHABLE;
        } else {
            mWindowManagerLayoutParams.flags |= LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        }
    }

    public void show() {
        configure();
        XToastQueue.getInstance().enqueue(this);
    }

    public void dismiss() {
        XToastQueue.getInstance().remove(this);
    }

    public boolean isShown() {
        return mXToastView != null && mXToastView.isShown();
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

    public XToast withDuration(int duration) {
        mDuration = duration;
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

    public XToast withPosition(View view, int position, int xOffset, int yOffset) {
        int[] pos = new int[2];
        view.getLocationOnScreen(pos);
        switch (position) {
            case Position.LEFT | Position.TOP:
                xOffset += pos[0];
                yOffset += pos[1];
                break;

            case Position.LEFT | Position.CENTER:
                xOffset += pos[0];
                yOffset += pos[1] + view.getHeight() / 2;
                break;

            case Position.LEFT | Position.BOTTOM:
                xOffset += pos[0];
                yOffset += pos[1] + view.getHeight();
                break;

            case Position.CENTER | Position.TOP:
                xOffset += pos[0] + view.getWidth() / 2;
                yOffset += pos[1];
                break;

            case Position.CENTER:
                xOffset += pos[0] + view.getWidth() / 2;
                yOffset += pos[1] + view.getHeight() / 2;
                break;

            case Position.CENTER | Position.BOTTOM:
                xOffset += pos[0] + view.getWidth() / 2;
                yOffset += pos[1] + view.getHeight();
                break;

            case Position.RIGHT | Position.TOP:
                xOffset += pos[0] + view.getWidth();
                yOffset += pos[1];
                break;

            case Position.RIGHT | Position.CENTER:
                xOffset += pos[0] + view.getWidth();
                yOffset += pos[1] + view.getHeight() / 2;
                break;

            case Position.RIGHT | Position.BOTTOM:
                xOffset += pos[0] + view.getWidth();
                yOffset += pos[1] + view.getHeight();
                break;

            case Position.LEFT:
            case Position.TOP:
                //equals to (LEFT | TOP)
                xOffset += pos[0];
                yOffset += pos[1];
                break;

            case Position.RIGHT:
                //equals to (RIGHT | TOP)
                xOffset += pos[0] + view.getWidth();
                yOffset += pos[1];
                break;

            case Position.BOTTOM:
                //equals to (LEFT | BOTTOM)
                xOffset += pos[0];
                yOffset += pos[1] + view.getHeight();
                break;
        }
        return withGravity(Gravity.TOP | Gravity.LEFT, xOffset, yOffset);
    }

    public XToast withCover(boolean cover) {
        mCover = cover;
        return this;
    }

    public XToast withButton(CharSequence text, Drawable icon, final ButtonClickListener listener) {
        if (text == null && icon == null) {
            return this;
        }
        if (mXToastView == null) {
            return this;
        }
        ((ViewStub) mXToastView.findViewById(R.id.xtoast_viewstub)).inflate();
        mButton = (Button) mXToastView.findViewById(R.id.xtoast_button);
        if (mButton != null) {
            if (text != null) {
                mButton.setText(text);
            }
            if (icon != null) {
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                mButton.setCompoundDrawables(icon, null, null, null);
            }
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(XToast.this);
                }
            });
        }
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

    public static class Position {
        public static final int LEFT = 0x0001;
        public static final int TOP = LEFT << 1;
        public static final int RIGHT = LEFT << 2;
        public static final int BOTTOM = LEFT << 3;
        public static final int CENTER = LEFT << 4;
    }

    private static class XToastQueue extends Handler {
        private static XToastQueue instance;
        private final LinkedList<XToast> queue = new LinkedList<XToast>();

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

        private XToastQueue() {
        }

        private void enqueue(XToast xtoast) {
            if (xtoast != null) {
                if (xtoast.mCover) {
                    XToast x = queue.peek();
                    synchronized (queue) {
                        queue.clear();
                        queue.add(xtoast);
                    }
                    remove(x);
                    return;
                }
                synchronized (queue) {
                    queue.add(xtoast);
                }
            }
            next();
        }

        private void remove(XToast xtoast) {
            if (xtoast != null) {
                synchronized (queue) {
                    queue.remove(xtoast);
                }
                dismiss(xtoast);
            }
            next();
        }

        private void removeCurrent() {
            XToast x;
            synchronized (queue) {
                x = queue.poll();
            }
            if (x == null) {
                return;
            }
            dismiss(x);
            next();
        }

        private void removeAll() {
            XToast x;
            synchronized (queue) {
                x = queue.peek();
                queue.clear();
            }
            if (x == null) {
                return;
            }
            dismiss(x);
            next();
        }

        private void show(XToast xtoast) {
            if (xtoast != null) {
                WindowManager wm = xtoast.mWindowManager;
                WindowManager.LayoutParams params = xtoast.mWindowManagerLayoutParams;
                View v = xtoast.mXToastView;
                if (wm != null && params != null && v != null) {
                    wm.addView(v, params);
                    Message msg = Message.obtain(this, 0, xtoast);
                    if (msg != null) {
                        sendMessageDelayed(msg, xtoast.mDuration + 1000);
                    }
                }
            }
        }

        private void dismiss(XToast xtoast) {
            if (xtoast == null) {
                return;
            }
            WindowManager wm = xtoast.mWindowManager;
            View v = xtoast.mXToastView;
            if (wm != null && v != null && xtoast.isShown()) {
                wm.removeView(v);
            }
        }

        private void next() {
            XToast x;
            synchronized (queue) {
                x = queue.peek();
            }
            if (x == null) {
                return;
            }
            if (x.isShown()) {
                return;
            }
            show(x);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                remove((XToast) msg.obj);
            }
        }
    }

    public static interface ButtonClickListener {
        void onClick(XToast xtoast);
    }
}
