package com.alexandre.Java_Sql_CDA.repository;

import com.alexandre.Java_Sql_CDA.db.Db;
import com.alexandre.Java_Sql_CDA.model.Role;
import com.alexandre.Java_Sql_CDA.model.User;
import com.sun.source.tree.UsesTree;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static Connection connection = Db.get_connection();

    public static User save(User user){

        User newUser = null;

        try {
            String request =  "INSERT INTO users(firstname, lastname, email, password) VALUE (?,?,?,?)";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, user.getFirstname());
            prepare.setString(2, user.getLastname());
            prepare.setString(3, user.getEmail());
            prepare.setString(4, user.getPassword());

            if(exists(user.getEmail())){
                throw new RuntimeException("Email invalid");
            }

            int numRows = prepare.executeUpdate();

            if(numRows > 0){
                newUser = new User();
                newUser.setFirstname(user.getFirstname());
                newUser.setLastname(user.getLastname());
                newUser.setEmail(user.getEmail());
                newUser.setPassword(user.getPassword());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return newUser;
    }

    public static User findByEmail(String email){

        User getUser = null;

        try {
            String request =  "SELECT u.id, u.firstname, u.lastname, u.email, r.id AS \"RoleId\", r.name FROM users AS u"+
                    "INNER JOIN roles AS r ON r.id = u.role_id WHERE u.email=?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, email);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getUser = new User();
                getUser.setId(resultSet.getInt("id"));
                getUser.setFirstname(resultSet.getString("firstname"));
                getUser.setLastname(resultSet.getString("lastname"));
                getUser.setEmail(resultSet.getString("email"));
                Role role = new Role(resultSet.getString("name"));
                role.setId(resultSet.getInt("RoleId"));
                getUser.setRole(role);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getUser;
    }

    public static User findById(int id){

        User getUser = null;

        try {
            String request =  "SELECT u.id, u.firstname, u.lastname, u.email, r.id AS \"RoleId\", r.name FROM users AS u"+
                    "INNER JOIN roles AS r ON r.id = u.role_id WHERE u.id=?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setInt(1, id);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getUser = new User();
                getUser.setId(resultSet.getInt("id"));
                getUser.setFirstname(resultSet.getString("firstname"));
                getUser.setLastname(resultSet.getString("lastname"));
                getUser.setEmail(resultSet.getString("email"));
                getUser.setPassword(resultSet.getString("password"));
                Role role = new Role(resultSet.getString("name"));
                role.setId(resultSet.getInt("RoleId"));
                getUser.setRole(role);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getUser;
    }

    public static boolean exists(String email){

        boolean getUser = false;

        try {
            String request =  "SELECT id FROM users WHERE email = ?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, email);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                getUser = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getUser;
    }

    public static List<Object> findAll(){

        List<Object> users = new ArrayList<>();

        try {
            String request =  "SELECT u.id, u.firstname, u.lastname, u.email, r.id AS \"RoleId\", r.name FROM users AS u"+
                    "INNER JOIN roles AS r ON r.id = u.role_id";

            PreparedStatement prepare = connection.prepareStatement(request);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()){
                User getUser = new User();
                getUser.setId(resultSet.getInt("id"));
                getUser.setFirstname(resultSet.getString("firstname"));
                getUser.setLastname(resultSet.getString("lastname"));
                getUser.setEmail(resultSet.getString("email"));
                Role role = new Role(resultSet.getString("name"));
                role.setId(resultSet.getInt("RoleId"));
                getUser.setRole(role);
                users.add(getUser);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static User update(User user, String email){
        User oldUser = findByEmail(email);
        try {
            String request =  "UPDATE users SET firstname=?, lastname=?, email=?, role_id=(SELECT id FROM roles WHERE name=?) password=? WHERE id =?";

            PreparedStatement prepare = connection.prepareStatement(request);

            prepare.setString(1, user.getFirstname());
            prepare.setString(2, user.getLastname());
            prepare.setString(3, user.getEmail());
            prepare.setString(4, user.getPassword());
            prepare.setString(5, user.getRole().getName());
            prepare.setInt(6, oldUser.getId());

            int rows = prepare.executeUpdate();

            if(rows > 0){
                oldUser.setFirstname(user.getFirstname());
                oldUser.setLastname(user.getLastname());
                oldUser.setEmail(user.getEmail());
                oldUser.setPassword(user.getPassword());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return oldUser;
    }

    public static User saveWithRole(User user) {
        User newUser = null;
        try {
            String sql = "INSERT INTO users(firstname, lastname, email, password, roles_id)" +
                    "VALUE(?,?,?,?,(SELECT id FROM roles WHERE roles_name = ?))";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().getName());
            int nbrRows = preparedStatement.executeUpdate();
            if(nbrRows > 0){
                newUser = user;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }
}
