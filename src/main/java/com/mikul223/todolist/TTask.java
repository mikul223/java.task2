package com.mikul223.todolist;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TTask {

    public static final int MIN_PRIORITY = 1;
    public static final int MAX_PRIORITY = 5;
    public static final int HIGH_PRIORITY_THRESHOLD = 4;
    public static final int URGENT_DAYS_THRESHOLD = 3;
    public static final int SOON_DAYS_THRESHOLD = 7;
    public static final int PRIORITY_WEIGHT = 10;
    public static final int OVERDUE_BONUS = 20;
    public static final int URGENT_BONUS = 15;
    public static final int SOON_BONUS = 5;


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
        this.priority = (MIN_PRIORITY + MAX_PRIORITY) / 2;
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
        if (priority >= MIN_PRIORITY && priority <= MAX_PRIORITY) {
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
        return priority >= HIGH_PRIORITY_THRESHOLD;
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

        score += priority * PRIORITY_WEIGHT;


        if (isOverdue()) {
            score += OVERDUE_BONUS;
        }


        if (dueDate != null) {
            long daysUntilDue = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
            if (daysUntilDue <= URGENT_DAYS_THRESHOLD) {
                score += URGENT_BONUS;
            } else if (daysUntilDue <= SOON_DAYS_THRESHOLD) {
                score += SOON_BONUS;
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
