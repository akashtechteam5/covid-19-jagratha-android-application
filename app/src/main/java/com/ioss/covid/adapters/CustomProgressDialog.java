package com.ioss.covid.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ioss.covid.R;
import com.ioss.covid.utilities.Constants;

public class CustomProgressDialog {

    private View dialogView;
    private ViewGroup activityBaseView;

    public CustomProgressDialog(Activity context) {

        this.activityBaseView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(context).inflate(R.layout.layout_custom_progressbar, activityBaseView, false);
        activityBaseView.addView(dialogView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hide();
    }

    public void show() {
        if (dialogView == null) {
            Constants.makeLog("Error: Dialog already dismissed");
            return;
        }
        dialogView.setVisibility(View.VISIBLE);
    }


    public void hide() {
        if (dialogView == null) {
            Constants.makeLog("Error: Dialog already dismissed");
            return;
        }
        dialogView.setVisibility(View.GONE);
    }

    public void dismiss() {
        if (dialogView == null) {
            Constants.makeLog("Error: Dialog already dismissed");
            return;
        }
        activityBaseView.removeView(dialogView);
        dialogView = null;
    }

    public boolean isShowing() {
        if (dialogView == null) {
            Constants.makeLog("Error: Dialog already dismissed");
            return false;
        }
        if (dialogView.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }
}
