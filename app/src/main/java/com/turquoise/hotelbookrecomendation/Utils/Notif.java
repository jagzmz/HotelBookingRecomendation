package com.turquoise.hotelbookrecomendation.Utils;

import android.content.Context;
import android.widget.Toast;

public class Notif {

    public static void showToast(Context ctx, String s) {

        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
    }
}
