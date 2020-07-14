FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'rootpwd';
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';

-- ****************************
-- Local File Inclusion example
-- ****************************
CREATE DATABASE `cmsuserdb`;
GRANT ALL PRIVILEGES ON `cmsuserdb`.* TO 'user'@'localhost';
GRANT FILE           ON *.*           TO 'user'@'localhost';
USE cmsuserdb;
CREATE TABLE `CMSUsers` (`UserID` int NOT NULL AUTO_INCREMENT, `User` VARCHAR(255), `Password` VARCHAR(255), PRIMARY KEY (`UserID`));
-- Create data
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("admin","admin");
