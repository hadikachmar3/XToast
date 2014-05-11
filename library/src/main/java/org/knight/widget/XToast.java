package org.knight.widget;

import android.content.Context;
import android.graphics.PixelFormat;
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
 * 1. Text size/color</br>
 * 2. Background resource/color</br>
 * 3. Animation</br>
 * 4. Gravity and offset</br>
 * 5. Cover previous toast</br>
 * 6. Integrate a button in the toast(text/icon/event)
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
        if (mWindowManagerLayoutParams.gravity == 0) {
            mWindowManagerLayoutParams.gravity = Gravity.CENTER;
        }
        if (mButton == null) {
            mWindowManagerLayoutParams.flags |= LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
        } else {
            mWindowManagerLayoutParams.flags = LayoutParams.FLAG_KEEP_SCREEN_ON;
        }
    }

    public void show() {
        configure();
        XToastQueue.getInstance().add(this);
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

    public XToast withCover(boolean cover) {
        mCover = cover;
        return this;
    }

    public XToast withButton(CharSequence text, final ButtonClickListener listener) {
        if (mXToastView == null) {
            return this;
        }
        ((ViewStub) mXToastView.findViewById(R.id.xtoast_viewstub)).inflate();
        mButton = (Button) mXToastView.findViewById(R.id.xtoast_button);
        if (mButton != null) {
            mButton.setText(text);
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

        private void add(XToast xtoast) {
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
            showNext();
        }

        private void remove(XToast xtoast) {
            if (xtoast != null) {
                synchronized (queue) {
                    queue.remove(xtoast);
                }
                WindowManager wm = xtoast.mWindowManager;
                View v = xtoast.mXToastView;
                if (wm != null && v != null && xtoast.isShown()) {
                    wm.removeView(v);
                }
            }
            showNext();
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
                        sendMessageDelayed(msg, xtoast.mDuration + 500);
                    }
                }
            }
        }

        private void showNext() {
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
