package de.verdion.mensaplan.HelperClasses;

import de.verdion.mensaplan.DataHolder.Config;

/**
 * Created by Lucas on 28.01.2017.
 */

public class OpeningTimes {

    public static String getOpeningTimes(int location, String theke, boolean isFriday){
        String openingTime = "";
        if (location == 0){
            if (theke.equals("UNTERGESCHOSS")){
                if (Config.IS_SEMESTER){
                    if (isFriday){
                        openingTime = "";
                    } else {
                        openingTime = "11:15 Uhr bis 14:15 Uhr";
                    }
                } else {
                    openingTime = "";
                }

            } else  if (theke.equals("THEKE 1") || theke.equals("THEKE 2")){
                if (Config.IS_SEMESTER){
                    if (isFriday){
                        openingTime = "11:15 Uhr bis 13:30 Uhr";
                    } else {
                        openingTime = "11:15 Uhr bis 13:45 Uhr";
                    }
                } else {
                    openingTime = "11:30 Uhr bis 13:30 Uhr";
                }

            } else if (theke.contains("forU")){
                if (Config.IS_SEMESTER){
                    if (isFriday){
                        openingTime = "08:15 bis 14:45 Uhr*";
                    } else {
                        openingTime = "08:15 Uhr bis 16:15 Uhr*";
                    }
                } else {
                    if (isFriday){
                        openingTime = "08:15 Uhr bis 14:45 Uhr*";
                    } else {
                        openingTime = "08:15 Uhr bis 15:45 Uhr*";
                    }

                }

            }
        } else if(location == 1){
            if (Config.IS_SEMESTER){
                if (theke.equals("ABENDMENSA")){
                    if (Config.IS_SEMESTER){
                        if (!isFriday){
                            openingTime = "17:30 Uhr bis 19:00 Uhr";
                        }
                    } else {
                        openingTime = "08:30 Uhr bis 16:30 Uhr*";
                    }

                } else  if (theke.equals("SAMSTAGSMENSA")){
                    openingTime = "11:30 Uhr bis 13:30 Uhr";
                } else {
                    if (isFriday){
                        openingTime = "07:45 Uhr bis 16:30 Uhr*";
                    } else {
                        openingTime = "07:45 Uhr bis 19:30 Uhr*";
                    }
                }
            } else {
                openingTime = "08:30 Uhr bis 16:30 Uhr*";
            }

        } else if (location == 2){
            if (Config.IS_SEMESTER){
                if (isFriday){
                    openingTime = "11:30 Uhr bis 13:30 Uhr";
                } else {
                    openingTime = "11:30 Uhr bis 13:45 Uhr";
                }
            } else {
                openingTime = "11:30 Uhr bis 13:30 Uhr";
            }
        }

        return openingTime;

    }
}
