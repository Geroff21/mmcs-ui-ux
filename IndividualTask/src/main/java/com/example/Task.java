package com.example;
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private LocalDate deadline;
    private String description;
    private boolean completed;
    private String comment;
    private LocalDate completionDate;
    private double grade;
    private double maxGrade;

    public Task(String name, LocalDate deadline, double maxGrade, String description) {
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.completed = false;
        this.comment = "";
        this.completionDate = null;
        this.maxGrade = maxGrade;
        this.grade = 0.0;
    }

    //Getters & setters

    //Name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    //Grade
    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    //MaxGrade
    public double getMaxGrade() { return maxGrade; }
    public void setMaxGrade(double maxGrade) { this.maxGrade = maxGrade; }

    //Deadline
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    
    //Description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    //Completion Date
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }

    //Comment
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    //Completed
    public boolean getCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isCompleted() { return completed;}
    public boolean isCompletedOnTime() { 

        boolean flag = false;

        if (completionDate != null && deadline != null) {
            flag = deadline.compareTo(completionDate) >= 0; 
        }

        return completed && flag;
    }


    @Override
    public String toString() {
        String res = name + " | Срок выполнения: " + deadline;

        if (completed && this.grade >= 0 && this.completionDate != null ) {
            res += " | Дата выполнения: " + this.completionDate + " | Оценка: " + this.grade; 

            if (this.maxGrade >= 0) {
                res += " / " + this.maxGrade;
            } 

        }
        
        return res;
    }
}
