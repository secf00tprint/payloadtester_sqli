FLUSH PRIVILEGES;
update mysql.user set password=password('rootpwd') where user='root';
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
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("ch4rl35","eekeu6Sh");
