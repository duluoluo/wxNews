package com.wxandroid.common.base;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wxandroid.common.R;


/**
 * Created by wenxin
 */
public class BaseDialog {
    Context context;
    android.app.AlertDialog dialog;
    TextView titleView;
    TextView messageView;
    Button ok_button, cancel_button;

    public BaseDialog(Context context) {
        this.context = context;
        dialog = new android.app.AlertDialog.Builder(context, R.style.BaseDialog).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.base_dialog);
        titleView = (TextView) window.findViewById(R.id.base_dialog_title_tv);
        messageView = (TextView) window.findViewById(R.id.base_dialog_content_tv);
        ok_button = (Button) window.findViewById(R.id.base_dialog_confirm_btn);
        cancel_button = (Button) window.findViewById(R.id.base_dialog_cancel_btn);
    }

    public BaseDialog setTitle(int resId) {
        titleView.setText(resId);
        return this;
    }

    public BaseDialog setTitle(String title) {
        titleView.setText(title);
        return this;
    }

    public BaseDialog setMessage(int resId) {
        messageView.setText(resId);
        return this;
    }

    public BaseDialog setMessage(String message) {
        messageView.setText(message);
        return this;
    }

    public BaseDialog setPositiveButton(String text, final View.OnClickListener listener) {
        ok_button.setText(text);
        ok_button.setOnClickListener(listener);
        return this;
    }

    public BaseDialog setNegativeButton(String text, final View.OnClickListener listener) {
        cancel_button.setText(text);
        cancel_button.setOnClickListener(listener);
        return this;
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
