package com.wxandroid.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.wxandroid.common.R;


public class AlertDialogUtil {
    public static android.support.v7.app.AlertDialog.Builder showDialog(Context activity,
                                                                        int titleId,
                                                                        int msgId,
                                                                        int leftBtnTextId,
                                                                        int rightBtnTextId,
                                                                        DialogInterface.OnClickListener leftClickListener,
                                                                        DialogInterface.OnClickListener rightClickListener) {
        return showDialog(activity,
                AppUtils.ls(titleId),
                AppUtils.ls(msgId),
                leftBtnTextId <= 0 ? "" : AppUtils.ls(leftBtnTextId),
                rightBtnTextId <= 0 ? "" : AppUtils.ls(rightBtnTextId),
                leftClickListener,
                rightClickListener);
    }

    public static android.support.v7.app.AlertDialog.Builder showDialog(Context activity,
                                                                        String title,
                                                                        String msg,
                                                                        String leftBtnText,
                                                                        String rightBtnText,
                                                                        DialogInterface.OnClickListener leftClickListener,
                                                                        DialogInterface.OnClickListener rightClickListener) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity, R.style.AppThemeDialogAlert);
        builder.setTitle(title);
        builder.setMessage(msg);
        if (!TextUtils.isEmpty(leftBtnText)) {
            builder.setNegativeButton(leftBtnText, leftClickListener);
        }

        if (!TextUtils.isEmpty(rightBtnText)) {
            builder.setPositiveButton(rightBtnText, rightClickListener);
        }
        builder.setCancelable(false);
        builder.show();

        return builder;
    }

    public static android.support.v7.app.AlertDialog.Builder getBaseAlertDialog(Context activity) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity, R.style.AppThemeDialogAlert);

        return builder;
    }
}
