package com.ioss.covid.utilities;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Utility {

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static void setFonts(Typeface typeface, Object... objects) {

        for (Object object : objects) {
            if (object instanceof TextView) {
                ((TextView) object).setTypeface(typeface);
            } else if (object instanceof EditText) {
                ((EditText) object).setTypeface(typeface);

            } else if (object instanceof TextInputLayout) {
                ((TextInputLayout) object).setTypeface(typeface);
                (((TextInputLayout) object).getEditText()).setTypeface(typeface);
                setErrorClearTextWatcher((TextInputLayout) object);
            } else if (object instanceof Button) {
                ((Button) object).setTypeface(typeface);
            }
        }
    }

    public static void setErrorClearTextWatcher(final TextInputLayout textInputLayout){

        final EditText editText=textInputLayout.getEditText();


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

}
