package com.ticketbooking.ticketBooking;

import com.ticketbooking.ticketBooking.Ticket;
import com.ticketbooking.ticketBooking.TicketPool;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class Vendor implements Runnable{
    private TicketPool ticketPool;
    private int releaseFrequency;
    private int maximumNumberOfTickets;
    private static int ticketCount=0;
    private static String[] eventlist = {"Event-1","Event-2","Event-3","Event-4","Event-5"};


    public Vendor(TicketPool ticketPool, int releaseFrequency, int maximumNumberOfTickets){
        this.ticketPool=ticketPool;
        this.releaseFrequency=releaseFrequency;
        this.maximumNumberOfTickets=maximumNumberOfTickets;
    }

    @Override
    public void run(){
        while(true){
            Random random = new Random();
            int numberOfReleasingTickets = (int) (Math.random() * maximumNumberOfTickets) + 1;

            for (int i = 0; i<numberOfReleasingTickets;i++){
                ticketCount++;
                int randomEvent = random.nextInt(5);
                Ticket newticket = new Ticket(eventlist[randomEvent],new BigDecimal(1000),new Date());
                ticketPool.addTicket(newticket);

            }
            try{
                Thread.sleep(releaseFrequency*1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }

    }
}