CREATE DATABASE IF NOT EXISTS java CHARSET utf8mb4;

USE java;

CREATE TABLE IF NOT EXISTS categories(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) UNIQUE NOT NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS roles(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) UNIQUE NOT NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    email VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    role_id INT,
    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES roles(id)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tasks(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    content VARCHAR(255) NOT NULL,
    creation_date DATETIME DEFAULT NOW(),
    end_date DATETIME,
    `status` TINYINT(1) DEFAULT 0,
    user_id INT,
    CONSTRAINT FK_tasks_users FOREIGN KEY (user_id) REFERENCES users(id)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS task_category(
	category_id INT,
    task_id INT,
    PRIMARY KEY(category_id, task_id),
    CONSTRAINT FK_task_category__categories FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT FK_task_category__tasks FOREIGN KEY (task_id) REFERENCES tasks(id)
)ENGINE=InnoDB;

INSERT INTO roles(`name`) VALUES
("USER"),("CLIENT"),("ADMIN");

INSERT INTO categories(`name`) VALUES
("Travail"),("Perso"),("Cinema"),
("Sport"),("Musique"),("Cuisine");



