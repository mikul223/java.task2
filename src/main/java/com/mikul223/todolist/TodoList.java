package com.mikul223.todolist;

import java.time.LocalDate;
import java.util.*;





public class TodoList {
    private List<TTask> tasks;
    private int nextId;

    public TodoList() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
    }


    public void addTask(String title, String description) {
        TTask task = new TTask(nextId, title, description);
        tasks.add(task);
        nextId++;
    }


    public void addTask(String title, String description, LocalDate dueDate, int priority, String category) {
        TTask task = new TTask(nextId, title, description, dueDate, priority, category);
        tasks.add(task);
        nextId++;
    }
    public List<TTask> getAllTasks() {
        return tasks;
    }




    public TTask getTaskById(int id) {
        for (TTask task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }


    public boolean deleteTask(int id) {
        TTask task = getTaskById(id);
        if (task != null) {
            tasks.remove(task);
            return true;
        }
        return false;
    }


    public List<TTask> getTasksByStatus(String status) {
        List<TTask> result = new ArrayList<>();
        for (TTask task : tasks) {
            if (task.getStatus().equals(status)) {
                result.add(task);
            }
        }
        return result;
    }


    public List<TTask> getOverdueTasks() {
        List<TTask> result = new ArrayList<>();
        for (TTask task : tasks) {
            if (task.isOverdue()) {
                result.add(task);
            }
        }
        return result;
    }

    public List<TTask> getImportantTasks() {
        List<TTask> result = new ArrayList<>();
        for (TTask task : tasks) {
            if (task.getPriority() >= 4) {
                result.add(task);
            }
        }
        return result;
    }

    // приоритет убывание
    public List<TTask> sortByPriority() {
        List<TTask> sorted = new ArrayList<>(tasks);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(i).getPriority() < sorted.get(j).getPriority()) {
                    TTask temp = sorted.get(i);
                    sorted.set(i, sorted.get(j));
                    sorted.set(j, temp);
                }
            }
        }
        return sorted;
    }

    // срочность
    public List<TTask> sortByUrgency() {
        List<TTask> sorted = new ArrayList<>(tasks);
        sorted.sort((t1, t2) -> {
            int score1 = t1.getUrgencyScore();
            int score2 = t2.getUrgencyScore();
            return Integer.compare(score2, score1);
        });
        return sorted;
    }

    // data
    public List<TTask> sortByDueDate() {
        List<TTask> sorted = new ArrayList<>(tasks);
        sorted.sort((t1, t2) -> {
            if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
            if (t1.getDueDate() == null) return 1;
            if (t2.getDueDate() == null) return -1;
            return t1.getDueDate().compareTo(t2.getDueDate());
        });
        return sorted;
    }

    public List<TTask> searchTasks(String search) {
        List<TTask> result = new ArrayList<>();
        for (TTask task : tasks) {
            if (task.getTitle().toLowerCase().contains(search.toLowerCase())) {
                result.add(task);
            }
        }
        return result;
    }

    public void markTaskDone(int id) {
        TTask task = getTaskById(id);
        if (task != null) {
            task.markAsDone();
        }
    }

    public int getTotalTasks() {
        return tasks.size();
    }


    public int getCompletedCount() {
        int count = 0;
        for (TTask task : tasks) {
            if ("DONE".equals(task.getStatus())) {
                count++;
            }
        }
        return count;
    }


    // обновляет статус

    public boolean updateTaskStatus(int id, String status) {
        TTask task = getTaskById(id);
        if (task != null) {
            try {
                task.setStatus(status);
                return true;
            }
            catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }


    //обновл приоритет

    public boolean updateTaskPriority(int id, int priority) {
        TTask task = getTaskById(id);
        if (task != null) {
            try {
                task.setPriority(priority);
                return true;
            }
            catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }



    //ищет задачи по категории

    public List<TTask> searchTasksByCategory(String category) {
        List<TTask> result = new ArrayList<>();
        for (TTask task : tasks) {
            if (task.getCategory().equalsIgnoreCase(category)) {
                result.add(task);
            }
        }
        return result;
    }
}