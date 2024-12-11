package com.ticketbooking.ticketBooking.Contoller;

import com.ticketbooking.ticketBooking.*;
import com.ticketbooking.ticketBooking.config.ConfigurationManager;
import com.ticketbooking.ticketBooking.model.TicketConfig;
import com.ticketbooking.ticketBooking.services.Consumer;
import com.ticketbooking.ticketBooking.services.TicketPool;
import com.ticketbooking.ticketBooking.services.Vendor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TicketController{



    @PostMapping("/save")
    public void setConfig(@RequestBody TicketConfig config){
        System.out.println("Configuration Created successfully"+config);
        //save the data to the "Configuration.json"
        ConfigurationManager.configurationSave(config);
        TicketBookingApplication.printConfig(config);    //print the ticket configuration information
        TicketBookingApplication.stopper();
    }

    @PostMapping("/stop")
    @ResponseBody
    public String stopSimulation(){
        TicketBookingApplication.stopper();
        return "Simulation stopped successfully";

    }

    @GetMapping("/start")
    public ResponseEntity<Map<String, Integer>> startSim(){
        TicketConfig config = ConfigurationManager.configurationLoad();
        TicketBookingApplication.printConfig(config);
        TicketBookingApplication.startThreads(config);    //start the threads on the backend
        int vendorsCount = config.getNoOfVendors();
        int consumersCount = config.getNoOfConsumers();
        Map<String, Integer> response = new HashMap<>();
        response.put("vendorsCount", vendorsCount);
        response.put("consumersCount", consumersCount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/increaseVendor")
    public void increaseVendor(){
        TicketConfig config = ConfigurationManager.configurationLoad();
        TicketPool ticketpool = new TicketPool(config.getMaxTicketCapacity());
        Vendor tred = new Vendor(ticketpool, config.getTicketReleaseRate(), config.getTotalTicketsByVendor());
        Thread vendorThread = new Thread(tred, "vendor-" + (config.getNoOfVendors()+1));
        config.setNoOfVendors((config.getNoOfVendors()+1));
        ConfigurationManager.configurationSave(config);
        vendorThread.start();
        TicketBookingApplication.addvendor(vendorThread);
    }

    @GetMapping("/decreaseVendor")
    public void decreaseVenddor(){
        TicketConfig config = ConfigurationManager.configurationLoad();
        Thread removable = TicketBookingApplication.returnremoveVendor();
        removable.stop();
        config.setNoOfVendors(config.getNoOfVendors()-1);
        ConfigurationManager.configurationSave(config);
    }

    @GetMapping("/addconsumer")
    public void addConsumer(){
        TicketConfig config = ConfigurationManager.configurationLoad();
        TicketPool ticketpool = new TicketPool(config.getMaxTicketCapacity());
        Consumer tred = new Consumer(ticketpool,config.getCustomerRetreivalRate(),config.getTotalTicketsByConsumer(),false);
        Thread consumerThread = new Thread(tred,"consumer-"+(config.getNoOfConsumers()+1));
        config.setNoOfConsumers((config.getNoOfConsumers()+1));
        ConfigurationManager.configurationSave(config);
        consumerThread.start();
        TicketBookingApplication.addConsumer(consumerThread);
    }

    @GetMapping("/decreaseConsumer")
    public void decreseConsumer(){
        TicketConfig config = ConfigurationManager.configurationLoad();
        Thread removable = TicketBookingApplication.returnremoveConsumer();
        removable.stop();
        config.setNoOfConsumers(config.getNoOfConsumers()-1);
        ConfigurationManager.configurationSave(config);
    }

    @GetMapping("/gettotaltickets")
    public ResponseEntity<Integer> gettotaltickets(){
        TicketConfig config = ConfigurationManager.configurationLoad();
       return ResponseEntity.ok(config.getMaxTicketCapacity());
    }
}
