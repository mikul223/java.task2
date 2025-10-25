package com.mikul223.todolist;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;


public class TodoListGUI {
    private JFrame mainFrame;

    public TodoListGUI() {
        initializeGUI();
    }

    // Окно
    private void initializeGUI() {
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
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 40) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Редактирование в таблице запрещено
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