package com.mikul223.todolist;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.List;
import java.awt.Color;
import java.awt.GridLayout;

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
        // Временная картинка
        JLabel imagePlaceholder = new JLabel("png", JLabel.CENTER);
        imagePlaceholder.setFont(new Font("Arial", Font.ITALIC, 14));
        imagePlaceholder.setForeground(Color.GRAY);
        imagePanel.add(imagePlaceholder);
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
        exitButton.setBackground(new Color(26, 67, 97));
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
        // Очищаем таблицу
        tableModel.setRowCount(0);
        // Получаем задачи

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
        tableModel = new DefaultTableModel(columnNames, 0) { // 0 вместо 40 - пустая таблица
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tasksTable = new JTable(tableModel);

        // Настраиваем внешний вид таблицы
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tasksTable.getTableHeader().setReorderingAllowed(false);
        tasksTable.setRowHeight(25);

        // Настраиваем ширину колонок
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
        tableScrollPane.setPreferredSize(new Dimension(700, 400));

        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Панель с кнопками внизу
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JButton("Добавить задачу"));
        bottomPanel.add(new JButton("Показать все"));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);


        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        refreshTasksTable();
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