package com.ticketbooking.ticketBooking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ConfigurationManager {

    //https://howtodoinjava.com/gson/pretty-print-json-output/
    public static void configurationSave(TicketConfig object){
        Gson newGsonfile = new GsonBuilder().setPrettyPrinting().create();
       try {
           FileWriter writer = new FileWriter("Configuration.json");
           newGsonfile.toJson(object, writer);
           System.out.println("Saved configuration to "+ "Configuration.json");
       }catch (Exception E){
           System.out.println("Error occured");
       }
       }

    public static TicketConfig configurationLoad(){
        Gson gsonFile = new Gson();
        try{
            FileReader reader = new FileReader("Configuration.json");
            return gsonFile.fromJson(reader, TicketConfig.class);
        }catch (Exception E){
            System.out.println("Error occured");
            return null;
        }
    }
}
