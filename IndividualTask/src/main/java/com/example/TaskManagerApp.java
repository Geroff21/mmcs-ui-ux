package com.example;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.*;
import java.time.LocalDate;
import java.util.Locale;

public class TaskManagerApp extends Application {

    Locale aLocale = Locale.forLanguageTag("ru-RU");

    // Existing fields
    private TaskManager taskManager;
    private ListView<Task> taskListView;
    private TextField nameTextField;
    private TextField maxGradeTextField;
    private DatePicker deadlineDatePicker;
    private TextArea descriptionTextArea;
    private Label messageTextField;
    private Label taskDescriptionLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        taskManager = loadTaskManager();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        VBox taskDetails = new VBox(5);
        taskDetails.setPadding(new Insets(5, 0, 5, 0));

        // Task name
        nameTextField = new TextField();
        nameTextField.setPromptText("Имя задания");

        // Task description
        descriptionTextArea = new TextArea();
        descriptionTextArea.setPrefHeight(100);
        descriptionTextArea.setPromptText("Описание задания");
        taskDescriptionLabel = new Label();

        // Task deadline
        deadlineDatePicker = new DatePicker();
        deadlineDatePicker.setPrefWidth(240);
        deadlineDatePicker.setMaxWidth(240);
        deadlineDatePicker.setPromptText("Срок выполнения");

        // Task maxGrade
        maxGradeTextField = new TextField();
        maxGradeTextField.setPrefWidth(240);
        maxGradeTextField.setMaxWidth(240);
        maxGradeTextField.setPromptText("Максимальная оценка");

        // Task errorField
        messageTextField = new Label();

        Button addButton = new Button("Добавить задание");
        addButton.setPrefWidth(117);
        addButton.setMaxWidth(117);
        addButton.setOnAction(e -> addTask());

        Button removeButton = new Button("Удалить задание");
        removeButton.setPrefWidth(118);
        removeButton.setMaxWidth(118);
        removeButton.setOnAction(e -> removeTask());

        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(addButton, removeButton);

