package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import javax.swing.border.Border;

public class AfrsGui extends Application implements Observer {

    private final int def_width = 100;
    private final int def_height = 50;
    private final int wind_width = 750;
    private final int wind_height = 750;

    private boolean isLocalService;
    private Stage stage;
    private AFRSapi c;
    private static ArrayList<String> Files;
    private File f;
    private String response;

    public AfrsGui()
    {
        Parser p = new Parser();
        AFRSapi c = new AFRSapi(p);
        this.c = c;
        c.addObserver(this);
        isLocalService = true;

    }

    @Override
    public void start(Stage stage) {

        this.stage = stage;
        //new AfrsGui();

        BorderPane bp = welcomeScene();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we)
            {
                System.out.println("Stage is closing");
            }
        });

        stage.setTitle("AFRS : " + c.ID);
        stage.setScene(new Scene(bp));
        //stage.setScene(new Scene(border));
        stage.show();
    }

    private BorderPane welcomeScene()
    {
        BorderPane border = new BorderPane();
        border.backgroundProperty().set(new Background(new BackgroundFill(Color.WHITESMOKE,CornerRadii.EMPTY,Insets.EMPTY)));

        border.setPrefWidth(wind_width);
        border.setPrefHeight(wind_height);

        HBox welcome = new HBox();
        //welcome.backgroundProperty().set(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        welcome.setPadding(new Insets(10,10,10,10));
        welcome.setAlignment(Pos.CENTER);
        Text text = new Text("Welcome to AFRS!");
        text.setFont(Font.font(50));
        text.setTextAlignment(TextAlignment.CENTER);
        text.underlineProperty().setValue(true);
        welcome.getChildren().add(text);

        Text authors = new Text("By,\n Joshua Eng, Melissa Gould,\n Liam Miller, Tyler Davis");
        authors.setFont(Font.font(25));
        authors.setTextAlignment(TextAlignment.CENTER);

        HBox but = new HBox();
        Button cont = new Button();
        cont.setText("Click here to Begin");
        cont.setFont(Font.font(14));
        cont.setPadding(new Insets(10,20,10,20));
        cont.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(init_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });
        but.getChildren().add(cont);
        but.setAlignment(Pos.CENTER);
        but.setPadding(new Insets(0,0,200,0));

        border.setTop(welcome);
        border.setCenter(authors);
        border.setBottom(but);

        return border;
    }

    private BorderPane init_Scene(){
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);




        VBox options = new VBox();
        options.setAlignment(Pos.CENTER);

        HBox testText = new HBox();
        Text test = new Text("Testing where this goes");
        test.setFont(Font.font(24));
        testText.setAlignment(Pos.CENTER);
        testText.getChildren().add(test);

        Button getInfo = new Button("Get Info");
        getInfo.setPadding(new Insets(10,20,10,20));

        getInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(getInfo_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });


        options.getChildren().addAll(getInfo,testText);


        Button addClient = new Button("Add a new Client");
        addClient.setPadding(new Insets(10,20,10,20));

        addClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage newClient = new Stage();
                c = new AfrsGui().c;
                start(newClient);
            }
        });

        VBox Settings = new VBox();
        HBox newClient = new HBox();
        HBox changeServ = new HBox();
        changeServ.setAlignment(Pos.TOP_RIGHT);
        Button toggleService = new Button("Use Test Service");
        toggleService.setPadding(new Insets(10,20,10,20));


        toggleService.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isLocalService = !isLocalService; //Toggle Service
                changeService();
                if(isLocalService)
                {
                    toggleService.setText("Use Web Service");
                }
                else
                {
                    toggleService.setText("Use Local Service");
                }
            }
        });

        changeServ.getChildren().add(toggleService);
        Settings.getChildren().addAll(newClient,changeServ);

        newClient.setAlignment(Pos.TOP_RIGHT);
        newClient.getChildren().add(addClient);

        b.setCenter(options);
        b.setTop(Settings);

        return b;
    }

    private BorderPane getInfo_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        VBox buttons = new VBox();
        //buttons.setPadding(new Insets(20,20,20,20));
        buttons.setSpacing(10);

        Text Search = new Text("What do you wish to find?");
        Search.setFont(Font.font(24));
        Search.underlineProperty().set(true);

        Button findFlight = new Button("Flights");
        findFlight.setPadding(new Insets(10,20,10,20));

        findFlight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(getFlight_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });

        Button findAirport = new Button("Airports");
        findAirport.setPadding(new Insets(10,20,10,20));

        findAirport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(getAirport_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });

        Button findReservation = new Button("Reservations");
        findReservation.setPadding(new Insets(10,20,10,20));

        findReservation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(getReservation_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });


        buttons.getChildren().addAll(Search,findAirport,findFlight,findReservation);
        buttons.setAlignment(Pos.CENTER);

        b.setCenter(buttons);


        return b;
    }

    private BorderPane getReservation_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);


        return b;
    }
    private BorderPane getFlight_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);


        return b;
    }
    private BorderPane getAirport_Scene()
    {
        String air = "airport,";
        c.updateString(air);
        String terminate = ";";

        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        TextField airports = new TextField("Airport Code");
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //System.out.println(airports.getCharacters());
                String input = airports.getCharacters().toString();
                //System.out.println("Input is: " + input);

                c.updateString(input + terminate); //Send the airport request

                Scene sc = new Scene(displayAirport_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });

        HBox airportCont = new HBox(airports,submit);
        b.setCenter(airportCont);

        return b;
    }



    private BorderPane displayAirport_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        Text info = new Text("Airport Results:");
        info.underlineProperty().set(true);
        info.setFont(Font.font(24));

        Text resp = new Text();
        resp.setText(response);
        resp.setFont(Font.font(16));

        VBox vb = new VBox();
        vb.getChildren().addAll(info,resp);
        vb.setAlignment(Pos.CENTER);

        HBox hb = new HBox();
        Button restart = new Button("New Request");
        restart.setPadding(new Insets(20,20,20,20));

        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(init_Scene());
                stage.setScene(sc);
                stage.show();

            }
        });

        hb.getChildren().addAll(restart);
        hb.setAlignment(Pos.TOP_RIGHT);

        b.setCenter(vb);
        b.setRight(hb);
        return b;
    }

    private void changeService()
    {
        //Need to add funcitonality for switching search models
        if (isLocalService)
        {
            System.out.println("Now using Local Service");
        }
        else
        {
            System.out.println("Now using Web Service");
        }
    }


    public void update(Observable t, Object o)
    {
        String output = c.getInput();
        System.out.println(output);
        this.response = output;


    }

    @Override
    public void stop() //Methods for after system shutdown should go here
    {
        //Only after the very last window to closes
        c.quit();

        System.out.println("I made it to closing");
    }

    public static void main(String[] args)
    {

        Parser p = new Parser();
        AFRSapi c = new AFRSapi(p);
        //Observer o = new AfrsGui(c);
        c.getFiles();
        c.loadFiles();
        new AfrsGui();

        Application.launch(args);
    }
}