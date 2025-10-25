package com.mikul223.todolist;

import javax.swing.*;
import java.awt.*;

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