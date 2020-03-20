package com.ioss.covid.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.ioss.covid.R;
import com.ioss.covid.utilities.Utility;

import static com.ioss.covid.core.AppConfig.openSansRegular;

/**
 * Created by ioss on 16/11/16.
 */
public class CustomAlertDialog {

    private final TextView titleTextView;
    private final TextView messageTextView;
    private View tittleLine;
    AlertDialog.Builder alertBuilder;
    AlertDialog dialog;
    Button buttonCancel;
    Button buttonOk;
    View dash;
    private Context context;

    public CustomAlertDialog(Context context) {

        this.context=context;

        alertBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_boxnew, null);
        alertBuilder.setView(view);

        dialog = alertBuilder.create();

        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonOk = (Button) view.findViewById(R.id.button_ok);
        buttonCancel.setVisibility(View.GONE);
        buttonOk.setVisibility(View.GONE);
        titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        messageTextView = (TextView) view.findViewById(R.id.message_text_view);
        dash = view.findViewById(R.id.dash);
        messageTextView.setMovementMethod(new ScrollingMovementMethod());
        tittleLine = view.findViewById(R.id.dash);
        titleTextView.setText(context.getString(R.string.message));

        titleTextView.setTypeface(openSansRegular);
        messageTextView.setTypeface(openSansRegular);
        buttonOk.setTypeface(openSansRegular);
        buttonCancel.setTypeface(openSansRegular);

      show();
    }
    public static CustomAlertDialog create(Context context) {
        return new CustomAlertDialog(context);
    }


    private View.OnClickListener defaultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    public CustomAlertDialog setOkButton(String title, View.OnClickListener onClickListener) {
        buttonOk.setVisibility(View.VISIBLE);
        buttonOk.setText(title);
        if (onClickListener != null) {
            buttonOk.setOnClickListener(onClickListener);
        } else {
            buttonOk.setOnClickListener(defaultListener);
        }
        return this;
    }

    public CustomAlertDialog setCancelButton(String title, View.OnClickListener onClickListener) {
        buttonCancel.setVisibility(View.VISIBLE);
        buttonCancel.setText(title);
        if (onClickListener != null) {
            buttonCancel.setOnClickListener(onClickListener);
        } else {
            buttonCancel.setOnClickListener(defaultListener);
        }
        return this;
    }

    public CustomAlertDialog setMessage(String message) {
        messageTextView.setText(message);
        return  this;
    }

    public CustomAlertDialog setHtmlContentMessage(String message) {
        messageTextView.setText(Utility.fromHtml(message));
        return this;
    }

    public CustomAlertDialog setTitle(String message) {
        titleTextView.setText(message);
        titleTextView.setVisibility(View.VISIBLE);
        dash.setVisibility(View.VISIBLE);

//        if(message==null || message.equalsIgnoreCase("")) {
//            tittleLine.setVisibility(View.GONE);
//        }
        return this;
    }

    public CustomAlertDialog setCancelable(Boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        return this;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        if (context instanceof Activity){
            if (((Activity)context).isFinishing()){
                return;
            }
        }
        dialog.show();
    }

    private CustomAlertDialog makeSimpleAlert(String title, String message) {
        if (title != null) {
            setTitle(title);
        }
        setMessage(message);
        buttonOk.setVisibility(View.VISIBLE);
        buttonOk.setOnClickListener(defaultListener);
        return this;
    }

    public static void makeSimpleAlert(Context context, String title, String message) {
        CustomAlertDialog alertDialog = new CustomAlertDialog(context);
        alertDialog.makeSimpleAlert(title, message);
    }

/*
    public static void showNetworkError(Context context){
        CustomAlertDialog alertDialog = new CustomAlertDialog(context);
        alertDialog.makeSimpleAlert(null, context.getString(R.string.no_internet_connection));
    }
*/
}
