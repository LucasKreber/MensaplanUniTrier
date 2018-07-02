package de.verdion.mensaplan.Dialogs;

import android.app.AlertDialog;
import android.content.Context;

import de.verdion.mensaplan.Interfaces.CDialog;
import de.verdion.mensaplan.R;

public class VersionDialog implements CDialog{

    @Override
    public void show(Context context) {
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(context);
        alerDialogBuilder.setTitle(context.getString(R.string.new_in_this_version_title))
                .setMessage(context.getString(R.string.new_in_this_version_content))
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialogNewVersion = alerDialogBuilder.create();
        dialogNewVersion.show();
    }
}
