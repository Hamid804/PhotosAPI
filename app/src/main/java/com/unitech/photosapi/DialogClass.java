package com.unitech.photosapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class DialogClass {
    private Activity activity;
    private AlertDialog dialogg;


    public DialogClass(Activity myActivity){
        activity=myActivity;
    }


    @SuppressLint("InflateParams")
    public void startDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        View rootView=inflater.inflate(R.layout.custum_dialog,null);
        builder.setView(rootView);
//        builder.setCancelable(false);
        dialogg=builder.create();
        dialogg.show();
    }
    public void dismissDialog(){
        dialogg.dismiss();
    }
}
