package de.verdion.mensaplan.HelperClasses;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Lucas on 31.03.2016.
 *
 */
public class FileUtils {

    public static void saveId(Context context, String id) {
        try {
            FileOutputStream outputStream = context.openFileOutput("id", Context.MODE_PRIVATE);
            outputStream.write(id.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readId(Context context){
        FileInputStream fis;
        StringBuilder sb = new StringBuilder();
        try {
            fis = context.openFileInput("id");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void savePriceConfig(Context context, String priceId) {
        try {
            FileOutputStream outputStream = context.openFileOutput("priceConfig", Context.MODE_PRIVATE);
            outputStream.write(priceId.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readPriceConfig(Context context){
        FileInputStream fis;
        StringBuilder sb = new StringBuilder();
        try {
            fis = context.openFileInput("priceConfig");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void writeFileToStorage(Context context, String data, String filename){
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileFromStorage(Context context, String filename){
        FileInputStream fis;
        StringBuilder sb = new StringBuilder();
        try {
            fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
