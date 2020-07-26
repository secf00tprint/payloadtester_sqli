CREATE OR REPLACE USER 'user'@'localhost' IDENTIFIED BY 'password';
-- ****************************
-- SQL Injection example
-- ****************************
CREATE DATABASE `sqli_example`;
GRANT ALL PRIVILEGES ON `sqli_example`.* TO 'user'@'localhost'; 
GRANT FILE           ON *.*           TO 'user'@'localhost';
