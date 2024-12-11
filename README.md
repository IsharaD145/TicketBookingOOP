# Ticket Booking Simulation

This project simulates a ticket booking system where vendors release tickets, and consumers purchase them. It is built using Spring Boot for the backend and provides RESTful APIs for controlling the simulation.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java JDK 11 or later**: This project requires Java 11 or newer.
- **Maven**: Maven is used for dependency management and building the project.
- **IDE**: IntelliJ IDEA, Eclipse, or any IDE that supports Java and Maven.
- **Git**: To clone the repository from GitHub.

## Steps to Clone, Set Up, and Run the Application

### 1. **Clone the Project from GitHub**

   To clone the project, follow these steps:

   1. Open your terminal (or Git Bash).
   2. Navigate to the directory where you want to save the project. For example:

      ```bash
      cd ~/Documents
      ```

   3. Run the following command to clone the repository:

      ```bash
      git clone https://github.com/IsharaD145/TicketBookingOOP.git
      ```

   4. Navigate into the project folder:

      ```bash
      cd TicketBookingOOP
      ```

### 2. **Open the Project in Your IDE**

   - **For IntelliJ IDEA**:
     1. Open IntelliJ IDEA.
     2. Go to **File** > **Open**.
     3. Navigate to the project directory you just cloned and select the root folder of the project, then click **OK**.


### 3. **Install Project Dependencies**

   If you haven’t already set up Maven in your IDE, follow these steps to ensure all dependencies are installed:

   - **For IntelliJ IDEA**: 
     1. Go to **View** > **Tool Windows** > **Maven**.
     2. Click on **Refresh** (the circular arrow icon) to download all required dependencies.


### 4. **Run the Application**

   After setting up the project and ensuring all dependencies are installed, you can run the application.

   1. In your IDE, locate the main class file (typically named `TicketBookingApplication.java`).
   2. Right-click on the file and select **Run** or use the **Run** button in your IDE.

   The application will start and by default, it will run on port **8080**.

   You should see the following log output in your terminal or console:


### 5. **Access the Application**

After running the application, open a web browser and go to the following URL to access the backend API:


This is the base URL for the backend. You can interact with the RESTful APIs at various endpoints like `/start`, `/stop`, `/save`, etc.

### 6. **Testing the Endpoints**

You can use tools like **Postman** or **curl** to test the RESTful APIs:

- **POST /save**: Save the ticket configuration (requires JSON payload).
- **POST /stop**: Stop the simulation.
- **GET /start**: Start the simulation.
- **GET /increaseVendor**: Add a new vendor to the simulation.
- **GET /decreaseVendor**: Remove a vendor from the simulation.
- **GET /addconsumer**: Add a new consumer to the simulation.
- **GET /decreaseConsumer**: Remove a consumer from the simulation.
- **GET /gettotaltickets**: Get the total ticket capacity.

Example of sending a POST request to `/save`:

- Open Postman and set the request type to **POST**.
- Set the URL to `http://localhost:8080/save`.
- In the **Body** tab, select **raw** and **JSON**, then enter the configuration data in JSON format. For example:

  ```json
  {
      "totalTicketsByVendor": 100,
      "totalTicketsByConsumer": 50,
      "ticketReleaseRate": 10,
      "customerRetreivalRate": 5,
      "noOfVendors": 2,
      "noOfConsumers": 3,
      "maxTicketCapacity": 500
  }
  ```

- Click **Send** to execute the request.

### 7. **Stopping the Application**

To stop the application:

- **In IntelliJ IDEA**: Click the **Stop** button in the console tab.

## Troubleshooting

- **Port 8080 is already in use**: If you encounter an error stating that port 8080 is already in use, you can either stop the process using port 8080 or change the port in the `application.properties` file. To change the port:

1. Go to `src/main/resources/application.properties`.
2. Add or modify the line:

  ```properties
  server.port=8081
  ```

- **Dependencies not loading**: If Maven dependencies are not loading correctly, try running the following Maven command in your terminal inside the project directory:

```bash
mvn clean install

This README file provides the full set of instructions to clone the repository, set up the project, and run it locally on port 8080.
