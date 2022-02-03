package app.src.main.java.network;

import java.util.*;

import app.src.main.java.DebugManager;

// import java.io.*;
import java.net.*;

import java.net.ServerSocket;

public class Server {

    private ServerSocket serverSocket;
    private int serverPort;

    private ArrayList<Lobby> lobbies;

    // Log d'erreur externe, sur un fichier texte.
    private DebugManager debugger = new DebugManager("Server_log.txt");
 
    /**
     * constructeur : attends que les clients se connectent, lance la partie, gère les tours de jeu
     * @param port
     * @param maxPlayers
     * @throws Exception
     */
    public Server(int port) throws Exception {

        boolean endMainLoop = false;
        serverPort = port;
        serverSocket = new ServerSocket(serverPort);

        debugger.log("Creating new game...");
        debugger.log("Awaiting for players...");
        debugger.log("Starting main wait loop");

        //les clients se connectent
        while (!endMainLoop) {
            try {
                Socket socket = serverSocket.accept();
                Lobby lobby = new Lobby(socket);
                lobbies.add(lobby);
                debugger.log("[Server] Starting a new game.");
                Runnable asyncLobby = new Runnable() {

                    @Override
                    public void run() {
                        try {
                            lobby.start();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    
                };
                (new Thread(asyncLobby)).start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        serverSocket.close();       
    }
}