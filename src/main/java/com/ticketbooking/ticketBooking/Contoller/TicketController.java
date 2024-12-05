package com.ticketbooking.ticketBooking.Contoller;

import com.ticketbooking.ticketBooking.ConfigurationManager;
import com.ticketbooking.ticketBooking.TicketBookingApplication;
import com.ticketbooking.ticketBooking.TicketConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController{

    @PostMapping("/start")
    public void setConfig(@RequestBody TicketConfig config){
        System.out.println("Configuration Created successfully"+config);
        TicketBookingApplication.stopper();
        //save the data to the "Configuration.json"
        ConfigurationManager.configurationSave(config);
        TicketBookingApplication.printConfig(config);    //print the ticket configuration information
        TicketBookingApplication.startThreads(config);    //start the threads on the backend
    }

    @PostMapping("/stop")
    @ResponseBody
    public String stopSimulation(){
        TicketBookingApplication.stopper();
        return "Simulation stopped successfully";

    }
}
