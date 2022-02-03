/* package app.src.main.java.launcher;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList; 

import javafx.stage.WindowEvent;

import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

import javafx.event.ActionEvent; 
import javafx.event.EventHandler;

import java.lang.Exception;
import java.io.IOException;

public class ClientLauncher extends Application {
    // Classe du lanceur graphique pour le client

    Stage primaryStage;
    Client c;
    private final int DEFAULT_PORT = 6666; // constante du port par défaut
    
    public ClientLauncher() {}

    public static void displayErrorWindow(String error) {
        Stage windowStage = new Stage();
        Group node = new Group();
        ObservableList list = node.getChildren();
        Scene windowScene = new Scene(node, 800, 200);
        windowStage.setScene(windowScene);
        windowStage.setTitle("Erreur !");
        windowStage.show();

        Label texte = new Label(error);
        texte.setFont(new Font(20));
        texte.setTranslateX(20);
        texte.setTranslateY(20);
        list.add(texte);

        //bouton oui
        Button boutonOK = new Button("OK");
        boutonOK.setTranslateX(80);
        boutonOK.setTranslateY(150);
        list.add(boutonOK);

        boutonOK.setOnAction(event -> {
            windowStage.close();
        });
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;

        Group node = new Group();
        ObservableList list = node.getChildren();
        Scene sceneMenu = new Scene(node, 600, 600);
        sceneMenu.setFill(Color.LIGHTGRAY);
        primaryStage.setScene(sceneMenu);
        primaryStage.setTitle("Menu");
        primaryStage.show();


        //texte sur la fenêtre : le titre du jeu
        Label texte = new Label("Vintage Monopoly");
        texte.setFont(new Font(40));
        texte.setStyle("-fx-text-fill: red");
        texte.setTranslateX(120);
        texte.setTranslateY(20);
        list.add(texte);


        //rentrer son pseudo
        Label pseudo = new Label("Pseudo :");
        pseudo.setFont(new Font(30));
        pseudo.setTranslateX(200);
        pseudo.setTranslateY(120);
        list.add(pseudo);
        TextField demandePseudo = new TextField();
        demandePseudo.setTranslateX(200);
        demandePseudo.setTranslateY(160);
        list.add(demandePseudo);


        //rentrer son adresse
        Label adresse = new Label("Adresse IP :");
        adresse.setFont(new Font(30));
        adresse.setTranslateX(200);
        adresse.setTranslateY(220);
        list.add(adresse);
        TextField demandeAdresse = new TextField();
        demandeAdresse.setTranslateX(200);
        demandeAdresse.setTranslateY(260);
        list.add(demandeAdresse);


        //rentrer le port
        Label port = new Label("Port :");
        port.setFont(new Font(30));
        port.setTranslateX(200);
        port.setTranslateY(320);
        list.add(port);
        TextField demandePort = new TextField();
        demandePort.setTranslateX(200);
        demandePort.setTranslateY(360);
        list.add(demandePort);


        //checkbox Joueur
        CheckBox boxJoueur = new CheckBox("Joueur");
        boxJoueur.setTranslateX(220);
        boxJoueur.setTranslateY(420);
        boxJoueur.setStyle("-fx-font-size: 25");
        list.add(boxJoueur);

        //checkbox IA
        CheckBox boxIA = new CheckBox("IA");
        boxIA.setTranslateX(220);
        boxIA.setTranslateY(460);
        boxIA.setStyle("-fx-font-size: 25");
        list.add(boxIA);


        //bouton GO qui ferme cette fenêtre
        Button boutonGO = new Button("GO !");
        boutonGO.setTranslateX(250);
        boutonGO.setTranslateY(540);
        boutonGO.setStyle("-fx-font: 20 arial");
        list.add(boutonGO);

        // NB : plutôt qu'une checkbox pour Joueur / IA, il faudrait un choix multiple (joueur par défaut)
        // Rajouter une checkbox pour le debug, rajouter des paramètres de customisation (ex : popups "c'est à vous de jouer") serait bien aussi


        //récupère les infos et affiche le plateau
        boutonGO.setOnAction(event -> {
            try 
            {
                //récuperer les textes entrés par le joueur 
                String playerName = demandePseudo.getText(); 
                String destAddr = demandeAdresse.getText();
                int numport;

                //si le joueur ne rentre pas de port, le port par défaut est 6666
                if (demandePort.getText() == null || demandePort.getText().equals("") || demandePort.getText().equals("default")) {
                    numport = DEFAULT_PORT;
                }
                //sinon on récupère le port qu'il a rentré
                else {
                    numport = Integer.parseInt(demandePort.getText()); 
                }

                c = new Client(destAddr, playerName, numport);

                //on récupère la valeur de la checkbox IA
                boolean choixIA = boxIA.isSelected(); 

                //si le joueur a coché IA, on lance une IA
                if (choixIA) {
                    c.setIsIA(true); 
                }
                if (c.isIA())  
                {
                    IA ia = new IA(); 
                    c.setIA(ia); 
                    ia.setClient(c); 
                    ia.start(); 
                }

                //sinon, on lance la Vue pour que le joueur puisse jouer lui-même
                else 
                {
                    Vue view = new Vue();
                    c.setView(view);
                    view.setClient(c);
                    try {
                        view.start(primaryStage);
                    }
                    catch (Exception except) {
                        System.out.println("Unable to start view : ");
                        except.printStackTrace();
                        displayErrorWindow("Erreur : impossible de démarrer l'interface graphique.");
                    }
                }  

            } catch (IOException ioe) {
                // parsing incorrect ou serveur offline
                displayErrorWindow("Erreur : vérifiez les arguments et l'état du serveur.");
            }
            catch (IllegalArgumentException iae) {
                // port < 0 ou > 65535
                displayErrorWindow("Erreur : Le numéro de port doit être compris entre 0 et 66535 inclus.");
            }
            catch (NullPointerException npe) {
                // champs vides
                displayErrorWindow("Erreur : Au moins un champ obligatoire est vide.");
            }
            catch (Exception e) {
                System.out.println("Une erreur inattendue s'est produite.");
            } 
        });
    }
} */