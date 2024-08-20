package com.cohen.myspantext;

import android.content.Context;

public class SpannableText_Singleton {
    private static SpannableText_Singleton me;
    private Context context;

    private SpannableText_Singleton(Context context) {
    this.context = context.getApplicationContext();
}

    public static void init(Context context) {
        if (me == null) {
            me = new SpannableText_Singleton(context);
        }
    }

    public Context getContext() {
        return context;
    }

    public static SpannableText_Singleton getMe() {
        return me;
    }
}
