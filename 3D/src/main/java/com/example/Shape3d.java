/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Shape3d extends Application {
   
   public static void main(String[] args) {
      Application.launch(args);
   }

   @Override
   public void start(Stage stage) throws Exception {
      VBox root = new VBox(10);
      root.setAlignment(Pos.CENTER);
      Button button1 = new Button("Класс Box");
      Button button2 = new Button("Класс Cylinder");
      Button button3 = new Button("Класс Sphere");
      Button button4 = new Button("Класс MeshView");
      
      root.getChildren().addAll(button1, button2, button3, button4);
      Scene scene = new Scene(root, 400, 300);
      
      button1.setOnAction(event -> {
         newWindow();
      });
      button2.setOnAction(event -> {
         newWindow2();
      });
      button3.setOnAction(event -> {
         newWindow3();
      });
      button4.setOnAction(event -> {
         newWindow4();
      });
      
      stage.setTitle("Shape3D");
      stage.setScene(scene);
      stage.show();
   }
   
   public void newWindow() {
      Stage window = new Stage();
     
      Box box = new Box(5.0, 5.0, 5.0);
      box.setMaterial(new PhongMaterial(Color.BLUE));
      box.setDrawMode(DrawMode.FILL);
      box.setCullFace(CullFace.BACK);
      box.setTranslateZ(5.0);
      /*System.out.println(box.getMaterial());
      System.out.println(box.getDrawMode());
      System.out.println(box.getCullFace());*/
      
      Box box2 = new Box();
      box2.setWidth(5.0);
      box2.setHeight(5.0);
      box2.setDepth(5.0);
      box2.setMaterial(new PhongMaterial(Color.RED));
      box2.setDrawMode(DrawMode.LINE);
      //box2.setDrawMode(DrawMode.FILL);
      box2.setCullFace(CullFace.BACK);
      //box2.setCullFace(CullFace.FRONT);
      box2.setTranslateX(10.0);
      
      /*System.out.println(box2.getWidth());
      System.out.println(box2.getHeight());
      System.out.println(box2.getDepth());*/
      
      System.out.println(Box.DEFAULT_SIZE); // 2.0
      
      PerspectiveCamera camera = new PerspectiveCamera(true);
      Translate tr = new Translate(5.0, 0.0, -40.0);
      camera.getTransforms().addAll(
            new Rotate(-30.0, Rotate.Y_AXIS),
            new Rotate(-30.0, Rotate.X_AXIS),
            tr
      );
      Line lineX = new Line(-100.0, 0.0, 100.0, 0.0);
      lineX.setStroke(Color.RED);
      Line lineY = new Line(0.0, -100.0, 0.0, 100.0);
      lineY.setStroke(Color.GREEN);
      Line lineZ = new Line(0.0, -100.0, 0.0, 100.0);
      lineZ.setRotationAxis(Rotate.X_AXIS);
      lineZ.setRotate(90.0);
      lineZ.setStroke(Color.BLUE);
      
      lineX.setVisible(false);
      lineY.setVisible(false);
      lineZ.setVisible(false);
      
      Button btn = new Button("Показать/скрыть оси");
      btn.relocate(50.0, 430.0);
      btn.setOnAction(event -> {
         lineX.setVisible(!lineX.isVisible());
         lineY.setVisible(!lineY.isVisible());
         lineZ.setVisible(!lineZ.isVisible());
      });
      Group group = new Group();
      group.getChildren().addAll(box, box2, lineX, lineY, lineZ);
      
      SubScene subScene = new SubScene(group, 500, 300, true,
                                       SceneAntialiasing.BALANCED);
      subScene.setFill(Color.ANTIQUEWHITE);
      subScene.setCamera(camera);
      subScene.relocate(5.0, 5.0);
      
      Group root = new Group();
      Slider sliderX = new Slider(-30.0, 40.0, 5.0);
      sliderX.relocate(50.0, 330.0);
      sliderX.setPrefWidth(400.0);
      
      Slider sliderY = new Slider(-20.0, 20.0, 0.0);
      sliderY.relocate(50.0, 360.0);
      sliderY.setPrefWidth(400.0);
      
      Slider sliderZ = new Slider(-80.0, 0.0, -40.0);
      sliderZ.relocate(50.0, 390.0);
      sliderZ.setPrefWidth(400.0);
      
      Text text = new Text("X");
      text.relocate(30.0, 330.0);
      Text text2 = new Text("Y");
      text2.relocate(30.0, 360.0);
      Text text3 = new Text("Z");
      text3.relocate(30.0, 390.0);
      
      root.getChildren().addAll(subScene, sliderX, sliderY, sliderZ,
                                text, text2, text3, btn);

      sliderX.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setX(newValue.doubleValue());
      });
      sliderY.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setY(newValue.doubleValue());
      });
      
      sliderZ.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setZ(newValue.doubleValue());
      });
      
      Scene scene = new Scene(root, 510, 500, Color.BURLYWOOD);
      window.setScene(scene);
      window.setTitle("Box");
      window.show();
   }

   public void newWindow2() {
      Stage window = new Stage();
     
      Cylinder cylinder = new Cylinder(2.0, 5.0);
      cylinder.setMaterial(new PhongMaterial(Color.BLUE));
      cylinder.setDrawMode(DrawMode.FILL);
      cylinder.setCullFace(CullFace.BACK);
      
      Cylinder cylinder2 = new Cylinder();
      cylinder2.setRadius(2.0);
      cylinder2.setHeight(5.0);
      cylinder2.setMaterial(new PhongMaterial(Color.RED));
      cylinder2.setDrawMode(DrawMode.LINE);
      cylinder2.setCullFace(CullFace.BACK);
      cylinder2.setTranslateX(10.0);
      
      System.out.println(cylinder2.getRadius());
      System.out.println(cylinder2.getHeight());
      System.out.println(cylinder2.getDivisions()); // 64

      Cylinder cylinder3 = new Cylinder(2.0, 5.0, 3);
      cylinder3.setMaterial(new PhongMaterial(Color.CHOCOLATE));
      cylinder3.setDrawMode(DrawMode.LINE);
      cylinder3.setCullFace(CullFace.BACK);
      cylinder3.setTranslateX(20.0);
      
      PerspectiveCamera camera = new PerspectiveCamera(true);
      Translate tr = new Translate(10.0, 5.0, -40.0);
      camera.getTransforms().addAll(
            new Rotate(-30.0, Rotate.Y_AXIS),
            new Rotate(-30.0, Rotate.X_AXIS),
            tr
      );
      Line lineX = new Line(-100.0, 0.0, 100.0, 0.0);
      lineX.setStroke(Color.RED);
      Line lineY = new Line(0.0, -100.0, 0.0, 100.0);
      lineY.setStroke(Color.GREEN);
      Line lineZ = new Line(0.0, -100.0, 0.0, 100.0);
      lineZ.setRotationAxis(Rotate.X_AXIS);
      lineZ.setRotate(90.0);
      lineZ.setStroke(Color.BLUE);
      
      lineX.setVisible(false);
      lineY.setVisible(false);
      lineZ.setVisible(false);
      
      Button btn = new Button("Показать/скрыть оси");
      btn.relocate(50.0, 430.0);
      btn.setOnAction(event -> {
         lineX.setVisible(!lineX.isVisible());
         lineY.setVisible(!lineY.isVisible());
         lineZ.setVisible(!lineZ.isVisible());
      });
      Group group = new Group();
      group.getChildren().addAll(cylinder, cylinder2, cylinder3, lineX, lineY, lineZ);
      
      SubScene subScene = new SubScene(group, 500, 300, true,
                                       SceneAntialiasing.BALANCED);
      subScene.setFill(Color.ANTIQUEWHITE);
      subScene.setCamera(camera);
      subScene.relocate(5.0, 5.0);
      
      Group root = new Group();
      Slider sliderX = new Slider(-30.0, 40.0, 10.0);
      sliderX.relocate(50.0, 330.0);
      sliderX.setPrefWidth(400.0);
      
      Slider sliderY = new Slider(-20.0, 20.0, 5.0);
      sliderY.relocate(50.0, 360.0);
      sliderY.setPrefWidth(400.0);
      
      Slider sliderZ = new Slider(-80.0, 0.0, -40.0);
      sliderZ.relocate(50.0, 390.0);
      sliderZ.setPrefWidth(400.0);
      
      Text text = new Text("X");
      text.relocate(30.0, 330.0);
      Text text2 = new Text("Y");
      text2.relocate(30.0, 360.0);
      Text text3 = new Text("Z");
      text3.relocate(30.0, 390.0);
      
      root.getChildren().addAll(subScene, sliderX, sliderY, sliderZ,
            text, text2, text3, btn);

      sliderX.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setX(newValue.doubleValue());
      });
      sliderY.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setY(newValue.doubleValue());
      });
      
      sliderZ.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setZ(newValue.doubleValue());
      });
      
      Scene scene = new Scene(root, 510, 500, Color.BURLYWOOD);
      window.setScene(scene);
      window.setTitle("Cylinder");
      window.show();
   }

   public void newWindow3() {
      Stage window = new Stage();
     
      Sphere sphere = new Sphere(2.0);
      sphere.setMaterial(new PhongMaterial(Color.BLUE));
      sphere.setDrawMode(DrawMode.FILL);
      sphere.setCullFace(CullFace.BACK);
      
      Sphere sphere2 = new Sphere();
      sphere2.setRadius(2.0);
      sphere2.setMaterial(new PhongMaterial(Color.RED));
      sphere2.setDrawMode(DrawMode.LINE);
      sphere2.setCullFace(CullFace.BACK);
      sphere2.setTranslateX(10.0);
      
      System.out.println(sphere2.getRadius());
      System.out.println(sphere2.getDivisions()); // 64
      System.out.println(sphere2.radiusProperty());

      Sphere sphere3 = new Sphere(2.0, 12);
      sphere3.setMaterial(new PhongMaterial(Color.CHOCOLATE));
      sphere3.setDrawMode(DrawMode.LINE);
      sphere3.setCullFace(CullFace.BACK);
      sphere3.setTranslateX(20.0);
      System.out.println(sphere3.getDivisions()); // 64
      
      PerspectiveCamera camera = new PerspectiveCamera(true);
      Translate tr = new Translate(10.0, 5.0, -40.0);
      camera.getTransforms().addAll(
            new Rotate(-30.0, Rotate.Y_AXIS),
            new Rotate(-30.0, Rotate.X_AXIS),
            tr
      );
      Line lineX = new Line(-100.0, 0.0, 100.0, 0.0);
      lineX.setStroke(Color.RED);
      Line lineY = new Line(0.0, -100.0, 0.0, 100.0);
      lineY.setStroke(Color.GREEN);
      Line lineZ = new Line(0.0, -100.0, 0.0, 100.0);
      lineZ.setRotationAxis(Rotate.X_AXIS);
      lineZ.setRotate(90.0);
      lineZ.setStroke(Color.BLUE);
      
      lineX.setVisible(false);
      lineY.setVisible(false);
      lineZ.setVisible(false);
      
      Button btn = new Button("Показать/скрыть оси");
      btn.relocate(50.0, 430.0);
      btn.setOnAction(event -> {
         lineX.setVisible(!lineX.isVisible());
         lineY.setVisible(!lineY.isVisible());
         lineZ.setVisible(!lineZ.isVisible());
      });
      Group group = new Group();
      group.getChildren().addAll(sphere, sphere2, sphere3, lineX, lineY, lineZ);
      
      SubScene subScene = new SubScene(group, 500, 300, true,
                                       SceneAntialiasing.BALANCED);
      subScene.setFill(Color.ANTIQUEWHITE);
      subScene.setCamera(camera);
      subScene.relocate(5.0, 5.0);
      
      Group root = new Group();
      Slider sliderX = new Slider(-30.0, 40.0, 10.0);
      sliderX.relocate(50.0, 330.0);
      sliderX.setPrefWidth(400.0);
      
      Slider sliderY = new Slider(-20.0, 20.0, 5.0);
      sliderY.relocate(50.0, 360.0);
      sliderY.setPrefWidth(400.0);
      
      Slider sliderZ = new Slider(-80.0, 0.0, -40.0);
      sliderZ.relocate(50.0, 390.0);
      sliderZ.setPrefWidth(400.0);
      
      Text text = new Text("X");
      text.relocate(30.0, 330.0);
      Text text2 = new Text("Y");
      text2.relocate(30.0, 360.0);
      Text text3 = new Text("Z");
      text3.relocate(30.0, 390.0);
      
      root.getChildren().addAll(subScene, sliderX, sliderY, sliderZ,
            text, text2, text3, btn);

      sliderX.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setX(newValue.doubleValue());
      });
      sliderY.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setY(newValue.doubleValue());
      });
      
      sliderZ.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setZ(newValue.doubleValue());
      });
      
      Scene scene = new Scene(root, 510, 500, Color.BURLYWOOD);
      window.setScene(scene);
      window.setTitle("Sphere");
      window.show();
   }

   public void newWindow4() {
      Stage window = new Stage();

      TriangleMesh mesh = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
      mesh.getPoints().addAll(
            0.0f, 10.0f, 0.0f,    // A 0
            0.0f, 0.0f, 0.0f,     // B 1
            10.0f, 0.0f, 0.0f,    // C 2
            10.0f, 10.0f, 0.0f    // D 3
      );
      mesh.getTexCoords().addAll(
            0.0f, 0.0f,           // 0
            1.0f, 0.0f,           // 1
            0.0f, 1.0f,           // 2
            1.0f, 1.0f            // 3
      );
      mesh.getFaces().addAll(
            0, 0, 1, 2, 2, 3,     // ABC
            0, 0, 2, 3, 3, 1      // ACD
      );
      
      PhongMaterial material = new PhongMaterial();
      material.setDiffuseMap(loadImage());
      
      MeshView meshView = new MeshView();
      //meshView.setMaterial(new PhongMaterial(Color.RED));
      meshView.setMaterial(material);
      meshView.setDrawMode(DrawMode.FILL);
      //meshView.setDrawMode(DrawMode.LINE);
      meshView.setCullFace(CullFace.NONE);
      meshView.setMesh(mesh);
      meshView.setTranslateZ(5.0);
      
      PerspectiveCamera camera = new PerspectiveCamera(true);
      double trX = 5.0, trY = 3.0, trZ = 40.0;
      double aX = 0.0, aY = 180.0, aZ = 180.0;
      Translate tr = new Translate(trX, trY, trZ);
      Rotate rx = new Rotate(aX, Rotate.X_AXIS);
      Rotate ry = new Rotate(aY, Rotate.Y_AXIS);
      Rotate rz = new Rotate(aZ, Rotate.Z_AXIS);
      camera.getTransforms().addAll(tr, rx, ry, rz);
      
      Line lineX = new Line(-1000.0, 0.0, 1000.0, 0.0);
      lineX.setStroke(Color.RED);
      Line lineY = new Line(0.0, -1000.0, 0.0, 1000.0);
      lineY.setStroke(Color.GREEN);
      Line lineZ = new Line(0.0, -1000.0, 0.0, 1000.0);
      lineZ.setRotationAxis(Rotate.X_AXIS);
      lineZ.setRotate(90.0);
      lineZ.setStroke(Color.BLUE);
      
      /*lineX.setVisible(false);
      lineY.setVisible(false);
      lineZ.setVisible(false);*/
      
      Button btn = new Button("Показать/скрыть оси");
      btn.relocate(50.0, 510.0);
      btn.setOnAction(event -> {
         lineX.setVisible(!lineX.isVisible());
         lineY.setVisible(!lineY.isVisible());
         lineZ.setVisible(!lineZ.isVisible());
      });
      
      Group group = new Group();
      group.getChildren().addAll(meshView, lineX, lineY, lineZ);
      
      SubScene subScene = new SubScene(group, 500, 300, true,
                                       SceneAntialiasing.BALANCED);
      subScene.setFill(Color.ANTIQUEWHITE);
      subScene.setCamera(camera);
      subScene.relocate(5.0, 5.0);
      
      Group root = new Group();
      Slider sliderX = new Slider(-100.0, 100.0, trX);
      sliderX.relocate(50.0, 330.0);
      sliderX.setPrefWidth(400.0);
      sliderX.setShowTickLabels(true);
      
      Slider sliderY = new Slider(-100.0, 100.0, trY);
      sliderY.relocate(50.0, 360.0);
      sliderY.setPrefWidth(400.0);
      sliderY.setShowTickLabels(true);
      
      Slider sliderZ = new Slider(-100.0, 100.0, trZ);
      sliderZ.relocate(50.0, 390.0);
      sliderZ.setPrefWidth(400.0);
      sliderZ.setShowTickLabels(true);
      
      Slider sliderRX = new Slider(-360.0, 360.0, aX);
      sliderRX.relocate(50.0, 420.0);
      sliderRX.setPrefWidth(400.0);
      sliderRX.setShowTickLabels(true);
      
      Slider sliderRY = new Slider(-360.0, 360.0, aY);
      sliderRY.relocate(50.0, 450.0);
      sliderRY.setPrefWidth(400.0);
      sliderRY.setShowTickLabels(true);
      
      Slider sliderRZ = new Slider(-360.0, 360.0, aZ);
      sliderRZ.relocate(50.0, 480.0);
      sliderRZ.setPrefWidth(400.0);
      sliderRZ.setShowTickLabels(true);
      
      Text text = new Text("X");
      text.relocate(30.0, 330.0);
      Text text2 = new Text("Y");
      text2.relocate(30.0, 360.0);
      Text text3 = new Text("Z");
      text3.relocate(30.0, 390.0);
      Text text4 = new Text("RX");
      text4.relocate(30.0, 420.0);
      Text text5 = new Text("RY");
      text5.relocate(30.0, 450.0);
      Text text6 = new Text("RZ");
      text6.relocate(30.0, 480.0);
      
      Text text7 = new Text("(x, y, z) -> (red, green, blue)");
      text7.relocate(300.0, 310.0);
      
      root.getChildren().addAll(subScene, sliderX, sliderY, sliderZ, sliderRX,
            sliderRY, sliderRZ,
            text, text2, text3, text4, text5, text6, btn, text7);

      sliderX.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setX(newValue.doubleValue());
      });
      sliderY.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setY(newValue.doubleValue());
      });
      
      sliderZ.valueProperty().addListener((obj, oldValue, newValue) -> {
         tr.setZ(newValue.doubleValue());
      });
      
      sliderRX.valueProperty().addListener((obj, oldValue, newValue) -> {
         rx.setAngle(newValue.doubleValue());
      });
      sliderRY.valueProperty().addListener((obj, oldValue, newValue) -> {
         ry.setAngle(newValue.doubleValue());
      });
      sliderRZ.valueProperty().addListener((obj, oldValue, newValue) -> {
         rz.setAngle(newValue.doubleValue());
      });
      
      Scene scene = new Scene(root, 510, 600, Color.BURLYWOOD);
      window.setScene(scene);
      window.setTitle("MeshView");
      window.show();
      
      scene.setOnMouseClicked(event -> {
         if (event.getClickCount() > 1) {
            sliderX.setValue(trX);
            sliderY.setValue(trY);
            sliderZ.setValue(trZ);
            sliderRX.setValue(aX);
            sliderRY.setValue(aY);
            sliderRZ.setValue(aZ);
         }
      });
      
   }
   
   private Image loadImage() {
      Image im = null;
      try {
         im = new Image(
            getClass().getResourceAsStream("bg_image.jpg"));
         if (im.isError()) new RuntimeException();
      } catch (Exception e) {
         System.out.println("Не удалось загрузить изображение");
      }
      return im;
   }
}
