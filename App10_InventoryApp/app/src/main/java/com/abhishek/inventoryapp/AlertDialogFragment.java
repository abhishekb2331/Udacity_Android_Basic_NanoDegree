package com.abhishek.inventoryapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment{
    public static final String TITLE="title";
    public static final String MESSAGE="message";
    public static final String POSITIVE_MSG="positive";
    public static final String NEGATIVE_MSG="negative";
    private DialogInterface.OnClickListener PositiveButtonClickListener;
    private DialogInterface.OnClickListener NegativeButtonClickListener;
    public AlertDialogFragment(){

    }

    public static AlertDialogFragment instance(String title,String message,String positive,String negative){
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(MESSAGE,message);
        bundle.putString(POSITIVE_MSG,positive);
        bundle.putString(NEGATIVE_MSG,negative);
        alertDialogFragment.setArguments(bundle);
        return alertDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title=getArguments().getString(TITLE);
        String message= getArguments().getString(MESSAGE);
        String positive = getArguments().getString(POSITIVE_MSG);
        String negative = getArguments().getString(NEGATIVE_MSG);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(positive,PositiveButtonClickListener);
        alertDialogBuilder.setNegativeButton(negative,NegativeButtonClickListener);
        return alertDialogBuilder.create();
    }

    public void setPositiveButtonClickListener(DialogInterface.OnClickListener positiveButtonClickListener) {
        PositiveButtonClickListener = positiveButtonClickListener;
    }

    public void setNegativeButtonClickListener(DialogInterface.OnClickListener negativeButtonClickListener) {
        NegativeButtonClickListener = negativeButtonClickListener;
    }
}
