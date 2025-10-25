package com.mikul223.todolist;

import java.time.LocalDate;
import java.util.*;

public class TodoList {
    private List<Task> tasks;
    private int nextId;

    public TodoList() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
    }

    //Методы добавления задач
    public void addTask(String title, String description) {
        Task task = new Task(nextId, title, description);
        tasks.add(task);
        nextId++;
    }


    public void addTask(String title, String description, LocalDate dueDate,
                        int priority, String category) {
        Task task = new Task(nextId, title, description, dueDate, priority, category);
        tasks.add(task);
        nextId++;
    }
    public List<Task> getAllTasks() {
        return tasks;
    }



    //Поиск задачи по ID
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }


    public boolean deleteTask(int id) {
        Task task = getTaskById(id);
        if (task != null) {
            tasks.remove(task);
            return true;
        }
        return false;
    }

    //Получение задач по статусу
    public List<Task> getTasksByStatus(String status) {
        List<Task> result = new ArrayList<>();
        if (status == null) {
            return result;
        }
        for (Task task : tasks) {
            if (status.equals(task.getStatus())) {
                result.add(task);
            }
        }
        return result;
    }


    public List<Task> getOverdueTasks() {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isOverdue()) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Task> getImportantTasks() {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() >= Task.HIGH_PRIORITY_THRESHOLD) {
                result.add(task);
            }
        }
        return result;
    }

    // Сортировка пузырьком по приоритету (по убыванию)
    public List<Task> sortByPriority() {
        List<Task> sorted = new ArrayList<>(tasks);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(i).getPriority() < sorted.get(j).getPriority()) {
                    Task temp = sorted.get(i);
                    sorted.set(i, sorted.get(j));
                    sorted.set(j, temp);
                }
            }
        }
        return sorted;
    }

    // Сортировка по срочности
    public List<Task> sortByUrgency() {
        List<Task> sorted = new ArrayList<>(tasks);
        sorted.sort((t1, t2) -> {
            int score1 = t1.getUrgencyScore();
            int score2 = t2.getUrgencyScore();
            return Integer.compare(score2, score1);
        });
        return sorted;
    }

    // Сортировка по дате выполнения. Задачи без даты в конец, с датой в начало
    public List<Task> sortByDueDate() {
        List<Task> sorted = new ArrayList<>(tasks);
        sorted.sort((t1, t2) -> {
            if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
            if (t1.getDueDate() == null) return 1;
            if (t2.getDueDate() == null) return -1;
            return t1.getDueDate().compareTo(t2.getDueDate());
        });
        return sorted;
    }

    //Поиск задач по названию
    public List<Task> searchTasks(String search) {
        List<Task> result = new ArrayList<>();
        if (search == null) {
            return result;
        }
        String searchLower = search.toLowerCase();
        for (Task task : tasks) {
            if (task.getTitle() != null && task.getTitle().toLowerCase().contains(searchLower)) {
                result.add(task);
            }
        }
        return result;
    }

/*
    //на будущее мб. интерфейс
    public void markTaskDone(int id) {
        TTask task = getTaskById(id);
        if (task != null) {
            task.markAsDone();
        }
    }

 */

    public int getTotalTasks() {
        return tasks.size();
    }


    public int getCompletedCount() {
        int count = 0;
        for (Task task : tasks) {
            if ("DONE".equals(task.getStatus())) {
                count++;
            }
        }
        return count;
    }


    // Обновление статуса с обработкой исключений
    public boolean updateTaskStatus(int id, String status) {
        Task task = getTaskById(id);
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


    //Обновление приоритета с обработкой исключений

    public boolean updateTaskPriority(int id, int priority) {
        Task task = getTaskById(id);
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



    //Поиск по категории (регистронезависимый)

    public List<Task> searchTasksByCategory(String category) {
        List<Task> result = new ArrayList<>();

        if (category == null) {
            return result;
        }
        for (Task task : tasks) {
            if (category.equalsIgnoreCase(task.getCategory())) {
                result.add(task);
            }
        }
        return result;
    }

    public boolean updateTaskDueDate(int id, LocalDate dueDate) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setDueDate(dueDate);
            return true;
        }
        return false;
    }


}