        taskListView = new ListView<>();
        taskListView.setPrefHeight(200);
        taskListView.setItems(FXCollections.observableArrayList(taskManager.getAllTasks()));
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(task.toString());
                    if (task.isCompleted()) {
                        if (task.isCompletedOnTime()) {
                            setStyle("-fx-background-color: #8AD877; -fx-font-size: 14;");
                        }
                        else {
                            setStyle("-fx-background-color: #D83C37; -fx-font-size: 14;");
                        }

                    } else {
                        setStyle("-fx-background-color: #D3D3D3; -fx-font-size: 14;");
                    }
                }
            }
        });
        
        taskListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    showTaskDetails(selectedTask);
                }
            }
            if (event.getClickCount() == 1) {
                messageTextField.setText("");
            }
        });

        
        taskDetails.getChildren().addAll(nameTextField, descriptionTextArea, deadlineDatePicker, maxGradeTextField, buttons, messageTextField);
        root.setCenter(taskListView);
        root.setBottom(taskDetails);

        primaryStage.setScene(new Scene(root, 800, 550));
        primaryStage.setTitle("Manager");
        primaryStage.show();
    }

    private void addTask() {

        String name = nameTextField.getText();
        LocalDate deadline = deadlineDatePicker.getValue();
        double maxGrade = maxGradeTextField.getText() == "" ? -1.0 : maxGradeTextField.getText().matches("((-|\\\\+)?[0-9]+(\\\\.[0-9]+)?)+") ? Double.parseDouble(maxGradeTextField.getText()): -1.0;
        String description = descriptionTextArea.getText();

        messageTextField.setStyle("-fx-text-fill: red;");

        if (name.isEmpty()) {
            messageTextField.setText("Ошибка! Не введено имя задания");
            return;
        }

        if (deadline == null) {
            messageTextField.setText("Ошибка! Не введен срок выполнения задания");
            return;
        }

        if (maxGrade < 0.0) {
            messageTextField.setText("Ошибка! Неверно введена максимальная оценка");
            return;
        }

        messageTextField.setStyle("-fx-text-fill: green;");

        Task task = new Task(name, deadline, maxGrade, description);
        taskManager.addTask(task);
        messageTextField.setText("Занятие успешно добавлено!");
        saveTaskManager();
        taskListView.getItems().add(task);
        clearFields();
    }

    private void removeTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskManager.removeTask(selectedTask);
            saveTaskManager();
            taskListView.getItems().remove(selectedTask);
            clearFields();
        }
    }

    private void saveTaskManager() {
        try {
            FileOutputStream fileOut = new FileOutputStream("taskManager.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(taskManager);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TaskManager loadTaskManager() {
        try {
            FileInputStream fileIn = new FileInputStream("taskManager.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            TaskManager manager = (TaskManager) in.readObject();
            in.close();
            fileIn.close();
            return manager;
        } catch (IOException | ClassNotFoundException e) {
            return new TaskManager();
        }
    }

    private void clearFields() {
        nameTextField.clear();
        deadlineDatePicker.setValue(null);
        descriptionTextArea.clear();
        maxGradeTextField.clear();
        taskDescriptionLabel.setText("");
    }

    private void showTaskDetails(Task task) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle(task.getName());
    
        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(10));
    
        TextField nameTextField = new TextField(task.getName());
        TextField maxGradeTextField = new TextField(task.getMaxGrade() == 0.0 ? "" : Double.toString(task.getMaxGrade())) ;
        maxGradeTextField.setPromptText("Максимальная оценка");
        DatePicker newDeadlineDatePicker = new DatePicker(task.getDeadline());
        TextArea descriptionTextArea = new TextArea(task.getDescription());
        CheckBox completedCheckBox = new CheckBox("Выполнено");
        completedCheckBox.setSelected(task.isCompleted());

        maxGradeTextField.setPrefWidth(240);
        maxGradeTextField.setMaxWidth(240);

        newDeadlineDatePicker.setPrefWidth(240);
        newDeadlineDatePicker.setMaxWidth(240);
    
        // Поля для комментария, оценки и даты выполнения
        Label commentLabel = new Label("Комментарий к выполнению:");
        TextField commentTextField = new TextField();
        commentTextField.setText(task.getComment());
        commentTextField.setPromptText("Комментарий к выполнению");
        commentLabel.setVisible(task.isCompleted());
        commentTextField.setVisible(task.isCompleted());

        commentTextField.setPrefWidth(240);
        commentTextField.setMaxWidth(240);
    
        Label completionDateLabel = new Label("Дата выполнения:");
        DatePicker completionDatePicker = new DatePicker(task.getCompletionDate());
        completionDateLabel.setVisible(task.isCompleted());
        completionDatePicker.setVisible(task.isCompleted());

        completionDatePicker.setPrefWidth(240);
        completionDatePicker.setMaxWidth(240);
    
        Label gradeLabel = new Label("Оценка:");
        TextField gradeTextField = new TextField(task.getGrade() == 0.0 ? "" : Double.toString(task.getGrade()));
        gradeTextField.setPromptText("Оценка");
        gradeLabel.setVisible(task.isCompleted());
        gradeTextField.setVisible(task.isCompleted());

        gradeTextField.setPrefWidth(240);
        gradeTextField.setMaxWidth(240);
    
        completedCheckBox.setOnAction(event -> {
            boolean isChecked = completedCheckBox.isSelected();
            commentLabel.setVisible(isChecked);
            commentTextField.setVisible(isChecked);
            completionDateLabel.setVisible(isChecked);
            completionDatePicker.setVisible(isChecked);
            gradeLabel.setVisible(isChecked);
            gradeTextField.setVisible(isChecked);
        });

        Button removeButton = new Button("Удалить");
        removeButton.setPrefWidth(118);
        removeButton.setMaxWidth(118);
        removeButton.setOnAction(e -> {
            detailsStage.close();
            removeTask();
        });
    
        Button saveButton = new Button("Сохранить");
        saveButton.setPrefWidth(118);
        saveButton.setMaxWidth(118);
        saveButton.setOnAction(event -> {
            task.setName(nameTextField.getText());
            task.setMaxGrade(maxGradeTextField.getText() == "" ? -1.0 : Double.parseDouble(maxGradeTextField.getText()) );
            task.setDescription(descriptionTextArea.getText());
            task.setCompleted(completedCheckBox.isSelected());
            task.setComment(commentTextField.getText());
            LocalDate newDeadline = newDeadlineDatePicker.getValue();

            messageTextField.setText("");
            messageTextField.setStyle("-fx-text-fill: red;");

            if (task.getName().isEmpty()) {
                messageTextField.setText("Ошибка! Не введено имя задания");
                return;
            }

            if (task.getMaxGrade() < 0.0) {
                messageTextField.setText("Ошибка! Неверно введена максимальная оценка");
                return;
            }

            if (newDeadline != null && !newDeadline.equals(task.getDeadline())) {
                task.setDeadline(newDeadline);
            }

            if (completedCheckBox.isSelected()) {
                task.setCompletionDate(completionDatePicker.getValue());
                
                double newGradeF = gradeTextField.getText() == "" ? -1.0 : Double.parseDouble(gradeTextField.getText() );
                task.setGrade(newGradeF);

                if (completionDatePicker.getValue() == null ) {
                    messageTextField.setText("Ошибка! Неверно введена дата выполнения");
                    return;
                }
    
                if (newGradeF < 0.0 ) {
                    messageTextField.setText("Ошибка! Неверно введена оценка");
                    return;
                }


            } else {
                task.setCompletionDate(null);
                task.setGrade(0.0);
                task.setComment("");
            }

            messageTextField.setStyle("-fx-text-fill: green;");

            saveTaskManager();
            taskListView.refresh();
            detailsStage.close();
        });
    
        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(saveButton, removeButton);

        detailsBox.getChildren().addAll(
            new Label("Название:"), nameTextField,
            new Label("Описание:"), descriptionTextArea,
            new Label("Срок выполнения:"), newDeadlineDatePicker,
            new Label("Максимальная оценка:"), maxGradeTextField,
            completedCheckBox,
            gradeLabel, gradeTextField,
            completionDateLabel, completionDatePicker,
            commentLabel, commentTextField,
            buttons
        );
    
        Scene detailsScene = new Scene(detailsBox, 400, 600);
        detailsStage.setScene(detailsScene);
        detailsStage.showAndWait();
    }
    
}
