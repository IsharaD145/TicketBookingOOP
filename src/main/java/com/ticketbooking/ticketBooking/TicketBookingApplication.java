package com.ticketbooking.ticketBooking;
import java.util.*;

import com.ticketbooking.ticketBooking.config.ConfigurationManager;
import com.ticketbooking.ticketBooking.model.TicketConfig;
import com.ticketbooking.ticketBooking.services.Consumer;
import com.ticketbooking.ticketBooking.services.TicketPool;
import com.ticketbooking.ticketBooking.services.Vendor;
import com.ticketbooking.ticketBooking.util.DatabaseUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;

/**
 * Main application class for the Ticket Booking simulation.
 * Handles the configuration of the simulation and the startup of vendor and consumer threads.
 */
@SpringBootApplication
public class TicketBookingApplication {
	private static List<Thread> runningVendorThreads = new ArrayList<>();
	private static List<Thread> runningConsumerThreads = new ArrayList<>();
	static Scanner input = new Scanner(System.in);

	/**
	 * Main method to start the Ticket Booking simulation.
	 * It provides options for running the simulation via CLI or exiting.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
        SpringApplication.run(TicketBookingApplication.class, args);
		int runmethod=0;

				System.out.println("***********************************************************");
				System.out.println("*             WELCOME TO TICKET BOOKING SIMULATION        *");
				System.out.println("***********************************************************");
				System.out.println("*  1. Continue in CLI                                     *");
				System.out.println("*  2. exit                                                *");
				System.out.println("***********************************************************");
		while(true){
			try {
				runmethod = input.nextInt();

				if(runmethod != 1 && runmethod != 2){
					System.out.println("only input (1,2)");
					continue;
				}
				break;
			}catch (InputMismatchException e){
				System.out.println("Enter valid data type");
				input.nextLine();
			}
		}


		switch (runmethod) {
			case 1:
				runCLI();
				break;

			case 2:
				System.exit(0);
				break;
		}
	}

	/**
	 * Runs the CLI interface to configure and start the simulation.
	 */
	private static void runCLI() {
		int choice = 0;

		System.out.println("***********************************************************");
		System.out.println("*             WELCOME TO TICKET BOOKING SIMULATION       *");
		System.out.println("***********************************************************");
		System.out.println("* SELECT AN OPTION TO PROCEED (1, 2)                     *");
		System.out.println("* 1. CHECK FOR CONFIGURATION FILE AND START SIMULATION   *");
		System.out.println("* 2. ENTER NEW CONFIGURATION INFORMATION START SIMULATION *");
		System.out.println("***********************************************************");

		while (true) {
			try {
				choice = input.nextInt();
				if ((choice > 2) || (choice < 1)) {
					System.out.println("Please enter a valid option, (1, 2)");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter valid data type (1, 2)");
				input.nextLine();
			}
		}

		TicketConfig ticketConfig = null;
		switch (choice) {
			case 1:
				ticketConfig = ConfigurationManager.configurationLoad();
				if (ticketConfig == null) {
					System.out.println("Failed to load Configuration, Configuration does not exist");
					System.out.println("Enter the configuration details to make configuration");
					ticketConfig = new TicketConfig();
					makeConfiguration(ticketConfig);
				}
				break;

			case 2:
				System.out.println("Enter Configuration Details.");
				ticketConfig = new TicketConfig();
				makeConfiguration(ticketConfig);
				break;
		}

		printConfig(ticketConfig);
		startThreads(ticketConfig);


    }

	/**
	 * Prints the current configuration of the ticket booking simulation.
	 *
	 * @param ticketConfig The configuration object containing ticket details.
	 */
	public static void printConfig(TicketConfig ticketConfig){
		System.out.println("\n*******************************************************");
		System.out.println("*        Ticket Configuration information             *");
		System.out.println("*******************************************************");
		System.out.println("*   Maximum tickets can be added by vendor: " + ticketConfig.getTotalTicketsByVendor()+"         *");
		System.out.println("*   Ticket Release Rate: " + ticketConfig.getTicketReleaseRate()+"                            *");
		System.out.println("*   Maximum tickets can be bought by consumer: " + ticketConfig.getTotalTicketsByConsumer()+"      *");
		System.out.println("*   Ticket Retrieval Rate: " + ticketConfig.getCustomerRetreivalRate()+"                          *");
		System.out.println("*   Maximum Ticket Capacity: " + ticketConfig.getMaxTicketCapacity()+"                      *");
		System.out.println("*******************************************************");

	}


	public static void addvendor( Thread threadrec){
		runningVendorThreads.add(threadrec);
	}
	public static Thread returnremoveVendor(){
		return runningVendorThreads.remove(0);
	}

	public static void addConsumer(Thread threadrec){
		runningConsumerThreads.add(threadrec);
	}

	public static Thread returnremoveConsumer(){
		return runningConsumerThreads.remove(0);
	}

	/**
	 * Starts the vendor and consumer threads based on the configuration details.
	 *
	 * @param ticketConfig The configuration object containing simulation details.
	 */
	public static void startThreads(TicketConfig ticketConfig){
		DatabaseUtil.initializeTables();
		// Run the simulation using the configuration data
		TicketPool ticketpool = new TicketPool(ticketConfig.getMaxTicketCapacity());
		for (int i = 0; i < ticketConfig.getNoOfVendors(); i++) {
			Vendor tred = new Vendor(ticketpool, ticketConfig.getTicketReleaseRate(), ticketConfig.getTotalTicketsByVendor());
			Thread vendorThread = new Thread(tred, "vendor-" + i);
			vendorThread.start();
			runningVendorThreads.add(vendorThread);

		}

		Consumer[] cusotmer = new Consumer[ticketConfig.getNoOfConsumers()];
		Random random = new Random();
		boolean vipstatus = false;
		for (int i = 0; i < cusotmer.length; i++) {
			int number = random.nextInt(2) + 1;
			switch (number){
				case 1:
					vipstatus = true;
					break;

				case 2:
					vipstatus=false;
					break;
			}
			cusotmer[i] = new Consumer(ticketpool, ticketConfig.getCustomerRetreivalRate(), ticketConfig.getTotalTicketsByConsumer(),vipstatus);
			Thread customerThread = new Thread(cusotmer[i],vipstatus?"VIP Customer-"+i:"Customer-" + i);
			customerThread.start();
			runningConsumerThreads.add(customerThread);

		}
	}


	/**
	 * Stops all running vendor and consumer threads and clears the thread lists.
	 */
	public static void stopper(){
		for (Thread thread:runningVendorThreads){
			if(thread!=null && thread.isAlive()){
				thread.stop();
			}
		}
		for (Thread thread:runningConsumerThreads){
			if(thread!=null && thread.isAlive()){
				thread.stop();
			}
		}
		runningVendorThreads.clear();
		runningConsumerThreads.clear();
	}

	/**
	 * Collects and sets the configuration details from the user.
	 *
	 * @param ticketConfig The TicketConfig object to store the configuration details.
	 */
	public static void makeConfiguration(TicketConfig ticketConfig){

		int totalTicketsVendor,totalTicketsCustomer, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity = 0,numOfVendors,numOfConsumers;

		// Loop for entering Maximum Ticket Capacity
		while (true) {
			try {
				System.out.print("Enter Maximum Ticket Capacity: ");
				maxTicketCapacity = input.nextInt();
				if (maxTicketCapacity < 1) {
					System.out.println("Maximum Ticket Capacity should be a positive number");
					continue;
				}
				ticketConfig.setMaxTicketCapacity(maxTicketCapacity);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for Maximum Ticket Capacity");
				input.nextLine();
			}
		}


		// Loop for entering Total Tickets that could be added by a vendor at a time.
		while (true) {
			try {
				System.out.print("Enter Maximum number of Tickets that could be added by a vendor at a time: ");
				totalTicketsVendor = input.nextInt();
				if ((totalTicketsVendor < 1)|| (totalTicketsVendor>maxTicketCapacity)) {
					System.out.println("Total tickets cannot be less than 1 or more than the maximum ticket capacity");
					System.out.println("The maximum tickets are : "+maxTicketCapacity);
					continue;
				}
				ticketConfig.setTotalTicketsByVendor(totalTicketsVendor);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for maximum tickets that could be released by a vendor at a time");
				input.nextLine();
			}
		}

		// Loop for entering Ticket Release frequency by the vendor
		while (true) {
			try {
				System.out.print("Enter Ticket Release frequency time by vendor in seconds: ");
				ticketReleaseRate = input.nextInt();
				if (ticketReleaseRate < 1 ) {
					System.out.println("Enter a valid Ticket Release Rate in seconds");
					continue;
				}
				ticketConfig.setTicketReleaseRate(ticketReleaseRate);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for Ticket Release Rate");
				input.nextLine();
			}
		}

		// Loop for entering Total Tickets that could be bought by a consumer at a time.
		while (true) {
			try {
				System.out.print("Enter Maximum number of Tickets that could be bought by a consumer at a time: ");
				totalTicketsCustomer = input.nextInt();
				if ((totalTicketsCustomer < 1) || (totalTicketsCustomer>maxTicketCapacity)) {
					System.out.println("Maximum tickets cannot be less than 1 or greater than the maximum ticket capacity");
					System.out.println("The maximum tickets are : "+maxTicketCapacity);
					continue;
				}
				ticketConfig.setTotalTicketsByConsumer(totalTicketsCustomer);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for maximum total tickets that could be bought by a consumer at a time");
				input.nextLine();
			}
		}

		// Loop for entering Customer Retrieval Rate
		while (true) {
			try {
				System.out.print("Enter Customer Retrieval frequency time by a customer in seconds: ");
				customerRetrievalRate = input.nextInt();
				if (customerRetrievalRate< 1) {
					System.out.println("Enter a valid Customer Retrieval Frequency in seconds.");
					continue;
				}
				ticketConfig.setCustomerRetreivalRate(customerRetrievalRate);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for Customer Retrieval Frequency in seconds.");
				input.nextLine();
			}
		}

		// Loop for entering number of Vendors
		while(true){
			try{
				System.out.print("Enter number of Vendors: ");
				numOfVendors = input.nextInt();
				if(numOfVendors<1){
					System.out.println("number of vendors cannot be less than 0");
					continue;
				}
				ticketConfig.setNoOfVendors(numOfVendors);
				break;
			}catch (InputMismatchException e){
				System.out.println("Enter valid data type for number of Vendors");
				input.nextLine();
			}
		}

		// Loop for entering number of Consumers
		while(true){
			try{
				System.out.print("Enter number of Consumers: ");
				numOfConsumers = input.nextInt();
				if(numOfConsumers<1){
					System.out.println("number of Consumers cannot be less than 0");
					continue;
				}
				ticketConfig.setNoOfVendors(numOfVendors);
				break;
			}catch (InputMismatchException e){
				System.out.println("Enter valid data type for number of Vendors");
				input.nextLine();
			}
		}
		//save the configuration
		ConfigurationManager.configurationSave(ticketConfig);
	}
	}

