package com.ticketbooking.ticketBooking;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import java.io.*;

public class ConfigurationManager {


    /**
     * Saves the configuration data to the json file and creates a file if it did not exist
     * @param ticketObject The reference to the memory location of the ticket object
     */
    public static void configurationSave(TicketConfig ticketObject){
        ObjectMapper objmapper = new ObjectMapper();
        try{
            File configFile = new File("/Users/isharad/Documents/GitHub/TicketBookingOOP/src/main/java/com/ticketbooking/ticketBooking/Configuration.json");
            if(!configFile.exists()){
                configFile.createNewFile();
            }

            //https://stackoverflow.com/questions/77677871/jackson-writevalue-is-ambiguous-for-the-type-objectmapper
            objmapper.writeValue(configFile,ticketObject);
            System.out.println("Configuration saved.");
        }catch (IOException e){
            System.out.println("Error occured while writing to the configuration file");

        }
    }

    /**
     * Checks if the configuration file exists
     * @return the object of the configuration file to class
     */
    public static TicketConfig configurationLoad(){
        ObjectMapper objmapp = new ObjectMapper();
        try{
            return objmapp.readValue(new File("/Users/isharad/Documents/GitHub/TicketBookingOOP/src/main/java/com/ticketbooking/ticketBooking/Configuration.json"), TicketConfig.class);
        }catch (IOException e){
            System.out.println("Enter new Configuration");
            return null;
        }
    }

//    //https://howtodoinjava.com/gson/pretty-print-json-output/
//    public static void configurationSave(TicketConfig object){
//        Gson newGsonfile = new GsonBuilder().setPrettyPrinting().create();
//       try {
//           FileWriter writer = new FileWriter("Configuration.json");
//           newGsonfile.toJson(object, writer);
//           System.out.println("Saved configuration to "+ "Configuration.json");
//       }catch (Exception E){
//           System.out.println("Error occured");
//       }
//       }
//
//    public static TicketConfig configurationLoad(){
//        Gson gsonFile = new Gson();
//        try{
//            FileReader reader = new FileReader("Configuration.json");
//            return gsonFile.fromJson(reader, TicketConfig.class);
//        }catch (Exception E){
//            System.out.println("Error occured");
//            return null;
//        }
//    }
}
