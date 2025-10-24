package com.mikul223.todolist;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TodoList todoList = new TodoList();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("  To-Do List Application  ");

        // Задачи тестовые
        todoList.addTask("математика", "счет");
        todoList.addTask("русский", "сочинение", LocalDate.now().plusDays(7), 4, "Учеба");
        todoList.addTask("купить продукты", "молоко, хлеб, фрукты", LocalDate.now().plusDays(2), 2, "дом");

        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n   Главное меню   ");
            System.out.println("1. Показать все задачи");
            System.out.println("2. Добавить задачу");
            System.out.println("3. Найти задачу по ID"); // в конец ?
            System.out.println("4. Редактировать задачу");
            System.out.println("5. Удалить задачу");
            System.out.println("6. Показать задачи по статусу");
            System.out.println("7. Показать просроченные задачи");
            System.out.println("8. Показать важные задачи");
            System.out.println("9. Сортировать по приоритету");
            System.out.println("10. Сортировать по срочности");
            System.out.println("11. Сортировать по дате выполнения");
            System.out.println("12. Поиск по названию");
            System.out.println("13. Поиск по категории");
            System.out.println("0. Выйти");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllTasks();
                    break;
                case 2:
                    addNewTask();
                    break;
                case 3:
                    findTaskById();
                    break;
                case 4:
                    editTask();
                    break;
                case 5:
                    deleteTask();
                    break;
                case 6:
                    showTasksByStatus();
                    break;
                case 7:
                    showOverdueTasks();
                    break;
                case 8:
                    showImportantTasks();
                    break;
                case 9:
                    showTasksSortedByPriority();
                    break;
                case 10:
                    showTasksSortedByUrgency();
                    break;
                case 11:
                    showTasksSortedByDueDate();
                    break;
                case 12:
                    searchTasksByTitle();
                    break;
                case 13:
                    searchTasksByCategory();
                    break;
                case 0:
                    System.out.println("Выход из приложения...");
                    return;
                default:
                    System.out.println("Неверный выбор :(");
            }
        }
    }

    private static void showAllTasks() {
        List<TTask> tasks = todoList.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        }
        else {
            System.out.println("\n=== Все задачи ===");
            for (TTask task : tasks) {
                System.out.println(task);
            }
            System.out.println("Всего задач: " + todoList.getTotalTasks() + ", выполнено: " + todoList.getCompletedCount());
        }
    }

    private static void addNewTask() {
        System.out.println("\n   Добавление новой задачи   ");
        System.out.print("Введите название: ");
        String title = scanner.nextLine();

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        System.out.print("Добавить срок выполнения? (да/нет): ");
        if (scanner.nextLine().equalsIgnoreCase("да")) {
            System.out.print("Введите срок выполнения (гггг-мм-дд): ");
            LocalDate dueDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Введите приоритет (1-5): ");
            int priority = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введите категорию: ");
            String category = scanner.nextLine();

            todoList.addTask(title, description, dueDate, priority, category);
        }
        else {
            todoList.addTask(title, description);
        }
        System.out.println("Задача добавлена успешно!");
    }

    private static void findTaskById() {
        System.out.print("Введите ID задачи: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TTask task = todoList.getTaskById(id);
        if (task != null) {
            System.out.println("Найдена задача: " + task);
        }
        else {
            System.out.println("Задача с ID " + id + " не найдена :(");
        }
    }

    private static void editTask() {
        System.out.print("Введите ID задачи для редактирования: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TTask task = todoList.getTaskById(id);
        if (task == null) {
            System.out.println("Задача не найдена :(");
            return;
        }

        System.out.println("Редактирование задачи: " + task);
        System.out.println("1. Изменить статус");
        System.out.println("2. Изменить приоритет");
        System.out.println("3. Изменить срок выполнения");
        System.out.print("Выберите опцию: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Введите новый статус (TODO/IN_PROGRESS/DONE): ");
                String status = scanner.nextLine();
                if (todoList.updateTaskStatus(id, status)) {
                    System.out.println("Статус обновлен.");
                } else {
                    System.out.println("Ошибка обновления статуса.");
                }
                break;
            case 2:
                System.out.print("Введите новый приоритет (1-5): ");
                int priority = scanner.nextInt();
                scanner.nextLine();
                if (todoList.updateTaskPriority(id, priority)) {
                    System.out.println("Приоритет обновлен.");
                } else {
                    System.out.println("Ошибка обновления приоритета.");
                }
                break;
            case 3:
                System.out.print("Введите новый срок (гггг-мм-дд): ");
                LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                task.setDueDate(dueDate);
                System.out.println("Срок выполнения обновлен.");
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void deleteTask() {
        System.out.print("Введите ID задачи для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (todoList.deleteTask(id)) {
            System.out.println("Задача удалена успешно!");
        } else {
            System.out.println("Задача с ID " + id + " не найдена.");
        }
    }

    private static void showTasksByStatus() {
        System.out.print("Введите статус (TODO/IN_PROGRESS/DONE): ");
        String status = scanner.nextLine();

        List<TTask> tasks = todoList.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println("Задачи с статусом '" + status + "' не найдены.");
        } else {
            System.out.println("\n=== Задачи со статусом '" + status + "' ===");
            for (TTask task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void showOverdueTasks() {
        List<TTask> overdueTasks = todoList.getOverdueTasks();
        if (overdueTasks.isEmpty()) {
            System.out.println("Просроченных задач нет.");
        } else {
            System.out.println("\n=== Просроченные задачи ===");
            for (TTask task : overdueTasks) {
                System.out.println("⚠ " + task);
            }
        }
    }

    private static void showImportantTasks() {
        List<TTask> importantTasks = todoList.getImportantTasks();
        if (importantTasks.isEmpty()) {
            System.out.println("Важных задач нет.");
        } else {
            System.out.println("\n=== Важные задачи ===");
            for (TTask task : importantTasks) {
                System.out.println(task);
            }
        }
    }

    private static void showTasksSortedByPriority() {
        List<TTask> sortedTasks = todoList.sortByPriority();
        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("\n=== Задачи отсортированные по приоритету ===");
            for (TTask task : sortedTasks) {
                System.out.println(task);
            }
        }
    }

    private static void showTasksSortedByUrgency() {
        List<TTask> sortedTasks = todoList.sortByUrgency();
        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("\n=== Задачи отсортированные по срочности ===");
            for (TTask task : sortedTasks) {
                System.out.println("[" + task.getUrgencyScore() + "] " + task);
            }
        }
    }

    private static void showTasksSortedByDueDate() {
        List<TTask> sortedTasks = todoList.sortByDueDate();
        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("\n=== Задачи отсортированные по дате выполнения ===");
            for (TTask task : sortedTasks) {
                System.out.println(task);
            }
        }
    }

    private static void searchTasksByTitle() {
        System.out.print("Введите ключевое слово для поиска: ");
        String keyword = scanner.nextLine();

        List<TTask> tasks = todoList.searchTasks(keyword);
        if (tasks.isEmpty()) {
            System.out.println("Задачи не найдены.");
        } else {
            System.out.println("\n=== Результаты поиска ===");
            for (TTask task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void searchTasksByCategory() {
        System.out.print("Введите категорию для поиска: ");
        String category = scanner.nextLine();

        List<TTask> tasks = todoList.searchTasksByCategory(category);
        if (tasks.isEmpty()) {
            System.out.println("Задачи в категории '" + category + "' не найдены.");
        } else {
            System.out.println("\n=== Задачи в категории '" + category + "' ===");
            for (TTask task : tasks) {
                System.out.println(task);
            }
        }
    }
}