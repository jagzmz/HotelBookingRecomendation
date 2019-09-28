package com.turquoise.hotelbookrecomendation.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Utilities {


    public static void newActivity(Context context, Class<?> cls){
        Intent i= new Intent(context,cls);
        context.startActivity(i);
        ((Activity)context).finish();
    }
}
