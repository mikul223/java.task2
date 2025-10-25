package com.mikul223.todolist;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TodoList todoList = new TodoList();
    private static Scanner scanner = new Scanner(System.in);


    private static void waitForEnter() {
        System.out.print("\n Нажмите Enter чтобы продолжить...");
        scanner.nextLine();
    }


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
            System.out.println("3. Редактировать задачу");
            System.out.println("4. Удалить задачу");
            System.out.println("5. Показать задачи по статусу");
            System.out.println("6. Показать просроченные задачи");
            System.out.println("7. Показать важные задачи");
            System.out.println("8. Сортировать по приоритету");
            System.out.println("9. Сортировать по срочности");
            System.out.println("10. Сортировать по дате выполнения");
            System.out.println("11. Найти задачу по ID");
            System.out.println("12. Поиск по названию");
            System.out.println("13. Поиск по категории");
            System.out.println("0. Выйти");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllTasks();
                    waitForEnter();
                    break;
                case 2:
                    addNewTask();
                    waitForEnter();
                    break;
                case 3:
                    editTask();
                    waitForEnter();
                    break;
                case 4:
                    deleteTask();
                    waitForEnter();
                    break;
                case 5:
                    showTasksByStatus();
                    waitForEnter();
                    break;
                case 6:
                    showOverdueTasks();
                    waitForEnter();
                    break;
                case 7:
                    showImportantTasks();
                    waitForEnter();
                    break;
                case 8:
                    showTasksSortedByPriority();
                    waitForEnter();
                    break;
                case 9:
                    showTasksSortedByUrgency();
                    waitForEnter();
                    break;
                case 10:
                    showTasksSortedByDueDate();
                    waitForEnter();
                    break;
                case 11:
                    findTaskById();
                    waitForEnter();
                    break;
                case 12:
                    searchTasksByTitle();
                    waitForEnter();
                    break;
                case 13:
                    searchTasksByCategory();
                    waitForEnter();
                    break;
                case 0:
                    System.out.println("Выход из приложения...");
                    return;
                default:
                    System.out.println("Неверный выбор :(");
                    waitForEnter();
            }
        }
    }

    private static void showAllTasks() {
        List<TTask> tasks = todoList.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        }
        else {
            System.out.println("\n    Все задачи    ");
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

            LocalDate dueDate = null;
            boolean validDate = false;

            while (!validDate) {
                try {
                    System.out.print("Введите срок выполнения (гггг-мм-дд) или '0' для отмены: ");
                    String dateInput = scanner.nextLine();

                    if (dateInput.equalsIgnoreCase("0")) {
                        System.out.println("Отмена добавления даты.");

                        break;
                    }

                    dueDate = LocalDate.parse(dateInput);
                    validDate = true;

                } catch (Exception e) {
                    System.out.println(" Неверный формат даты! ");
                    System.out.println(" Попробуйте снова или ведите '0' чтобы добавить задачу без даты");
                }
            }
            if (!validDate) {
                todoList.addTask(title, description);
            } else {
                System.out.print("Введите приоритет (TTask.MIN_PRIORITY-TTask.MAX_PRIORITY): ");
                int priority = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Введите категорию: ");
                String category = scanner.nextLine();

                todoList.addTask(title, description, dueDate, priority, category);
            }
        }
        else {
            todoList.addTask(title, description);
        }
        System.out.println("Задача добавлена успешно!");
    }


    private static void findTaskById() {
        System.out.print("Введите ID задачи для поиска: ");
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
        System.out.println("0. Выйти");
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
                System.out.print("Введите новый приоритет (TTask.MIN_PRIORITY-TTask.MAX_PRIORITY): ");
                int priority = scanner.nextInt();
                scanner.nextLine();
                if (todoList.updateTaskPriority(id, priority)) {
                    System.out.println("Приоритет обновлен.");
                } else {
                    System.out.println("Ошибка обновления приоритета.");
                }
                break;
            case 3:
                LocalDate dueDate = null;
                boolean validDate = false;

                while (!validDate) {
                    try {
                        System.out.print("Введите новый срок (гггг-мм-дд) или '0' для отмены: ");
                        String dateInput = scanner.nextLine();

                        if (dateInput.equalsIgnoreCase("0")) {
                            System.out.println("Отмена изменения даты.");
                            break;
                        }

                        dueDate = LocalDate.parse(dateInput);
                        validDate = true;

                    }
                    catch (Exception e) {
                        System.out.println(" Неверный формат даты!");
                        System.out.println(" Попробуйте снова или введите '0' чтобы отменить изменение даты");
                    }
                }

                if (validDate) {
                    if (todoList.updateTaskDueDate(id, dueDate)) {
                        System.out.println("Срок выполнения обновлен.");
                    }
                    else {
                        System.out.println("Ошибка обновления срока выполнения.");
                    }
                }
                break;
            case 0:
                System.out.println("Возврат в главное меню...");
                break;
            default:
                System.out.println(" Ошибка: опция " + choice + " не существует!");
                System.out.println("   Доступные опции: 1, 2, 3, 0");
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
            System.out.println("\n    Задачи со статусом '" + status + "'    ");
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
            System.out.println("\n    Просроченные задачи    ");
            for (TTask task : overdueTasks) {
                System.out.println("! " + task);
            }
        }
    }

    private static void showImportantTasks() {
        List<TTask> importantTasks = todoList.getImportantTasks();
        if (importantTasks.isEmpty()) {
            System.out.println("Важных задач нет.");
        } else {
            System.out.println("\n    Важные задачи    ");
            for (TTask task : importantTasks) {
                System.out.println(task);
            }
        }
    }

    private static void showTasksSortedByPriority() {
        List<TTask> sortedTasks = todoList.sortByPriority();
        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        }
        else {
            System.out.println("\n   Задачи отсортированные по приоритету    ");
            for (TTask task : sortedTasks) {
                System.out.println(task);
            }
        }
    }

    private static void showTasksSortedByUrgency() {
        List<TTask> sortedTasks = todoList.sortByUrgency();
        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        }
        else {
            System.out.println("\n    Задачи отсортированные по срочности    ");
            for (TTask task : sortedTasks) {
                System.out.println("[" + task.getUrgencyScore() + "] " + task);
            }
        }
    }

    private static void showTasksSortedByDueDate() {
        List<TTask> sortedTasks = todoList.sortByDueDate();
        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        }
        else {
            System.out.println("\n   Задачи отсортированные по дате выполнения    ");
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
        }
        else {
            System.out.println("\n   Результаты поиска    ");
            for (TTask task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void searchTasksByCategory() {
        System.out.print("Введите категорию для поиска: ");
        String category = scanner.nextLine();

        if (category == null || category.trim().isEmpty()) {
            System.out.println("Ошибка: категория не может быть пустой");
            return;
        }
        List<TTask> tasks = todoList.searchTasksByCategory(category);
        if (tasks.isEmpty()) {
            System.out.println("Задачи в категории '" + category + "' не найдены.");
        }
        else {
            System.out.println("\n Задачи в категории '" + category + "' ");
            for (TTask task : tasks) {
                System.out.println(task);
            }
        }
    }
}