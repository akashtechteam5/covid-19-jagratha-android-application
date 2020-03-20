package com.ioss.covid.utilities;

import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.ioss.covid.R;

public class Validation {

    private Context context;

    public Validation(Context context) {
        this.context = context;
    }

    private static boolean isEmpty(String content) {

        if (content.length() == 0) {
            return true;
        }
        return false;
    }

    private void setError(Object object, String error) {
        if (object instanceof TextInputLayout) {
            ((TextInputLayout) object).setError(error);
            ((TextInputLayout) object).clearFocus();
            ((TextInputLayout) object).requestFocus();
        } else if (object instanceof EditText) {
            ((EditText) object).setError(error);
            ((EditText) object).clearFocus();
            ((EditText) object).requestFocus();
        }
    }
    private void setErrorNull(Object object) {
        if (object instanceof TextInputLayout) {
            ((TextInputLayout) object).setError(null);
        } else if (object instanceof EditText) {
            ((EditText) object).setError(null);
        }
    }

//    public boolean isMailValid(Object object, String mail) {
//
//        if (isEmpty(mail)) {
//            setError(object, context.getString(R.string.error_message_mail_empty));
//            return false;
//        }
//        if (mail.length()<8) {
//            setError(object, context.getString(R.string.error_message_mail_length));
//            return false;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
//            setError(object, context.getString(R.string.error_message_mail));
//            return false;
//        }
//        setErrorNull(object);
//        return true;
//    }

    public boolean isMobileValid(Object object, String mobile) {

        if (isEmpty(mobile)) {
            setError(object, context.getString(R.string.error_message_mobile_empty));
            return false;
        }

        if (mobile.length() != 10) {
            setError(object, context.getString(R.string.error_message_mobile));
            return false;
        }

        setErrorNull(object);
        return true;

    }

    public boolean isConfirmPwdValid(Object object, String password,String confirm_pwd) {

        if (isEmpty(confirm_pwd)) {
            setError(object, context.getString(R.string.message_err_password_empty));
            return false;
        }

        if (!password.equals(confirm_pwd)) {
            setError(object, context.getString(R.string.error_message_confirm_pwd));
            return false;
        }

        setErrorNull(object);
        return true;

    }


    public boolean isNameValid(Object object, String name) {

        if (isEmpty(name)) {
            setError(object, context.getString(R.string.error_message_name_empty));
            return false;
        }

/*
        if (name.length() < 3) {
            setError(object, context.getString(R.string.error_message_name));
            return false;
        }
*/

       setErrorNull(object);
        return true;

    }

/*
    public boolean isOtpValid(Object object, String otp) {

        if (isEmpty(otp)) {
            setError(object, context.getString(R.string.error_message_otp_empty));
            return false;
        }

        if (otp.length() != 4) {
            setError(object, context.getString(R.string.error_message_otp));
            return false;
        }
        setErrorNull(object);
        return true;
    }
*/
/*
    public boolean isChangeMobileOtpValid(Object object, String otp) {

        if (isEmpty(otp)) {
            setError(object, context.getString(R.string.error_message_otp_empty));
            return false;
        }

        if (otp.length() != 6) {
            setError(object, context.getString(R.string.error_message_otp));
            return false;
        }
        setErrorNull(object);
        return true;
    }
*/

    public boolean isEmptyValidation(Object object, String content, String errMessage){

        if (isEmpty(content)) {
            setError(object,errMessage);
            return false;
        }
        setErrorNull(object);
        return true;
    }

}