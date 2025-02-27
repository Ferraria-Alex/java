package com.alexandre.Java_Sql_CDA.repository;

import com.alexandre.Java_Sql_CDA.db.Db;
import com.alexandre.Java_Sql_CDA.model.Category;
import com.alexandre.Java_Sql_CDA.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskRepository {
    private static Connection connection = Db.get_connection();

    public static Task save(Task task){

        Task newTask = null;

        try {
            String request =  "INSERT INTO tasks(title, content, end_date, user_id) VALUE (?,?,?,(SELECT id FROM users WHERE email = ?))";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, task.getTitle());
            prepare.setString(2, task.getContent());
            prepare.setDate(3, task.getEndDate());
            prepare.setString(4, task.getUser().getEmail());

            if(exists(task.getId())){
                throw new RuntimeException("Task Exists");
            }

            int rows = prepare.executeUpdate();

            if(rows > 0){
                newTask = new Task();
                newTask.setTitle(task.getTitle());
                newTask.setContent(task.getContent());
                newTask.setEndDate(task.getEndDate());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return newTask;
    }

    public static Task findById(int id){

        Task getTask = null;

        try {
            String request =  "SELECT t.id AS ID, t.title AS `Title`, t.content AS `Content`, t.creation_date AS `Creation Date`, t.end_date AS `End Date`, t.`status` AS `Status`, t.user_id AS UserID, GROUP_CONCAT(c.id) AS \"Id Categories\", GROUP_CONCAT(c.`name`) AS \"Categories\" FROM tasks AS t " +
                    "INNER JOIN task_category AS tc ON t.id = tc.task_id" +
                    "INNER JOIN categories AS c ON tc.category_id = c.id" +
                    "GROUP BY t.id;";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();


            while (resultSet.next()){
                getTask = new Task();
                getTask.setId(id);
                getTask.setTitle(resultSet.getString("title"));
                getTask.setContent(resultSet.getString("content"));
                getTask.setCreationDate(resultSet.getDate("creation_date"));
                getTask.setEndDate(resultSet.getDate("end_date"));
                getTask.setStatus(resultSet.getBoolean("status"));
                getTask.setUser(UserRepository.findById(resultSet.getInt("user_id")));
                String[] listName = resultSet.getString("Categories").split(",");
                String[] listId = resultSet.getString("Id Categories").split(",");
                for(int i  = 0; i < listName.length; i++){
                    Category cat = new Category(listName[i]);
                    cat.setId(Integer.parseInt(listId[i]));
                    getTask.addCategory(cat);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTask;
    }

    public static boolean exists(int id){

        boolean getTask = false;

        try {
            String request =  "SELECT title, content, creation_date, end_date, user_id FROM tasks WHERE id = ?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getTask = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTask;
    }

    public static List<Task> findAll(){

        List<Task> tasks = new ArrayList<>();

        try {
            String request =  "SELECT t.id, t.title, t.content, t.creation_date, t.end_date, t.`status`, t.user_id, GROUP_CONCAT(c.`name`) AS \"Categories\" FROM tasks AS t " +
                    "INNER JOIN task_category AS tc ON t.id = tc.task_id" +
                    "INNER JOIN categories AS c ON tc.category_id = c.id" +
                    "GROUP BY t.id;";

            PreparedStatement prepare = connection.prepareStatement(request);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                Task getTask = new Task();
                getTask.setId(resultSet.getInt("id"));
                getTask.setTitle(resultSet.getString("title"));
                getTask.setContent(resultSet.getString("content"));
                getTask.setCreationDate(resultSet.getDate("creation_date"));
                getTask.setEndDate(resultSet.getDate("end_date"));
                getTask.setStatus(resultSet.getBoolean("status"));
                getTask.setUser(UserRepository.findById(resultSet.getInt("user_id")));
                String[] listName = resultSet.getString("Categories").split(",");
                String[] listId = resultSet.getString("Id Categories").split(",");
                for(int i  = 0; i < listName.length; i++){
                    Category cat = new Category(listName[i]);
                    cat.setId(Integer.parseInt(listId[i]));
                    getTask.addCategory(cat);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }
}
