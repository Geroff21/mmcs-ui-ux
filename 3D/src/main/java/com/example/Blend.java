package com.example;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Blend extends Application {

   public static void main(String[] args) {
      Application.launch(args);
   }


   public void start(Stage stage) throws Exception {
      ScrollPane root = new ScrollPane();
      VBox box = new VBox(15.0);
      box.setAlignment(Pos.CENTER);
      box.setPadding(new Insets(20.0));
      Button button2 = new Button("Порядок отображения слоев");
      button2.setOnAction(event -> {
         newWindow2();
      });
      box.getChildren().add(button2);
      List<BlendMode> list = new ArrayList<>();
      list.add(BlendMode.SRC_OVER);
      list.add(BlendMode.SRC_ATOP);
      
      list.add(BlendMode.RED);
      list.add(BlendMode.GREEN);
      list.add(BlendMode.BLUE);

      // Группа затемнения
      list.add(BlendMode.DARKEN);     // Замена темным
      list.add(BlendMode.MULTIPLY);   // Умножение
      list.add(BlendMode.COLOR_BURN); // Затемнение основы
      
      // Группа увеличения контрастности
      list.add(BlendMode.OVERLAY);    // Перекрытие
      list.add(BlendMode.SOFT_LIGHT); // Мягкий свет
      list.add(BlendMode.HARD_LIGHT); // Жесткий свет
      
      // Группа осветления
      list.add(BlendMode.LIGHTEN);     // Замена светлым
      list.add(BlendMode.SCREEN);      // Экран
      list.add(BlendMode.COLOR_DODGE); // Осветление основы
      list.add(BlendMode.ADD);         // Линейный осветлитель
      
      // Группа сравнения
      list.add(BlendMode.DIFFERENCE); // Разница
      list.add(BlendMode.EXCLUSION);  // Исключение
      

      for (BlendMode b: list) {
         Button button = new Button(b.toString());
         button.setMnemonicParsing(false);
         button.setOnAction(event -> {
            newWindow(b);
         });
         box.getChildren().add(button);
      }
      root.setContent(box);
      Scene scene = new Scene(root, 400.0, 300.0);
      stage.setTitle("BlendMode");
      stage.setScene(scene);

      stage.show();
   }

   public void newWindow(BlendMode bm) {
      Stage window = new Stage();
      Rectangle rect = new Rectangle(100.0, 100.0);
      rect.setStroke(Color.CHOCOLATE);
      rect.setFill(Color.rgb(0, 0, 0, 0.8));
      rect.relocate(100.0, 70.0);
      rect.setBlendMode(bm);
      Rectangle rect8 = new Rectangle(100.0, 100.0);
      rect8.setStroke(Color.CHOCOLATE);
      rect8.setFill(Color.rgb(128, 128, 128, 0.8));
      rect8.relocate(250.0, 70.0);
      rect8.setBlendMode(bm);
      Rectangle rect2 = new Rectangle(100.0, 100.0);
      rect2.setStroke(Color.CHOCOLATE);
      rect2.setFill(Color.rgb(255, 255, 255, 0.8));
      rect2.relocate(400.0, 70.0);
      rect2.setBlendMode(bm);
      Stop[] stops = new Stop[] {
            new Stop(0.0, Color.BLACK), new Stop(1.0, Color.WHITE)
      };
      Rectangle rect9 = new Rectangle(400.0, 70.0);
      rect9.setStroke(Color.CHOCOLATE);
      rect9.setFill(new LinearGradient(0.0, 0.0, 1.0, 0.0, true, 
                                       CycleMethod.NO_CYCLE, stops));
      rect9.relocate(100.0, 320.0);
      rect9.setBlendMode(bm);
      
      Rectangle rect3 = new Rectangle(50.0, 50.0);
      rect3.setFill(Color.RED);
      rect3.relocate(200.0, 250.0);
      rect3.setMouseTransparent(true); // Нельзя переместить
      Rectangle rect4 = new Rectangle(50.0, 50.0);
      rect4.setFill(Color.GREEN);
      rect4.relocate(300.0, 250.0);
      rect4.setMouseTransparent(true); // Нельзя переместить
      Rectangle rect7 = new Rectangle(50.0, 50.0);
      rect7.setFill(Color.BLUE);
      rect7.relocate(400.0, 250.0);
      rect7.setMouseTransparent(true); // Нельзя переместить

      Rectangle rect5 = new Rectangle(70.0, 70.0);
      rect5.setStroke(Color.CHOCOLATE);
      rect5.setFill(Color.rgb(0, 0, 0));
      rect5.relocate(50.0, 200.0);
      Rectangle rect6 = new Rectangle(70.0, 70.0);
      rect6.setStroke(Color.CHOCOLATE);
      rect6.setFill(Color.rgb(255, 255, 255));
      rect6.relocate(500.0, 200.0);

      Pane pane = new Pane();
      pane.getChildren().addAll(rect3, rect4, rect7, rect5, rect6, rect,
            rect8, rect2, rect9);
     
      Scene scene = new Scene(pane, 600, 400);
      window.setScene(scene);
      window.setTitle("BlendMode " + bm);
      
      Point click = new Point(0.0, 0.0);
      boolean[] isDragDetect = {false};
      scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
         if (event.getTarget() instanceof Rectangle) {
            Rectangle node = (Rectangle)event.getTarget();
            click.setPoint(event.getSceneX() - node.getLayoutX(),
                           event.getSceneY() - node.getLayoutY());
            if (event.getClickCount() == 2) {
               node.toFront();
            }
         }
      });
      scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
         if (event.isDragDetect()) isDragDetect[0] = true;
         if (isDragDetect[0] && event.getTarget() instanceof Rectangle) {
            Rectangle node = (Rectangle)event.getTarget();
            node.relocate(event.getSceneX() - click.getX(), 
                          event.getSceneY() - click.getY());
         }
      });
      scene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
         isDragDetect[0] = false;
      });
      window.show();
   }
   
   public void newWindow2() {
      Stage window = new Stage();
      Rectangle rect = new Rectangle(100.0, 100.0);
      rect.setFill(Color.RED);
      rect.setId("rectRed");
      rect.relocate(150.0, 200.0);
      Rectangle rect2 = new Rectangle(100.0, 100.0);
      rect2.setFill(Color.GREEN);
      rect2.setId("rectGreen");
      rect2.relocate(200.0, 150.0);
      Rectangle rect3 = new Rectangle(100.0, 100.0);
      rect3.setFill(Color.BLUE);
      rect3.setId("rectBlue");
      rect3.relocate(250.0, 100.0);
      
      Pane pane = new Pane();
      pane.getChildren().addAll(rect, rect2, rect3);
      Point click = new Point(0, 0);
      Scene scene = new Scene(pane, 600, 400);
      window.setScene(scene);
      window.setTitle("Порядок отображения слоев");
      rect.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
         click.setPoint(event.getX(), event.getY());
      });
      rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
         rect.relocate(event.getSceneX() - click.getX(), 
                       event.getSceneY() - click.getY());
      });
      rect2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
         click.setPoint(event.getX(), event.getY());
      });
      rect2.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
         rect2.relocate(event.getSceneX() - click.getX(), 
                        event.getSceneY() - click.getY());
      });
      scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
         if (event.getTarget() instanceof Rectangle) {
            Rectangle node = (Rectangle)event.getTarget();
            if (event.getClickCount() == 2) {
               if (event.isPrimaryButtonDown()) node.toFront();
               else if (event.isSecondaryButtonDown()) node.toBack();
               pane.getChildren().stream().forEachOrdered(n -> {
                  System.out.println(n.getId());
               });
            }
         }
      });
      
      window.show();
      pane.getChildren().stream().forEachOrdered(n -> {
         System.out.println(n.getId());
      });
   }
}

class Point {
   private double x, y;

   public Point(double x, double y) {
      this.x = x;
      this.y = y;
   }
   public double getX() {
      return x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return y;
   }

   public void setY(double y) {
      this.y = y;
   }
   
   public void setPoint(double x, double y) {
      this.x = x;
      this.y = y;
   }
   
   @Override
   public String toString() {
      return "Point [x=" + x + ", y=" + y + "]";
   }
}
