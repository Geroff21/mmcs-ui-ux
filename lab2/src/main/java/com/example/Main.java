package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.util.*;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        stage.setResizable(false);
        Scene scene = new Scene(root, 350.0, 350.0);
        // ----- Add Text Field ----- //
        ArrayList<TextField> textFields = new ArrayList<TextField>(6);
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>(6);
        VBox vboxTF = new VBox();
        VBox vboxCheck = new VBox(10);
        for (int i = 0; i < 6; i++) {
            var strLabel = String.valueOf(i+1);
            var textF = new TextField(strLabel);
            textFields.add(textF);
            vboxTF.getChildren().add(new HBox(new Label(strLabel),textF));
            vboxCheck.getChildren().add(new HBox(new Label(strLabel),new CheckBox(strLabel)));
        }
 
        var scroll = new ScrollPane(vboxTF);

        HBox hBoxTF = new HBox(10);
        hBoxTF.setPrefWidth(400);
        hBoxTF.setMaxWidth(400);
        hBoxTF.getChildren().addAll(scroll);
        hBoxTF.setAlignment(Pos.TOP_CENTER);

        // Config Buttons //
        Button button1 = new Button("Кнопка 1");
        Button button2 = new Button("Кнопка 2");
        Button button3 = new Button("Кнопка 3");

        HBox buttons = new HBox(5);
        buttons.setPadding(new Insets(5));
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.getChildren().addAll(button1, button2, button3);

        Label messageTextField = new Label();
        messageTextField.setPrefWidth(150);
        messageTextField.setMaxWidth(150);
        messageTextField.setPadding(new Insets(5));

        button1.setOnAction(event-> {
            Stage wind = new Stage();
            TextField tf = new TextField();

            VBox root1 = new VBox(15.0);
            Scene scene1 = new Scene(root1,150.0, 80.0);
            root1.getChildren().addAll(tf);

            tf.addEventHandler(ActionEvent.ACTION, ev -> {
                tf.getText();
                try{
                    var coef = Double.parseDouble(tf.getText());
                    if (coef>0)
                    {
                        if (!stage.isShowing()) stage.show();
                        stage.setWidth(stage.getWidth() * coef );
                        stage.setHeight(stage.getHeight() * coef );

                    }
                    else {
                        messageTextField.setText("Вы ввели число, меньшее нуля");
                    }
                }
                catch (NumberFormatException e){
                    messageTextField.setText("Вы ввели не число");
                }

            });

            wind.setTitle("window1");
            wind.setScene(scene1);
            wind.show();
        });
        button2.setOnAction(event-> {
            Stage wind2 = new Stage();
            vboxCheck.setAlignment(Pos.TOP_CENTER);
            var scrollCheck = new ScrollPane(vboxCheck);
            Scene scene2 = new Scene(scrollCheck,150.0, 160.0);
            wind2.setTitle("window2");
            wind2.setScene(scene2);
            wind2.show();
        });
        button3.setOnAction(event-> {
            Stage window3 = new Stage();
            VBox root3 = new VBox();
            root3.setAlignment(Pos.TOP_CENTER);
            TextField textFieldNum = new TextField("1");
            Button addButton = new Button("Добавить");
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    var l = String.valueOf(textFields.size()+1);
                    Label add_label = new Label(l);
                    TextField add_tf = new TextField(l);
                    CheckBox add_check = new CheckBox(l);
                    textFields.add(add_tf);
                    checkBoxes.add(add_check);
                    vboxCheck.getChildren().add(new HBox(new Label(l),add_check));
                    vboxTF.getChildren().add(new HBox(new Label(l),add_tf));
                }
            });
            Button delButton = new Button("Удалить");
            delButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (textFields.size() > 0) {
                        var id = Integer.parseInt(textFieldNum.getText())-1;
                        textFields.remove(id);
                        vboxTF.getChildren().remove(id);
                    }
                    }
            });
            root3.getChildren().addAll(textFieldNum,addButton, delButton);
            root3.setSpacing(5);
            Scene scene3 = new Scene(root3,180.0, 120.0);
            window3.setTitle("window3");
            window3.setScene(scene3);
            window3.show();
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttons, messageTextField);
        vBox.setAlignment(Pos.TOP_CENTER);
        // ----- Add Elements on Stage ----- //
        root.getChildren().addAll(hBoxTF);
        root.getChildren().addAll(vBox);
        // -----   Config Stage  ----- //
        stage.setTitle("Лабораторная 2");
        stage.setScene(scene);
        stage.show();
    }
}