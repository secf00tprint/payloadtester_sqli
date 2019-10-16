/*
 * MySQL Database Initialization Script
 * 
 */

-- ***************************************
-- Specific Information Disclosure example
-- ***************************************
CREATE DATABASE `productdb`;
GRANT ALL PRIVILEGES ON `productdb`.* TO 'user'@'%';
USE productdb;
CREATE TABLE `Products` (`ProductID` int NOT NULL AUTO_INCREMENT, `ProductName` VARCHAR(255), `Price` DECIMAL(5,2), PRIMARY KEY (`ProductID`));
-- Create data 
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product A", 70.1);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product B", 100.2);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product C", 1.2);

-- **************************************
-- Generic Information Disclosure example
-- **************************************

CREATE DATABASE `employeedb`;
GRANT ALL PRIVILEGES ON `employeedb`.* TO 'user'@'%';
USE employeedb;

CREATE TABLE `Timesheets` (`EmployeeID` int NOT NULL AUTO_INCREMENT, `EmployeeName` VARCHAR(255) NOT NULL, `Date` DATE NOT NULL , PRIMARY KEY (`EmployeeID`));
-- Create data
INSERT INTO `Timesheets` (`EmployeeName`, `Date`) VALUES ("Thoko", STR_TO_DATE('August 10 2019', '%M %d %Y'));
INSERT INTO `Timesheets` (`EmployeeName`, `Date`) VALUES ("Luise", STR_TO_DATE('August 13 2019', '%M %d %Y'));
INSERT INTO `Timesheets` (`EmployeeName`, `Date`) VALUES ("Edmund", STR_TO_DATE('September 13 2019', '%M %d %Y'));
CREATE TABLE `Thoko-1-2019-08-10` (`Topic` VARCHAR(255),`JobDetails` VARCHAR(255),`DayRate` DECIMAL (7,2),`WorkingHours` DECIMAL(5,2));
INSERT INTO `Thoko-1-2019-08-10` (`Topic`, `JobDetails`,`DayRate`,`WorkingHours`) VALUES ("Topic 1", "Job Details 1",2000,8.2);
CREATE TABLE `Luise-2-2019-08-13` (`Topic` VARCHAR(255),`JobDetails` VARCHAR(255),`DayRate` DECIMAL (7,2),`WorkingHours` DECIMAL(5,2));
INSERT INTO `Luise-2-2019-08-13` (`Topic`, `JobDetails`,`DayRate`,`WorkingHours`) VALUES ("Topic 1", "Job Details 1",2400,6);
CREATE TABLE `Edmund-3-2019-09-13` (`Topic` VARCHAR(255),`JobDetails` VARCHAR(255),`DayRate` DECIMAL (7,2),`WorkingHours` DECIMAL(5,2));
INSERT INTO `Edmund-3-2019-09-13` (`Topic`, `JobDetails`,`DayRate`,`WorkingHours`) VALUES ("Topic 1", "Job Details 1",1900,4);
-- Create Login Data
CREATE TABLE `Users` (`UserID` int NOT NULL AUTO_INCREMENT, `User` VARCHAR(255), `Password` VARCHAR(255), `AdminRights` BOOLEAN, PRIMARY KEY (`UserID`));
-- Create data
INSERT INTO `Users` (`User`,`Password`,`AdminRights`) VALUES ("admin","admin", TRUE);
INSERT INTO `Users` (`User`,`Password`,`AdminRights`) VALUES ("kariuki","mypassword", FALSE);

-- **********************
-- Login SQLi     example
-- File Inclusion example
-- **********************
CREATE DATABASE `cmsuserdb`;
GRANT ALL PRIVILEGES ON `cmsuserdb`.* TO 'user'@'%';
GRANT FILE           ON *.*           TO 'user'@'%';
USE cmsuserdb;
CREATE TABLE `CMSUsers` (`UserID` int NOT NULL AUTO_INCREMENT, `User` VARCHAR(255), `Password` VARCHAR(255), PRIMARY KEY (`UserID`));
-- Create data
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("admin","admin");
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("tola","mypass");
