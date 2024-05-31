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
    private DatePicker deadlineDatePicker;
    private TextArea descriptionTextArea;
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

        // Task deadline
        deadlineDatePicker = new DatePicker();
        deadlineDatePicker.setPromptText("Срок выполнения");

        // Task description
        descriptionTextArea = new TextArea();
        descriptionTextArea.setPromptText("Описание задания");
        taskDescriptionLabel = new Label();

        Button addButton = new Button("Добавить занятие");
        addButton.setOnAction(e -> addTask());

        Button removeButton = new Button("Удалить занятие");
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
                            setStyle("-fx-background-color: green;");
                        }
                        else {
                            setStyle("-fx-background-color: red;");
                        }

                    } else {
                        setStyle("-fx-background-color: gray;");
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
        });

        taskDetails.getChildren().addAll(nameTextField, deadlineDatePicker, descriptionTextArea, buttons, taskDescriptionLabel);
        root.setCenter(taskListView);
        root.setBottom(taskDetails);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Manager");
        primaryStage.show();
    }

    private void addTask() {
        String name = nameTextField.getText();
        LocalDate deadline = deadlineDatePicker.getValue();
        String description = descriptionTextArea.getText();

        if (name.isEmpty() || deadline == null) {
            return;
        }

        Task task = new Task(name, deadline, description);
        taskManager.addTask(task);
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
        taskDescriptionLabel.setText("");
    }

    private void showTaskDetails(Task task) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle(task.getName());
    
        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(10));
    
        TextField nameTextField = new TextField(task.getName());
        DatePicker newDeadlineDatePicker = new DatePicker(task.getDeadline());
        TextArea descriptionTextArea = new TextArea(task.getDescription());
        CheckBox completedCheckBox = new CheckBox("Выполнено");
        completedCheckBox.setSelected(task.isCompleted());
    
        // Поля для комментария, оценки и даты выполнения
        Label commentLabel = new Label("Комментарий к выполнению:");
        TextField commentTextField = new TextField();
        commentTextField.setText(task.getComment());
        commentTextField.setPromptText("Комментарий к выполнению");
        commentLabel.setVisible(task.isCompleted());
        commentTextField.setVisible(task.isCompleted());
    
        Label completionDateLabel = new Label("Дата выполнения:");
        DatePicker completionDatePicker = new DatePicker(task.getCompletionDate());
        completionDateLabel.setVisible(task.isCompleted());
        completionDatePicker.setVisible(task.isCompleted());
    
        Label gradeLabel = new Label("Оценка:");
        TextField gradeTextField = new TextField(task.getGrade() == 0.0 ? "" : Double.toString(task.getGrade()));
        gradeTextField.setPromptText("Оценка");
        gradeLabel.setVisible(task.isCompleted());
        gradeTextField.setVisible(task.isCompleted());
    
        completedCheckBox.setOnAction(event -> {
            boolean isChecked = completedCheckBox.isSelected();
            commentLabel.setVisible(isChecked);
            commentTextField.setVisible(isChecked);
            completionDateLabel.setVisible(isChecked);
            completionDatePicker.setVisible(isChecked);
            gradeLabel.setVisible(isChecked);
            gradeTextField.setVisible(isChecked);
        });
    
        Button saveButton = new Button("Сохранить!");
        saveButton.setOnAction(event -> {
            task.setName(nameTextField.getText());
            task.setDescription(descriptionTextArea.getText());
            task.setCompleted(completedCheckBox.isSelected());
            task.setComment(commentTextField.getText());
            LocalDate newDeadline = newDeadlineDatePicker.getValue();
            if (newDeadline != null && !newDeadline.equals(task.getDeadline())) {
                task.setDeadline(newDeadline);
            }
            if (completedCheckBox.isSelected()) {
                task.setCompletionDate(completionDatePicker.getValue());
                task.setGrade(Double.parseDouble(gradeTextField.getText()));

                if (descriptionTextArea.getText().equals("") ) {
                    return;
                }
    
                if (commentTextField.getText().equals("") ) {
                    return;
                }
    
                if (gradeTextField.getText().equals("") ) {
                    return;
                }


            } else {
                task.setCompletionDate(null);
                task.setGrade(0.0);
                task.setComment("");
            }

            saveTaskManager();
            taskListView.refresh();
            detailsStage.close();
        });
    
        detailsBox.getChildren().addAll(
            new Label("Название:"), nameTextField,
            new Label("Срок выполнения:"), newDeadlineDatePicker,
            new Label("Описание:"), descriptionTextArea,
            completedCheckBox,
            commentLabel, commentTextField,
            completionDateLabel, completionDatePicker,
            gradeLabel, gradeTextField,
            saveButton
        );
    
        Scene detailsScene = new Scene(detailsBox, 300, 500);
        detailsStage.setScene(detailsScene);
        detailsStage.showAndWait();
    }
    
}
