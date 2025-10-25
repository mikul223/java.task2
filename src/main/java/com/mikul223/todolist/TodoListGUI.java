package com.mikul223.todolist;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.List;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

public class TodoListGUI {
    private JFrame mainFrame;
    private JTable tasksTable;
    private DefaultTableModel tableModel;
    private TodoList todoList;

    public TodoListGUI() {
        this.todoList = new TodoList();
        initializeTestData();
        showMainMenu();
    }

    //Главное меню
    private void showMainMenu() {

        JFrame menuFrame = new JFrame("Менеджер задач");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 600);
        menuFrame.setLocationRelativeTo(null);

        // Основная панель меню
        JPanel menuPanel = new JPanel(new BorderLayout(20, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        menuPanel.setBackground(new Color(240, 240, 240));

        // Заголовок
        JLabel titleLabel = new JLabel("Менеджер задач", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(89, 146, 195));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        // Центральная панель для картинки и кнопок
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBackground(new Color(240, 240, 240));
        // Панель для картинки
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(240, 240, 240));
        imagePanel.setPreferredSize(new Dimension(300, 200)); // Место для картинки
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
     /*   // Временная картинка
        JLabel imagePlaceholder = new JLabel("png", JLabel.CENTER);
        imagePlaceholder.setFont(new Font("Arial", Font.ITALIC, 14));
        imagePlaceholder.setForeground(Color.GRAY);
        imagePanel.add(imagePlaceholder);
        centerPanel.add(imagePanel, BorderLayout.CENTER);

      */
        // Картинка из файла

        try {
            java.net.URL imageUrl = getClass().getResource("/com/mikul223/todolist/resources/кот.png");

            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage), JLabel.CENTER);
                imagePanel.add(imageLabel);
                System.out.println( imageUrl);
            } else {
                throw new Exception("Картинка не найдена по пути: /com/mikul223/todolist/resources/кот.png");
            }

        } catch (Exception e) {

            System.out.println("Проблема с загрузкой картинки :( " + e.getMessage());
            JLabel imageLabel = new JLabel("Тут должен быть котик :3", JLabel.CENTER);
            imageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            imageLabel.setForeground(new Color(89, 146, 195));
            imagePanel.add(imageLabel);
        }
        centerPanel.add(imagePanel, BorderLayout.CENTER);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setPreferredSize(new Dimension(100, 150));

        // Кнопка "Показать задачи"
        JButton showTasksButton = new JButton("Показать задачи");
        showTasksButton.setFont(new Font("Arial", Font.PLAIN, 16));
        showTasksButton.setBackground(new Color(111, 161, 205));
        showTasksButton.setForeground(Color.WHITE);
        showTasksButton.setFocusPainted(false);
        showTasksButton.setPreferredSize(new Dimension(100, 55));

        // Кнопка "Выйти"
        JButton exitButton = new JButton("Выйти");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setBackground(new Color(51, 100, 143));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(100, 55));

        // Добавляем обработчики кнопок
        showTasksButton.addActionListener(e -> {
            menuFrame.dispose(); // Закрываем меню
            showTasksWindow();   // Показываем окно с задачами
        });

        // Выход из приложения
        exitButton.addActionListener(e -> {
            System.exit(0);
        });


        buttonPanel.add(showTasksButton);
        buttonPanel.add(exitButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        menuPanel.add(centerPanel, BorderLayout.CENTER);

        menuFrame.add(menuPanel);
        menuFrame.setVisible(true);
    }


    //Тестовые задачи
    private void initializeTestData() {
        todoList.addTask("Математика", "Сделать домашнее задание");
        todoList.addTask("Русский", "Написать сочинение",
                java.time.LocalDate.now().plusDays(7), 4, "Учеба");
        todoList.addTask("Купить продукты", "Молоко, хлеб, фрукты",
                java.time.LocalDate.now().plusDays(2), 2, "Дом");
    }

    // Обновление данных в таблице
    private void refreshTasksTable() {
        tableModel.setRowCount(0);

        java.util.List<Task> tasks = todoList.getAllTasks();
        for (Task task : tasks) {
            Object[] rowData = {
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getCategory(),
                    task.getDueDate() != null ?
                            task.getDueDate().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")) :
                            "Нет", task.isOverdue() ? "Да" : "Нет"
            };
            tableModel.addRow(rowData);
        }
    }

    // Окно
    private void showTasksWindow() {
        // Главное окно
        mainFrame = new JFrame("Управление задачами");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);


        // Основная панель с отступами
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel titleLabel = new JLabel("Менеджер задач", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Таблица с задачами
        String[] columnNames = {"ID", "Название", "Описание", "Статус", "Приоритет",
                "Категория", "Дедлайн", "Просрочена?"
        };

        // Модель таблицы
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tasksTable = new JTable(tableModel);


        // Внешний вид таблицы
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tasksTable.getTableHeader().setReorderingAllowed(false);
        tasksTable.setRowHeight(25);

        tasksTable.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tasksTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Название
        tasksTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Описание
        tasksTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Статус
        tasksTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Приоритет
        tasksTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Категория
        tasksTable.getColumnModel().getColumn(6).setPreferredWidth(80); // Дедлайн
        tasksTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Просрочена?

        // Таблица прокручиваема
        JScrollPane tableScrollPane = new JScrollPane(tasksTable);
        tableScrollPane.setPreferredSize(new Dimension(700, 300));
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);



        //  ПАНЕЛЬ УПРАВЛЕНИЯ
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));

        // Верхний ряд кнопок 3 кнопки
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton addButton = new JButton("Добавить");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(new Color(89, 146, 195));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(140, 35));
        addButton.addActionListener(e -> {
            showAddTaskDialog();
        });

        JButton editButton = new JButton("Изменить");
        editButton.setFont(new Font("Arial", Font.PLAIN, 16));
        editButton.setBackground(new Color(89, 146, 195));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setPreferredSize(new Dimension(140, 35));
        editButton.addActionListener(e -> {
            editSelectedTask();
        });

        JButton deleteButton = new JButton("Удалить");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 16));
        deleteButton.setBackground(new Color(51, 100, 143));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(140, 35));
        deleteButton.addActionListener(e -> {
            deleteSelectedTask();
        });

        topButtonPanel.add(addButton);
        topButtonPanel.add(editButton);
        topButtonPanel.add(deleteButton);

        // Нижний ряд кнопок
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));

        // Панель для кнопок "Сортировать" и "Найти"
        JPanel leftButtonsPanel = new JPanel();
        leftButtonsPanel.setLayout(new BoxLayout(leftButtonsPanel, BoxLayout.Y_AXIS));
        leftButtonsPanel.setPreferredSize(new Dimension(150, 80));

        JButton sortButton = new JButton("Сортировать");
        sortButton.setFont(new Font("Arial", Font.PLAIN, 14));
        sortButton.setBackground(new Color(89, 146, 195));
        sortButton.setForeground(Color.WHITE);
        sortButton.setFocusPainted(false);
        sortButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sortButton.setMaximumSize(new Dimension(140, 35));
        sortButton.addActionListener(e -> {
            showSortDialog();
        });

        JButton searchButton = new JButton("Найти");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setBackground(new Color(89, 146, 195));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setMaximumSize(new Dimension(140, 35));
        searchButton.addActionListener(e -> {
            showSearchDialog();
        });

        leftButtonsPanel.add(Box.createVerticalStrut(5));
        leftButtonsPanel.add(sortButton);
        leftButtonsPanel.add(Box.createVerticalStrut(10));
        leftButtonsPanel.add(searchButton);
        leftButtonsPanel.add(Box.createVerticalStrut(5));

        // Кнопка "Важное!"
        JButton importantButton = new JButton("Важное!");
        importantButton.setFont(new Font("Arial", Font.BOLD, 14));
        importantButton.setBackground(new Color(51, 100, 143));
        importantButton.setForeground(Color.WHITE);
        importantButton.setFocusPainted(false);
        importantButton.setPreferredSize(new Dimension(120, 70));
        importantButton.addActionListener(e -> {
            showImportantTasksDialog();
        });

        bottomButtonPanel.add(leftButtonsPanel);
        bottomButtonPanel.add(importantButton);

        controlPanel.add(topButtonPanel, BorderLayout.NORTH);
        controlPanel.add(bottomButtonPanel, BorderLayout.CENTER);

        // Кнопка "Назад в меню"
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Назад в меню");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            showMainMenu();
        });
        backPanel.add(backButton);

        controlPanel.add(backPanel, BorderLayout.SOUTH);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        refreshTasksTable();
    }

    private void showAddTaskDialog() {
        JDialog addDialog = new JDialog(mainFrame, "Добавить новую задачу", true);
        addDialog.setLayout(new BorderLayout());
        addDialog.setSize(400, 400);
        addDialog.setLocationRelativeTo(mainFrame);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Поля формы
        JTextField titleField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        JTextField dueDateField = new JTextField();
        JSpinner prioritySpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JTextField categoryField = new JTextField("General");

        formPanel.add(new JLabel("Название*:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Описание:"));
        formPanel.add(descriptionScroll);
        formPanel.add(new JLabel("Срок (гггг-мм-дд):"));
        formPanel.add(dueDateField);
        formPanel.add(new JLabel("Приоритет (1-5):"));
        formPanel.add(prioritySpinner);
        formPanel.add(new JLabel("Категория:"));
        formPanel.add(categoryField);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        saveButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Название задачи не может быть пустым!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String description = descriptionArea.getText().trim();
                String dueDateText = dueDateField.getText().trim();
                int priority = (Integer) prioritySpinner.getValue();
                String category = categoryField.getText().trim();

                // Методы из TodoList
                if (dueDateText.isEmpty()) {
                    todoList.addTask(title, description);
                } else {
                    java.time.LocalDate dueDate = java.time.LocalDate.parse(dueDateText);
                    todoList.addTask(title, description, dueDate, priority, category);
                }

                refreshTasksTable();
                addDialog.dispose();
                JOptionPane.showMessageDialog(mainFrame, "Задача добавлена успешно! :)", "Успех",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Ошибка при добавлении задачи: " + ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> addDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    // Редактирование выбранной задачи
    private void editSelectedTask() {
        int selectedRow = tasksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Пожалуйста, выберите задачу для редактирования!",
                    "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int taskId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Task task = todoList.getTaskById(taskId);

        if (task == null) {
            JOptionPane.showMessageDialog(mainFrame, "Задача не найдена!", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        showEditTaskDialog(task);
    }

    // Редактирование задачи
    private void showEditTaskDialog(Task task) {
        JDialog editDialog = new JDialog(mainFrame, "Редактирование задачи", true);
        editDialog.setLayout(new BorderLayout());
        editDialog.setSize(400, 400);
        editDialog.setLocationRelativeTo(mainFrame);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Поля формы с текущими значениями
        JLabel idLabel = new JLabel(String.valueOf(task.getId()));
        JTextField titleField = new JTextField(task.getTitle());
        JTextArea descriptionArea = new JTextArea(task.getDescription(), 3, 20);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);

        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"TODO", "IN_PROGRESS", "DONE"});
        statusCombo.setSelectedItem(task.getStatus());

        JSpinner prioritySpinner = new JSpinner(new SpinnerNumberModel(task.getPriority(),
                1, 5, 1));

        JTextField dueDateField = new JTextField(
                task.getDueDate() != null ? task.getDueDate().toString() : ""
        );

        JTextField categoryField = new JTextField(task.getCategory());

        formPanel.add(new JLabel("ID:"));
        formPanel.add(idLabel);
        formPanel.add(new JLabel("Название*:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Описание:"));
        formPanel.add(descriptionScroll);
        formPanel.add(new JLabel("Статус:"));
        formPanel.add(statusCombo);
        formPanel.add(new JLabel("Приоритет:"));
        formPanel.add(prioritySpinner);
        formPanel.add(new JLabel("Срок:"));
        formPanel.add(dueDateField);
        formPanel.add(new JLabel("Категория:"));
        formPanel.add(categoryField);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        saveButton.addActionListener(e -> {
            try {
                // Обновление задачи через методы TodoList
                String newStatus = (String) statusCombo.getSelectedItem();
                int newPriority = (Integer) prioritySpinner.getValue();

                String dueDateText = dueDateField.getText().trim();
                if (!dueDateText.isEmpty()) {
                    java.time.LocalDate newDueDate = java.time.LocalDate.parse(dueDateText);
                    todoList.updateTaskDueDate(task.getId(), newDueDate);
                } else {
                    todoList.updateTaskDueDate(task.getId(), null);
                }

                todoList.updateTaskStatus(task.getId(), newStatus);
                todoList.updateTaskPriority(task.getId(), newPriority);

                refreshTasksTable();
                editDialog.dispose();
                JOptionPane.showMessageDialog(mainFrame, "Задача обновлена успешно! :)",
                        "Успех", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Ошибка при обновлении задачи: " +
                        ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> editDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }

    // Удаление выбранной задачи
    private void deleteSelectedTask() {
        int selectedRow = tasksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Пожалуйста, выберите задачу для удаления!",
                    "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int taskId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String taskTitle = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                mainFrame,
                "Вы уверены, что хотите удалить задачу:\n\"" + taskTitle + "\"?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Используем метод deleteTask из TodoList (как в Main)
            if (todoList.deleteTask(taskId)) {
                refreshTasksTable();
                JOptionPane.showMessageDialog(mainFrame, "Задача удалена успешно! :)",
                        "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Ошибка при удалении задачи!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Диалог выбора сортировки
    private void showSortDialog() {
        String[] options = {"Приоритет", "Срочность", "Дата выполнения"};

        String choice = (String) JOptionPane.showInputDialog(
                mainFrame,
                "Выберите тип сортировки:",
                "Сортировка задач",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice != null) {
            List<Task> sortedTasks;

            switch (choice) {
                case "Приоритет":
                    sortedTasks = todoList.sortByPriority();
                    break;
                case "Срочность":
                    sortedTasks = todoList.sortByUrgency();
                    break;
                case "Дата выполнения":
                    sortedTasks = todoList.sortByDueDate();
                    break;
                default:
                    sortedTasks = todoList.getAllTasks();
            }

            refreshTasksTableWithData(sortedTasks);
            JOptionPane.showMessageDialog(mainFrame,
                    "Задачи отсортированы по: " + choice,
                    "Сортировка",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // Диалог поиска задач
    private void showSearchDialog() {
        String[] searchOptions = {"ID", "Название", "Категория", "Статус"};

        String searchType = (String) JOptionPane.showInputDialog(
                mainFrame,
                "Выберите тип поиска:",
                "Поиск задач",
                JOptionPane.QUESTION_MESSAGE,
                null,
                searchOptions,
                searchOptions[0]
        );

        if (searchType != null) {
            String searchValue = JOptionPane.showInputDialog(
                    mainFrame,
                    "Введите значение для поиска:",
                    "Поиск по " + searchType,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (searchValue != null && !searchValue.trim().isEmpty()) {
                List<Task> foundTasks = new ArrayList<>();

                switch (searchType) {
                    case "ID":
                        try {
                            int id = Integer.parseInt(searchValue.trim());
                            Task task = todoList.getTaskById(id);
                            if (task != null) {
                                foundTasks.add(task);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Неверный формат ID! Введите число.",
                                    "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;

                    case "Название":
                        foundTasks = todoList.searchTasks(searchValue.trim());
                        break;

                    case "Категория":
                        foundTasks = todoList.searchTasksByCategory(searchValue.trim());
                        break;

                    case "Статус":
                        foundTasks = todoList.getTasksByStatus(searchValue.trim().toUpperCase());
                        break;
                }

                if (foundTasks.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Задачи не найдены по вашему запросу.",
                            "Результаты поиска",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    refreshTasksTableWithData(foundTasks);
                    JOptionPane.showMessageDialog(mainFrame,
                            "Найдено задач: " + foundTasks.size(),
                            "Результаты поиска",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    // Обновление таблицы с переданным списком задач
    private void refreshTasksTableWithData(List<Task> tasks) {
        tableModel.setRowCount(0);

        for (Task task : tasks) {
            Object[] rowData = {
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getCategory(),
                    task.getDueDate() != null ?
                            task.getDueDate().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")) :
                            "Нет",
                    task.isOverdue() ? "Да" : "Нет"
            };
            tableModel.addRow(rowData);
        }
    }

    // Выбор важных задач
    private void showImportantTasksDialog() {
        String[] options = {"Важное", "Просроченное"};

        String choice = (String) JOptionPane.showInputDialog(
                mainFrame,
                "Выберите тип задач для отображения:",
                "Важные задачи",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice != null) {
            List<Task> filteredTasks;
            String message;

            switch (choice) {
                case "Важное":
                    filteredTasks = todoList.getImportantTasks();
                    message = "важные задачи (приоритет 4-5)";
                    break;
                case "Просроченное":
                    filteredTasks = todoList.getOverdueTasks();
                    message = "просроченные задачи";
                    break;
                default:
                    filteredTasks = todoList.getAllTasks();
                    message = "все задачи";
            }

            if (filteredTasks.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        choice + " задачи не найдены!",
                        "Результат",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                refreshTasksTableWithData(filteredTasks);
                JOptionPane.showMessageDialog(mainFrame,
                        "Показаны " + message + ": " + filteredTasks.size() + " шт.",
                        "Результат",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoListGUI();
            }
        });
    }
}