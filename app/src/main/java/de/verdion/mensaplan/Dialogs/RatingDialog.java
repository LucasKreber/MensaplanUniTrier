package de.verdion.mensaplan.Dialogs;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import de.verdion.mensaplan.HelperClasses.FileUtils;
import de.verdion.mensaplan.Interfaces.CDialog;
import de.verdion.mensaplan.MainActivity;
import de.verdion.mensaplan.R;

public class RatingDialog implements CDialog{
    @Override
    public void show(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(context);
        alerDialogBuilder.setTitle(context.getString(R.string.rating_title))
                .setMessage(context.getString(R.string.rating_message))
                .setCancelable(false)
                .setNeutralButton("bewerten", (dialog, which) -> {
                    editor.putInt("ratingOption", -1);
                    editor.commit();
                    Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        context.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                    }
                })
                .setNegativeButton("Später", (dialog, which) -> {
                    editor.putInt("ratingOption", 0);
                    editor.commit();
                    dialog.dismiss();
                })
                .setPositiveButton("Nie", (dialog, which) -> editor.putInt("ratingOption", -1));


        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();
    }
}
