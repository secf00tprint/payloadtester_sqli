-- update mysql.user set password=password('rootpwd') where user='root';
-- FLUSH PRIVILEGES;
-- update mysql.user set plugin='mysql_native_password' where user='root';
-- FLUSH PRIVILEGES;
-- SET PASSWORD = PASSWORD('rootpwd');
CREATE OR REPLACE USER 'user'@'localhost' IDENTIFIED BY 'password';
-- ****************************
-- SQL Injection example
-- ****************************
CREATE DATABASE `sqli_example`;
GRANT ALL PRIVILEGES ON `sqli_example`.* TO 'user'@'localhost'; 
GRANT FILE           ON *.*           TO 'user'@'localhost';
