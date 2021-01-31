CREATE OR REPLACE USER 'user'@'localhost' IDENTIFIED BY 'password';
-- ****************************
-- SQL Injection example
-- ****************************
CREATE DATABASE `sqli_example`;
GRANT ALL PRIVILEGES ON `sqli_example`.* TO 'user'@'localhost'; 
GRANT FILE           ON *.*           TO 'user'@'localhost';

-- Fill with example data
USE `sqli_example`;
CREATE TABLE `user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `password` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
                    );
INSERT INTO `user` (`username`,`password`) VALUES ('admin','4dm1n');
INSERT INTO `user` (`username`,`password`) VALUES ('user123','pass');
