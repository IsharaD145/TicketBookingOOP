package com.ticketbooking.ticketBooking.services;

import com.ticketbooking.ticketBooking.model.Ticket;
import com.ticketbooking.ticketBooking.services.TicketPool;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

/**
 * Represents a vendor responsible for releasing tickets into the ticket pool.
 * A vendor continuously releases a random number of tickets at a specified frequency
 * until interrupted.
 */
public class Vendor implements Runnable{
    private TicketPool ticketPool;
    private int releaseFrequency;
    private int maximumNumberOfTickets;
    private static int ticketCount=0;
    private static String[] eventlist = {"Event-1","Event-2","Event-3","Event-4","Event-5"};


    /**
     * Constructs a Vendor with the specified ticket pool, release frequency, and maximum tickets to release.
     *
     * @param ticketPool           the shared ticket pool to which tickets will be added.
     * @param releaseFrequency     the frequency (in seconds) at which tickets are released.
     * @param maximumNumberOfTickets the maximum number of tickets to release in each cycle.
     */
    public Vendor(TicketPool ticketPool, int releaseFrequency, int maximumNumberOfTickets){
        this.ticketPool=ticketPool;
        this.releaseFrequency=releaseFrequency;
        this.maximumNumberOfTickets=maximumNumberOfTickets;
    }

    /**
     * Executes the vendor's ticket releasing process in a separate thread.
     * Continuously releases tickets until the thread is interrupted.
     */
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            Random random = new Random();
            int numberOfReleasingTickets = (int) (Math.random() * maximumNumberOfTickets) + 1;

            for (int i = 0; i<numberOfReleasingTickets;i++){
                ticketCount++;
                int randomEvent = random.nextInt(5);
                Ticket newticket = new Ticket(eventlist[randomEvent],new BigDecimal(1000),new Date());
                try {
                    ticketPool.addTicket(newticket);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            try{
                Thread.sleep(releaseFrequency*1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        System.out.println(Thread.currentThread().getName() + "Is interrupted");
    }
}