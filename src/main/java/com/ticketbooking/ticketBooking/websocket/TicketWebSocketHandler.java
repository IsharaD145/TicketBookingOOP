//https://www.geeksforgeeks.org/spring-boot-web-socket/
package com.ticketbooking.ticketBooking.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket handler for managing real-time communication between the server and clients.
 * Handles the establishment and closure of WebSocket sessions and broadcasts messages to all connected clients.
 */
public class TicketWebSocketHandler extends TextWebSocketHandler {

    /**
     * A thread-safe list of active WebSocket sessions.
     */
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    /**
     * Invoked after a new WebSocket connection is established.
     * Adds the session to the list of active sessions.
     *
     * @param session the WebSocket session that was established.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * Invoked after a WebSocket connection is closed.
     * Removes the session from the list of active sessions.
     *
     * @param session the WebSocket session that was closed.
     * @param status  the close status indicating the reason for closure.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    /**
     * Broadcasts a message to all connected WebSocket clients.
     *
     * @param message the message to be broadcasted.
     */
    public static void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
