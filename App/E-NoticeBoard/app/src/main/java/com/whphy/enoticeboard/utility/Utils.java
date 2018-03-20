package com.whphy.enoticeboard.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

/**
 * Created by Jaykishan on 26/7/2017.
 */

public class Utils {

    public static String LOGIN = "http://clients.whyphyinfotech.com/enotice/app/login.php";
    public static String GETCOLLEGES = "http://clients.whyphyinfotech.com/enotice/app/getColleges.php";
    public static String GETCOLLEGESBYNAME = "http://clients.whyphyinfotech.com/enotice/app/getCollegeByName.php";
    public static String SENDFEEDBACK = "http://clients.whyphyinfotech.com/enotice/app/setFeedbacks.php";
    public static String CHANGEPASS = "http://clients.whyphyinfotech.com/enotice/app/changePassword.php";
    public static String PREF = "pref";
    public static String USERID = "UID";
    public static String COLLEGEID = "COLLEGEUID";
    public static String BRANCH = "BRANCH";
    public static String USERNAME = "USERNAME";
    public static String ENROLL = "ENROLL";
    public static String GETBRANCHES = "http://clients.whyphyinfotech.com/enotice/app/getBranches.php";


    public static void saveUserId(Context context, String userid){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERID,userid);
        editor.apply();
    }

    public static void saveCollageId(Context context, String userid){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(COLLEGEID,userid);
        editor.apply();
    }

    public static String getCollageId(Context context){
        SharedPreferences sharedpreferences  = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return  sharedpreferences.getString(COLLEGEID,"null");
    }

    public static void logOut(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

    }

    public static void showErrorDialog(Context context,String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }
    public static String getUserId(Context context){
        SharedPreferences sharedpreferences  = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return  sharedpreferences.getString(USERID,"null");
    }

    public static void saveBranch(Context context, String userid){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BRANCH,userid);
        editor.apply();
    }

    public static String getBranch(Context context){
        SharedPreferences sharedpreferences  = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return  sharedpreferences.getString(BRANCH,"null");
    }

    public static void saveUserName(Context context, String name){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME,name);
        editor.apply();
    }

    public static String getUsername(Context context){
        SharedPreferences sharedpreferences  = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return  sharedpreferences.getString(USERNAME,"null");
    }

    public static void saveEnroll(Context context, String name){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ENROLL,name);
        editor.apply();
    }

    public static String getEnroll(Context context){
        SharedPreferences sharedpreferences  = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return  sharedpreferences.getString(ENROLL,"null");
    }


}
