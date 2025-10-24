package com.mikul223.todolist;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TTask {
    private int id;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private int priority;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public TTask(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "TODO";
        this.priority = 3;
        this.category = "General";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }

    public TTask( int id, String title, String description, LocalDate dueDate, int priority, String category){
        this(id, title, description);
        this.dueDate = dueDate;
        setPriority(priority);
        this.category = category;

    }


    //g

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public int getPriority() {
        return priority;
    }
    public String getCategory() {
        return category;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    //s

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(String status) {
        if (isValidStatus(status)) {
            this.status = status;
            this.updatedAt = LocalDateTime.now();
        }
        else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPriority(int priority) {
        if (priority >= 1 && priority <= 5) {
            this.priority = priority;
            this.updatedAt = LocalDateTime.now();
        }
        else {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
    }

    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(LocalDate.now()) && !"DONE".equals(status);
    }
    public boolean isHighPriority() {
        return priority >= 4;
    }

    public void markAsDone() {
        setStatus("DONE");
    }

    public void markAsInProgress() {
        setStatus("IN_PROGRESS");
    }


    @Override
    public String toString() {
        String dueInfo = dueDate != null ?
                (isOverdue() ? "OVERDUE (" + dueDate + ")" : dueDate.toString()) : "No due date";
        return String.format("Task #%d: %s [%s] | Priority: %d/5 | Category: %s | Due: %s", id, title, status, priority, category, dueInfo);
    }


    public int getUrgencyScore() {
        int score = 0;

        score += priority * 10;


        if (isOverdue()) {
            score += 20;
        }


        if (dueDate != null) {
            long daysUntilDue = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
            if (daysUntilDue <= 3) {
                score += 15;
            } else if (daysUntilDue <= 7) {
                score += 5;
            }
        }

        return score;
    }



    private boolean isValidStatus(String status) {
        return status != null &&
                (status.equals("TODO") ||
                        status.equals("IN_PROGRESS") ||
                        status.equals("DONE"));
    }


}
