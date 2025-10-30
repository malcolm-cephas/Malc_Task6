import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ToDoList extends JFrame 
{
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private static final String FILE_NAME = "tasks.txt";

    public ToDoList() 
    {
        setTitle("To-Do List");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top panel for adding tasks
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        topPanel.add(taskInput, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // Task list
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Bottom panel for delete/save/load
        JPanel bottomPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Selected");
        JButton saveButton = new JButton("Save Tasks");
        JButton loadButton = new JButton("Load Tasks");
        bottomPanel.add(deleteButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        saveButton.addActionListener(e -> saveTasks());
        loadButton.addActionListener(e -> loadTasks());

        // Auto-load on startup
        loadTasks();

        setVisible(true);
    }

    private void addTask() 
    {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) 
        {
            listModel.addElement(task);
            taskInput.setText("");
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Please enter a task first.");
        }
    }

    private void deleteTask() 
    {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Select a task to delete.");
        }
    }

    private void saveTasks() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) 
        {
            for (int i = 0; i < listModel.size(); i++) 
            {
                writer.write(listModel.getElementAt(i));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully!");
        } 
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasks() 
    {
        listModel.clear();
        File file = new File(FILE_NAME);
        if (file.exists()) 
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
            {
                String task;
                while ((task = reader.readLine()) != null) 
                {
                    listModel.addElement(task);
                }
            } 
            catch (IOException e) 
            {
                JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(ToDoList::new);
    }
}
