package com.ticketbooking.ticketBooking.Contoller;

import com.ticketbooking.ticketBooking.ConfigurationManager;
import com.ticketbooking.ticketBooking.TicketBookingApplication;
import com.ticketbooking.ticketBooking.TicketConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController{

    @PostMapping("/start")
    public void setConfig(@RequestBody TicketConfig config){
        System.out.println("Configuration Created successfully"+config);
        //save the data to the "Configuration.json"
        ConfigurationManager.configurationSave(config);
        TicketBookingApplication.printConfig(config);    //print the ticket configuration information
        TicketBookingApplication.startThreads(config);    //start the threads on the backend
    }
}
