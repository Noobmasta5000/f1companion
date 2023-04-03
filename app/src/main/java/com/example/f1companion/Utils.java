// https://www.c-sharpcorner.com/UploadFile/88b6e5/change-theme-of-layout-runtime-by-button-click-in-android/

package com.example.f1companion;

import android.app.Activity;
import android.content.Intent;
public class Utils
{
    private static int sTheme;

    public final static int THEME_ALFA_ROMEO = 1;
    public final static int THEME_ALPHA_TAURI = 2;
    public final static int THEME_ALPINE = 3;
    public final static int THEME_ASTON_MARTIN = 4;
    public final static int THEME_FERRARI = 5;
    public final static int THEME_HAAS = 6;
    public final static int THEME_MCLAREN = 7;
    public final static int THEME_MERCEDES = 8;
    public final static int THEME_RED_BULL = 9;
    public final static int THEME_WILLIAMS = 10;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.recreate();
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_ALFA_ROMEO:
                activity.setTheme(R.style.alfaromeo);
                break;
            case THEME_ALPHA_TAURI:
                activity.setTheme(R.style.alphatauri);
                break;
            case THEME_ALPINE:
                activity.setTheme(R.style.alpine);
                break;
            case THEME_ASTON_MARTIN:
                activity.setTheme(R.style.astonmartin);
                break;
            case THEME_FERRARI:
                activity.setTheme(R.style.ferrari);
                break;
            case THEME_HAAS:
                activity.setTheme(R.style.haas);
                break;
            case THEME_MCLAREN:
                activity.setTheme(R.style.mclaren);
                break;
            case THEME_MERCEDES:
                activity.setTheme(R.style.mercedes);
                break;
            case THEME_RED_BULL:
                activity.setTheme(R.style.redbull);
                break;
            case THEME_WILLIAMS:
                activity.setTheme(R.style.williams);
                break;
        }
    }
}
