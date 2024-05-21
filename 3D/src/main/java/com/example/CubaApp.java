package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.LightBase;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class CubaApp extends Application {

    private static final double SIZE = 300;
    private final Content content = Content.create(SIZE);

    private static final class Content {

        private final Group group = new Group();
        private final Group cube = new Group();
        private final Group axes = new Group();
        private final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        private final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        private final Box box1;
        private final Box box2;
        private final Box box3;
        private final Box box4;
        private PointLight light;
        
        private static Content create(double size) {
            Content c = new Content(size);
            c.cube.getChildren().addAll(c.box1, c.box2, c.box3, c.box4);
            c.cube.getTransforms().addAll(c.rz, c.ry, c.rx);
            c.group.getChildren().addAll(c.cube, c.axes);
            c.group.getChildren().addAll(c.light);
            return c;
        }

        private Content(double size) {

            double edge = 3 * SIZE / 4;
            box1 = new Box(edge, edge, edge);
            Image texture1_mat = new Image(getClass().getResource("bg_image.jpg").toExternalForm()); //Текстура кубиков
            //Image texture1_num = new Image(getClass().getResource("one.jpg").toExternalForm()); //Текстура номеров
            PhongMaterial material1 = new PhongMaterial(Color.CORAL); //материал + цвет
            //material1.setDiffuseMap(texture1); 
            box1.setMaterial(material1);

            box2 = new Box(edge, edge, edge);
            box2.setMaterial(new PhongMaterial(Color.AQUA));
            box2.setTranslateX(edge);

            box3 = new Box(edge, edge, edge);
            box3.setMaterial(new PhongMaterial(Color.YELLOW));
            box3.setTranslateX(-edge);

            box4 = new Box(edge, edge, edge);
            box4.setMaterial(new PhongMaterial(Color.RED));
            box4.setTranslateY(edge);

            box1.setLayoutX(0);
            box2.setLayoutX(0);
            box3.setLayoutX(0);
            box4.setLayoutX(0);

            light = new PointLight();

            light.setTranslateZ(-1000); 
            light.setTranslateX(+1000); 
            light.setTranslateY(+10);

            light.getScope().addAll(box1,box2,box3,box4);

            System.out.println(light.getColor());
            System.out.println(light.isLightOn());

        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX 3D");

        VBox root = new VBox(10);
        VBox sliders = new VBox(15);

        sliders.setPadding(new Insets(20, 20, 20, 20));

        double trX = 10.0, trY = 10.0, trZ = -50.0;
        Translate tr = new Translate(trX, trY, trZ);

        Slider sliderPower = new Slider(-100.0, 100.0, trX);
        Slider sliderAttenuation = new Slider(-100.0, 100.0, trY);

        sliderPower.relocate(50.0, 330.0);
        sliderPower.setPrefWidth(400.0);
        sliderPower.setShowTickLabels(true);

        sliderAttenuation.relocate(50.0, 360.0);
        sliderAttenuation.setPrefWidth(400.0);
        sliderAttenuation.setShowTickLabels(true);

        sliderPower.valueProperty().addListener((obj, oldValue, newValue) -> {
            tr.setX(newValue.doubleValue());
            });
        sliderAttenuation.valueProperty().addListener((obj, oldValue, newValue) -> {
            tr.setY(newValue.doubleValue());
        });

        sliders.getChildren().addAll(sliderPower, sliderAttenuation);

        SubScene subScene = new SubScene(content.group, SIZE * 2, SIZE * 2, true, SceneAntialiasing.BALANCED);

        sliders.setAlignment(Pos.CENTER);
        root.getChildren().addAll(subScene, sliders);
        Scene scene = new Scene(root, SIZE * 2, SIZE * 2 + 150, true, SceneAntialiasing.BALANCED);

        primaryStage.setScene(scene);
        subScene.setFill(Color.BLACK);

        subScene.setOnMouseDragged((final MouseEvent e) -> {
            content.rx.setAngle(-e.getSceneY() * 360 / scene.getHeight());
            content.ry.setAngle(-e.getSceneX() * 360 / scene.getWidth());
        }); 
        
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(SIZE * 10);
        camera.setTranslateZ(-5 * SIZE);
        subScene.setCamera(camera);
        subScene.setOnScroll((final ScrollEvent e) -> {
            camera.setTranslateZ(camera.getTranslateZ() + e.getDeltaY());
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}