package com.alexandre.Java_Sql_CDA.repository;

import com.alexandre.Java_Sql_CDA.db.Db;
import com.alexandre.Java_Sql_CDA.model.Category;
import com.alexandre.Java_Sql_CDA.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    private static Connection connection = Db.get_connection();

    public static Role save(Role role){

        Role newRole = null;

        try {
            String request =  "INSERT INTO roles(name) VALUE (?)";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, role.getName());

            if(exists(role.getId())){
                throw new RuntimeException("Category Exists");
            }

            int rows = prepare.executeUpdate();

            if(rows > 0){
                newRole = new Role();
                newRole.setName(role.getName());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return newRole;
    }

    public static Role findById(int id){

        Role getRole = null;

        try {
            String request =  "SELECT id, name FROM categories WHERE id = ?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getRole = new Role();
                getRole.setId(resultSet.getInt("id"));
                getRole.setName(resultSet.getString("name"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getRole;
    }

    public static boolean exists(int id){

        boolean getRole = false;

        try {
            String request =  "SELECT id, name FROM roles WHERE id = ?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getRole = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getRole;
    }

    public static List<Role> findAll(){

        List<Role> roles = new ArrayList<>();

        try {
            String request =  "SELECT id, name FROM roles";

            PreparedStatement prepare = connection.prepareStatement(request);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                Role getRole = new Role();
                getRole.setId(resultSet.getInt("id"));
                getRole.setName(resultSet.getString("name"));
                roles.add(getRole);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return roles;
    }
}
