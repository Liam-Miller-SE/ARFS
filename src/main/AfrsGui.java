package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
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
import java.util.Random;

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
    private static AFRSapi c;
    private String response;
    private String id;
    private String terminate = ";";
    private ArrayList<Itinerary> itins;
    private static ArrayList<Integer> ids = new ArrayList<>();
    private Text outputM = new Text();

    public AfrsGui()
    {


        c.addObserver(this);
        isLocalService = true;

        Random r = new Random();
        while (true) {
            int tempID = r.nextInt(100);
            if (!ids.contains(tempID)) {
                ids.add(tempID);
                this.id = Integer.toString(tempID);
                break;
            }
        }

        System.out.println(ids);

    }

    private Stage whichClient(Stage s)
    {
        //System.out.println(s.getTitle());
        if (ids.size() > 1) {
            //System.out.println(ids.get(0).equals(ids.get(1)));
        }
        return s;
    }

    @Override
    public void start(Stage stage) {

        this.stage = stage;
        this.sendServ();
        //new AfrsGui();

        BorderPane bp = welcomeScene();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we)
            {
                System.out.println("Stage is closing");
            }
        });

        stage.setTitle("AFRS : " + this.id);
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

        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        Button schedule = new Button("Schedule");
        schedule.setPadding(new Insets(10,20,10,20));

        schedule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                Scene sc = new Scene(genFlights_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });
        hb.getChildren().add(schedule);


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

        options.getChildren().addAll(getInfo,hb);

        Button addClient = new Button("Add a new Client");
        addClient.setPadding(new Insets(10,20,10,20));

        addClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage newClient = new Stage();
                AfrsGui g = new AfrsGui();
                g.stage = newClient;
                g.start(newClient);
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

        HBox hold = new HBox();
        Button ezWay = new Button("Terminal Client");
        ezWay.setPrefWidth(200);
        ezWay.setPrefHeight(100);
        ezWay.setFont(Font.font(20));
        ezWay.underlineProperty().set(true);
        ezWay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene sc = new Scene(terminal_Scene());
                stage.setScene(sc);
                stage.show();

                //whichClient(stage);
            }
        });
        hold.getChildren().add(ezWay);
        hold.setPadding(new Insets(30,30,30,30));
        hold.setAlignment(Pos.CENTER);
        options.getChildren().add(hold);

        changeServ.getChildren().add(toggleService);
        Settings.getChildren().addAll(newClient,changeServ);

        newClient.setAlignment(Pos.TOP_RIGHT);
        newClient.getChildren().add(addClient);

        b.setCenter(options);
        b.setTop(Settings);

        return b;
    }




    private BorderPane terminal_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        TextField input = new TextField();
        input.setPadding(new Insets(20,20,20,20));
        input.setFont(Font.font(20));

        ScrollPane screen = new ScrollPane();
        screen.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        screen.setPadding(new Insets(10,10,10,10));
        //outputM = new Text();

        outputM.setTextAlignment(TextAlignment.JUSTIFY);
        outputM.setFont(Font.font(24));
        outputM.setWrappingWidth(700);
        screen.setContent(outputM);
        screen.setPrefHeight(600);

        VBox window = new VBox();

        HBox inpField = new HBox();
        input.setPrefWidth(600);
        inpField.setPadding(new Insets(10,10,10,10));
        inpField.setAlignment(Pos.CENTER);
        Button submit = new Button("Submit");
        submit.setPrefHeight(80);
        submit.setPrefWidth(120);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String inp = input.getCharacters().toString();
                whichClient(stage);
                if (inp.equals("connect;"))
                {
                    Stage newClient = new Stage();
                    AfrsGui g = new AfrsGui();
                    g.stage = newClient;
                    g.start(newClient);
                }
                else if(inp.equals("disconnect;"))
                {
                    Scene sc = new Scene(discon_Scene());
                    stage.setScene(sc);
                    stage.show();
                }
                else {
                    //outputM.setText(inp);
                    sendString(inp);
                }
                input.setText("");





            }
        });
        inpField.getChildren().addAll(input,submit);


        window.getChildren().addAll(screen);

        b.setCenter(window);
        b.setBottom(inpField);
        return b;
    }

    private BorderPane discon_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        stop();

        Text disc = new Text( this.id +",disconnect");
        disc.setFont(Font.font(24));

        b.setCenter(disc);

        return b;
    }









    private BorderPane genFlights_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        VBox vb = new VBox();
        HBox hb = new HBox();


        TextField orig = new TextField("Origin");
        orig.setPrefColumnCount(10);
        orig.setPrefWidth(140);
        orig.setPrefHeight(50);
        orig.setFont(Font.font(20));

        TextField dest = new TextField("Destination");
        dest.setPrefColumnCount(10);
        dest.setPrefWidth(140);
        dest.setPrefHeight(50);
        dest.setFont(Font.font(20));

        hb.getChildren().addAll(orig,dest);
        hb.setAlignment(Pos.CENTER);
        vb.getChildren().add(hb);

        HBox but  = new HBox();
        Button submit = new Button("Submit");

        but.getChildren().add(submit);
        but.setAlignment(Pos.CENTER);

        vb.setAlignment(Pos.CENTER);

        ObservableList<Integer> numHops = FXCollections.observableArrayList(2,1,0);
        ComboBox<Integer> hops = new ComboBox<>();
        hops.setItems(numHops);
        hops.getSelectionModel().selectFirst();
        hops.setPrefHeight(50);
        hops.setPrefWidth(100);

        ObservableList<String> sortOrder = FXCollections.observableArrayList("departure", "arrival", "airfare");

        ComboBox<String> sort = new ComboBox<>();
        sort.setItems(sortOrder);
        sort.getSelectionModel().selectFirst();
        sort.setPrefHeight(50);
        sort.setPrefWidth(100);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String inf = "info";
                String or = orig.getCharacters().toString();
                String de = dest.getCharacters().toString();
                String con = hops.getSelectionModel().getSelectedItem().toString();
                String sor = sort.getSelectionModel().getSelectedItem();

                c.updateString(inf + "," + or + "," + de + "," + con + "," + sor + terminate);

                Scene sc = new Scene(getFlight_Scene());
                stage.setScene(sc);
                stage.show();

            }
        });

        vb.getChildren().addAll(hops,sort, but);
        b.setCenter(vb);

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

                Scene sc = new Scene(genFlights_Scene());
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

        Text reserv = new Text();
        reserv.underlineProperty().set(true);
        reserv.setFont(Font.font(24));
        reserv.setText("Finding a Reservation");

        HBox hb = new HBox();
        TextField name = new TextField("Name");
        name.setPrefColumnCount(20);
        name.setPrefWidth(280);
        name.setPrefHeight(50);
        name.setFont(Font.font(20));

        hb.getChildren().add(name);
        hb.setAlignment(Pos.CENTER);

        TextField orig = new TextField("Origin");
        orig.setPrefColumnCount(10);
        orig.setPrefWidth(140);
        orig.setPrefHeight(50);
        orig.setFont(Font.font(20));

        TextField dest = new TextField("Destination");
        dest.setPrefColumnCount(10);
        dest.setPrefWidth(140);
        dest.setPrefHeight(50);
        dest.setFont(Font.font(20));

        Button submit = new Button("Submit");

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String nameInp = name.getCharacters().toString();
                String origInp = orig.getCharacters().toString();
                String destInp = dest.getCharacters().toString();
                c.updateString("retrieve" + "," + nameInp + "," + origInp + "," + destInp + terminate); //Send the Retrieve request

                Scene sc = new Scene(displayReservation_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });

        VBox vb = new VBox();
        //vb.setPadding(new Insets(20,20,20,20));
        HBox ports = new HBox();
        ports.getChildren().addAll(orig, dest);
        ports.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(reserv,hb, ports,submit);
        vb.setAlignment(Pos.CENTER);
        b.setCenter(vb);

        return b;
    }

    private BorderPane displayReservation_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        Text info = new Text("Reservation Results:");
        info.underlineProperty().set(true);
        info.setFont(Font.font(24));


        Text results = new Text();
        results.setText(response);
        results.setFont(Font.font(16));


        VBox vb = new VBox();
        vb.getChildren().addAll(info,results);
        vb.setAlignment(Pos.CENTER);

        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_RIGHT);
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
        hb.getChildren().add(restart);

        b.setCenter(vb);
        b.setTop(hb);

        return b;
    }

    private BorderPane getFlight_Scene()
    {
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);


        Text info = new Text();
        info.setText(response);

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
        b.setCenter(info);
        b.setTop(restart);

        return b;
    }
    private BorderPane getAirport_Scene()
    {
        String air = "airport,";
        c.updateString(air);

        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);

        TextField airports = new TextField("Airport Code");
        airports.setPrefColumnCount(10);
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //System.out.println(airports.getCharacters());
                String input = airports.getCharacters().toString();
                //System.out.println("Input is: " + input);

                c.updateString(  input + terminate); //Send the airport request

                Scene sc = new Scene(displayAirport_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });

        HBox airportCont = new HBox(airports,submit);
        airportCont.setAlignment(Pos.CENTER);
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
        //System.out.println(output);
        this.response = output;


        itins = c.getItineraries();
        if(output.equals("exit"))
        {
            c.quit();
            System.out.println("Goodbye!");

        }
        else if(output.equals("help"))
        {
            System.out.println("Options are: info, reserve, retrieve, delete, airport, help, exit");
            System.out.println("Please note that all responses should end with a ';'");
            System.out.println("For more info about a specific option, Type 'help,[option];' ");
        }
        else
        {
            outputM.setText(response);
        }


    }

    private void sendItins()
    {
        c.updateItin(this.itins);
    }
    private void sendServ()
    {
        c.updateServer(this.isLocalService);
    }

    private void sendString(String myInput)
    {

        String lastChar = myInput.substring(myInput.length() -1);
        String command = myInput.substring(0,myInput.length()-1);
        String isHelp = "";
        String isReserve = "";
        String isServ = "";
        if (myInput.length() >= 7) {
            isReserve = myInput.substring(0, 7);
        }
        //System.out.println(isReserve);
        if(myInput.length() >= 5) {
            isHelp = myInput.substring(0, 4);
        }
        if(myInput.length() >= 6) {
            isServ = myInput.substring(0, 7);
        }

        //System.out.println(isHelp);


        if (isServ.equals("server"))
        {
            sendServ();
            c.updateString(id + "," + myInput);
        }
        else if (command.equals("exit"))
        {
            System.out.println("Goodbye!");
            c.quit();
            return;
        }

        else if (isReserve.equals("reserve"))
        {
            sendItins();
            c.updateString(id + "," + myInput);
        }

        else
        {
            c.updateString(id + "," + myInput);

        }
        //sendString();



    }

    private void help(String str)
    {
        //System.out.println(list[1]);
        if (str.equals("info"))
        {
            System.out.println("info,origin,destination[,connections[,sort-order]];");
            System.out.println("origin/destination are 3-letter airport codes, All CAPS");
            System.out.println("connections is a number from 0-2");
            System.out.println("sort-order options: departure, arrival, airfare");
        }
        else if (str.equals("reserve"))
        {
            System.out.println("reserve,id,passenger;");
            System.out.println("id is the number identifier ");
        }
        else if (str.equals("retrieve"))
        {
            System.out.println("retrieve,passenger[,origin[,destination]];");
        }
        else if (str.equals("delete"))
        {
            System.out.println("delete,passenger,origin,destination;");
        }
        else if (str.equals("airport"))
        {
            System.out.println("airport,airport;");
        }
        else if (str.equals("help"))
        {
            System.out.println("Only usage is 'help;'");
        }
        else if (str.equals("exit"))
        {
            System.out.println("Only usage is 'exit;'");
        }
        else
        {
            System.out.println("Unknown help option, please try again.");
        }

    }




    @Override
    public void stop() //Methods for after system shutdown should go here
    {
        for(Integer s : ids)
        {
            if (Integer.parseInt(id) == s   )
            {
                ids.remove(s);
                break;
            }
        }

        //ids.remove(Integer.parseInt(this.id));
        //System.out.println(ids);

        c.quit();

        //System.out.println("I made it to closing");
    }

    public static void main(String[] args)
    {

        Parser p = new Parser();
        c = new AFRSapi(p);
        //Observer o = new AfrsGui(c);
        c.getFiles();
        c.loadFiles();
        //AfrsGui G = new AfrsGui();



        Application.launch(args);
    }
}
