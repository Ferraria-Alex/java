package com.alexandre.Java_Sql_CDA.repository;

import com.alexandre.Java_Sql_CDA.db.Db;
import com.alexandre.Java_Sql_CDA.model.Category;
import com.alexandre.Java_Sql_CDA.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static Connection connection = Db.get_connection();

    public static Category save(Category category){

        Category newCategory = null;

        try {
            String request =  "INSERT INTO categories(name) VALUE (?)";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, category.getName());

            if(exists(category.getId())){
                throw new RuntimeException("Category Exists");
            }

            int rows = prepare.executeUpdate();

            if(rows > 0){
                newCategory = new Category();
                newCategory.setName(category.getName());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return newCategory;
    }

    public static Category findById(int id){

        Category getCategory = null;

        try {
            String request =  "SELECT id, name FROM categories WHERE id = ?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getCategory = new Category();
                getCategory.setId(resultSet.getInt("id"));
                getCategory.setName(resultSet.getString("name"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getCategory;
    }

    public static boolean exists(int id){

        boolean getCategory = false;

        try {
            String request =  "SELECT id, name FROM categories WHERE id = ?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getCategory = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getCategory;
    }

    public static List<Category> findAll(){

        List<Category> categories = new ArrayList<>();

        try {
            String request =  "SELECT id, name FROM categories";

            PreparedStatement prepare = connection.prepareStatement(request);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                Category getCategory = new Category();
                getCategory.setId(resultSet.getInt("id"));
                getCategory.setName(resultSet.getString("name"));
                categories.add(getCategory);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}
