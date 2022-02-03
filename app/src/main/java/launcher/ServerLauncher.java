package app.src.main.java.launcher;

import app.src.main.java.network.Server;

public class ServerLauncher {

    private static final int defaultPort = 6666;
    public static void main(String[] args) throws Exception {
        int port;
        //System.out.println("-- Running Server at " + InetAddress.getHostAddress() + "--");
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception e) {
            port = defaultPort;
        }
        new Server(port);
    }
}
