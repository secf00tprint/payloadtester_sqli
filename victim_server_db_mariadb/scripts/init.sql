/*
 * MySQL Database Initialization Script
 * 
 */

-- ********************
-- Product SQLi example
-- ********************
CREATE DATABASE `productdb`;
GRANT ALL PRIVILEGES ON `productdb`.* TO 'user'@'%';
USE productdb;
CREATE TABLE `Products` (`ProductID` int NOT NULL AUTO_INCREMENT, `ProductName` VARCHAR(255), `Price` DECIMAL(5,2), PRIMARY KEY (`ProductID`));
-- Create data 
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product A", 70.1);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product B", 100.2);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product C", 1.2);

-- ******************
-- Login SQLi example
-- File Inclusion example
-- ****************** 
CREATE DATABASE `cmsuserdb`;
GRANT ALL PRIVILEGES ON `cmsuserdb`.* TO 'user'@'%';
GRANT FILE           ON *.*           TO 'user'@'%';
USE cmsuserdb;
CREATE TABLE `CMSUsers` (`UserID` int NOT NULL AUTO_INCREMENT, `User` VARCHAR(255), `Password` VARCHAR(255), PRIMARY KEY (`UserID`));
-- Create data
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("admin","admin");
