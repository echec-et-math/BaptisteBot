package app.src.main.java.network;

import app.src.main.java.model.Game;

import java.io.*;
import java.net.*;

public class Lobby {
    
    // Stores sockets and RW streams
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private Game game;

    public Lobby(Socket socket) throws IOException {
        game = new Game();
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        
    }

    private void terminate() throws IOException {
        reader.close();
        socket.getInputStream().close();
        writer.flush();
        writer.close();
        socket.close();
    }

    public void start() throws IOException {
        String greetings = reader.readLine();
        if (!greetings.equals(Requests.CONST_GREETINGS)) {
            writer.println(Requests.CONST_ERROR);
            writer.println(Requests.ERRCODE_BAD_FORMAT);
            terminate();
            return;
        }
        writer.println(Requests.CONST_STARTGAME);
        game.start();
        while (!game.isOver()) {
            String guess = reader.readLine();
            for (String letterinfo : game.getReply(guess)) {
                writer.println(letterinfo);
            }
            writer.flush();
        }
        writer.println(game.getEndState());
        writer.flush();
        writer.println(Requests.CONST_ENDGAME);
        writer.flush();
        terminate();
    }
}
