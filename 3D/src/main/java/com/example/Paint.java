package com.example;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Paint extends Application {
   
   public static void main(String[] args) {
      Application.launch(args);
   }

   @Override
   public void start(Stage stage) throws Exception {
      VBox root = new VBox(15.0);
      root.setAlignment(Pos.CENTER);
      Button button1 = new Button("Открыть окно");

      root.getChildren().addAll(button1);
      Scene scene = new Scene(root, 400.0, 300.0);
      stage.setTitle("Класс LinearGradient");
      stage.setScene(scene);
      button1.setOnAction(event -> {
         newWindow();
      });
      Stop[] stops = new Stop[] {
            new Stop(0.0, Color.BLACK), new Stop(1.0, Color.WHITE)
      };
      LinearGradient lg = new LinearGradient(0.0, 0.0, 1.0, 0.0, true, 
                                             CycleMethod.NO_CYCLE, stops);
      System.out.println(lg.getStartX() + " " + lg.getStartY());
      System.out.println(lg.getEndX() + " " + lg.getEndY());
      System.out.println(lg.isProportional());
      System.out.println(lg.getCycleMethod());
      lg.getStops().stream().forEachOrdered(s -> {
         System.out.println(s.getOffset() + " " + s.getColor());
      });
      System.out.println(lg.toString());
      
      stage.show();

   }
   
   public void newWindow() {
      Stage window = new Stage();
      ScrollPane root = new ScrollPane();
      VBox pane = new VBox(15.0);
      pane.setAlignment(Pos.CENTER);
      pane.setPadding(new Insets(15.0));
      Stop[] stops = new Stop[] {
            new Stop(0.0, Color.BLACK), new Stop(1.0, Color.WHITE)
      };
      Rectangle rect = new Rectangle(400.0, 50.0);
      rect.setStroke(Color.BLACK);
      rect.setFill(new LinearGradient(0.0, 0.0, 1.0, 0.0, true, 
                          CycleMethod.NO_CYCLE, stops));
      
      Rectangle rect2 = new Rectangle(400.0, 200.0);
      rect2.setStroke(Color.BLACK);
      rect2.setFill(new LinearGradient(0.0, 0.0, 0.0, 1.0, true, 
                          CycleMethod.NO_CYCLE, stops));
      
      Rectangle rect3 = new Rectangle(400.0, 200.0);
      rect3.setStroke(Color.BLACK);
      rect3.setFill(new LinearGradient(0.0, 1.0, 1.0, 0.0, true, 
                           CycleMethod.NO_CYCLE, stops));
      Rectangle rect4 = new Rectangle(400.0, 200.0);
      rect4.setStroke(Color.BLACK);
      rect4.setFill(new LinearGradient(1.0, 0.0, 0.0, 1.0, true, 
                           CycleMethod.NO_CYCLE, stops));
      
      Rectangle rect5 = new Rectangle(400.0, 200.0);
      rect5.setStroke(Color.BLACK);
      rect5.setFill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, 
                           CycleMethod.NO_CYCLE, stops));
      Rectangle rect6 = new Rectangle(400.0, 200.0);
      rect6.setStroke(Color.BLACK);
      rect6.setFill(new LinearGradient(1.0, 1.0, 0.0, 0.0, true, 
                           CycleMethod.NO_CYCLE, stops));
      
      Rectangle rect7 = new Rectangle(400.0, 50.0);
      rect7.setStroke(Color.BLACK);
      rect7.setFill(new LinearGradient(0.0, 0.0, 200.0, 0.0, false, 
                                       CycleMethod.NO_CYCLE, stops));
      Rectangle rect8 = new Rectangle(400.0, 50.0);
      rect8.setStroke(Color.BLACK);
      rect8.setFill(new LinearGradient(0.0, 0.0, 100.0, 0.0, false, 
                            CycleMethod.REPEAT, stops));
      Rectangle rect9 = new Rectangle(400.0, 50.0);
      rect9.setStroke(Color.BLACK);
      rect9.setFill(new LinearGradient(0.0, 0.0, 100.0, 0.0, false, 
                            CycleMethod.REFLECT, stops));
      List<Stop> stops2 = new ArrayList<Stop>();
      stops2.add(new Stop(0.0, Color.RED));
      stops2.add(new Stop(0.5, Color.GREEN));
      stops2.add(new Stop(1.0, Color.BLUE));
      Rectangle rect10 = new Rectangle(400.0, 50.0);
      rect10.setStroke(Color.BLACK);
      rect10.setFill(new LinearGradient(0.0, 0.0, 1.0, 0.0, true, 
                            CycleMethod.NO_CYCLE, stops2));
      
      Rectangle rect11 = new Rectangle(400.0, 50.0);
      rect11.setStroke(Color.BLACK);
      rect11.setFill(LinearGradient.valueOf(
                     "linear-gradient(from 0% 0% to 50% 0%, reflect, " +
                     "#000000 0%, #ffffff 100%)"));
      
      pane.getChildren().addAll(rect10, rect, rect2, rect3, rect4, rect5, rect6,
            rect7, rect8, rect9, rect11);
      root.setContent(pane);
      Scene scene = new Scene(root, 500, 400);
      window.setScene(scene);
      window.setTitle("Класс LinearGradient");
      window.show();
   }
}