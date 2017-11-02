import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import javafx.scene.text.Text;

public class AfrsGui extends Application implements Observer {

    private final int def_width = 100;
    private final int def_height = 50;
    private final int wind_width = 750;
    private final int wind_height = 750;


    //TimersModel model;
    private Stage stage;
    private AFRSapi c;
    private static ArrayList<String> Files;
    private File f;

    public AfrsGui()
    {
        //this.c = c;
        //c.addObserver(this);

    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        BorderPane bp = start_Scene();

        stage.setTitle("AFRS");
        stage.setScene(new Scene(bp));
        //stage.setScene(new Scene(border));
        stage.show();

    }


    private BorderPane start_Scene(){
        BorderPane b = new BorderPane();
        b.setPrefWidth(wind_width);
        b.setPrefHeight(wind_height);
        ComboBox cb;

        VBox vb = new VBox();
        for (int i = 0 ; i < 5; i ++ ) {
            HBox hb = new HBox();
            hb.setPadding(new Insets(10, 10, 10, 10));
            Text text = new Text();

            text.setTextAlignment(TextAlignment.CENTER);
            text.setFont(Font.font(20));

            hb.getChildren().add(text);

            cb = new ComboBox<String>();
            //cb.setItems(Sums_Options);
            cb.getSelectionModel().selectFirst();
            cb.setPrefHeight(50);
            cb.setPrefWidth(100);
            hb.getChildren().add(cb);

            cb = new ComboBox<String>();
            //cb.setItems(Sums_Options);
            cb.getSelectionModel().selectFirst();
            cb.setPrefHeight(50);
            cb.setPrefWidth(100);
            hb.getChildren().add(cb);

            vb.getChildren().add(hb);
        }

        Button submit = new Button("Submit");


        b.setCenter(vb);
        b.setBottom(submit);

        return b;
    }

    private BorderPane main_Scene(){
        BorderPane border = new BorderPane();
        border.setPrefWidth(wind_width);
        border.setPrefHeight(wind_height);

        VBox vb;
        HBox hb = new HBox();
        hb.setPadding(new Insets(10, 50, 10 , 50));
        hb.setAlignment(Pos.CENTER);

        int j = 0;
        for (int i = 0 ; i < 5; i ++ ) {
            vb = new VBox();
            vb.setPadding(new Insets(10, 10, 10, 10));
            Text text = new Text();

            text.setTextAlignment(TextAlignment.CENTER);
            text.setFont(Font.font(20));
            vb.getChildren().add(text);


            //b.setText("Grab from Champion Model and replace");

            j += 2;

            hb.getChildren().add(vb);
        }
        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sc = new Scene(start_Scene());
                stage.setScene(sc);
                stage.show();
            }
        });
        border.setTop(back);
        border.setCenter(hb);
        return border;
    }

    public void update(Observable t, Object o)
    {
        String output = c.getInput();
        System.out.println(output);

    }



    public static void main(String[] args)
    {
        System.out.println("Welcome to AFRS!");
        System.out.println("We are now in the first release of development...");
        System.out.println("What are you looking to do?");
        System.out.println("If unsure about inputs... Type 'help;'");

        //AFRSapi c = new AFRSapi();
        //Observer o = new AfrsGui(c);

        //c.takeInput();

        Application.launch(args);
    }
}
