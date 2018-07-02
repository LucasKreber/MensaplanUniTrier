package com.codebutler.farebot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;



/**
 * Created by wenzel on 28.11.14.
 */
public class NfcOffFragment extends DialogFragment {
	public static final String TAG = "NfcOffFragment";

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
			.setTitle("NFC ausgeschaltet")
			.setMessage("Um die neue Guthabenfunktion nutzen zu können, musst du NFC in deinen Einstellungen aktivieren.")
			.setCancelable(true)
			.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			})
			.setNeutralButton("Einstellungen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
						getActivity().startActivity(intent);
					} else {
						Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
						getActivity().startActivity(intent);
					}
				}
			}).create();
	}
}
