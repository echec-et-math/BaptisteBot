package app.src.main.java;

import java.io.FileWriter;
import java.io.IOException;

public class DebugManager {

    FileWriter f;

    private static int nbLogs = 0;

    // constructeur
    public DebugManager(String filename) {
        try {
            f = new FileWriter("logs/" + Integer.toString(nbLogs++) + "_" + filename);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // action d'écriture
    public void log(String log) {
        try {
            f.write(log + '\n');
            f.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fermeture du fichier
    public void close() {
        try {
            f.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}