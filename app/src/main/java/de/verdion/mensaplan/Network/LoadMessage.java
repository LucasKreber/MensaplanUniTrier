package de.verdion.mensaplan.Network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import de.verdion.mensaplan.DataHolder.APIConfig;
import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.MessageObject;
import de.verdion.mensaplan.Interfaces.InterfaceSemesterLoad;
import de.verdion.mensaplan.Logger.CLog;

/**
 * Created by Lucas on 04.11.2016.
 */

public class LoadMessage extends AsyncTask<Void,Void,Void> {

    private StringBuilder builder;
    private MessageObject messageObject;
    private Context context;
    private InterfaceSemesterLoad interfaceSemesterLoad;
    private boolean onlySemester;

    public LoadMessage(Context context, InterfaceSemesterLoad interfaceSemesterLoad, boolean onlySemester) {
        this.context = context;
        this.interfaceSemesterLoad = interfaceSemesterLoad;
        this.onlySemester = onlySemester;
    }

    @Override
    protected Void doInBackground(Void... params) {
        builder = new StringBuilder();

        try {
            URL url = new URL(APIConfig.BASE_URL+APIConfig.ENDPOINT_MESSAGE+APIConfig.GET_BEGIN+APIConfig.GET_KEY+APIConfig.API_KEY);
            CLog.p(url.toString());
            BufferedReader buf = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while((line = buf.readLine()) != null){
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(builder.toString());
            messageObject = new MessageObject();
            messageObject.setTitle(jsonObject.getString("title"));
            messageObject.setMessage(jsonObject.getString("message"));
            messageObject.setButtonTitle(jsonObject.getString("buttonTitle"));
            messageObject.setVersion(jsonObject.getInt("version"));
            messageObject.setCancelable(jsonObject.getInt("cancelable"));
            if (jsonObject.getInt("semester") == 1){
                Config.IS_SEMESTER = true;
            } else {
                Config.IS_SEMESTER = false;
            }

            if (onlySemester)
                messageObject = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        interfaceSemesterLoad.isSemesterLoaded();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int version = preferences.getInt("version", 0);
        if (messageObject != null){
            if(version < messageObject.getVersion()) {
                ((TextView) new AlertDialog.Builder(context)
                        .setTitle(correctText(messageObject.getTitle()))
                        .setMessage(Html.fromHtml(correctText(messageObject.getMessage())))
                        .setPositiveButton(messageObject.getButtonTitle(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show()
                        .findViewById(android.R.id.message))
                        .setMovementMethod(LinkMovementMethod.getInstance());
            }

            if (messageObject.isCancelable() == 1){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("version",messageObject.getVersion());
                editor.apply();
            }
        }

    }

    private String correctText(String text){
        text = text.replace("_a", "ä");
        text = text.replace("_A", "Ä");
        text = text.replace("_o", "ö");
        text = text.replace("_O", "Ö");
        text = text.replace("_u", "ü");
        text = text.replace("_U", "Ü");
        return text;
    }
}
