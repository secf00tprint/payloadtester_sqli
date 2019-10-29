/*
 * MySQL Database Initialization Script
 * 
 */

-- *******************************************************
-- Specific Information Disclosure example: specific table
-- *******************************************************
CREATE DATABASE `productdb`;
GRANT ALL PRIVILEGES ON `productdb`.* TO 'user'@'%';
USE productdb;
CREATE TABLE `Products` (`ProductID` int NOT NULL AUTO_INCREMENT, `ProductName` VARCHAR(255), `Price` DECIMAL(5,2), PRIMARY KEY (`ProductID`));
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product A", 70.1);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product B", 100.2);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product C", 1.2);

-- *******************************************************
-- Specific Information Disclosure example: Form Steps
-- *******************************************************
CREATE DATABASE `capabilitiesdb`;
GRANT ALL PRIVILEGES ON `capabilitiesdb`.* TO 'user'@'%';
USE capabilitiesdb;
CREATE TABLE `Users` (
    `UserID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `Givenname` VARCHAR(50),
    `Surname` VARCHAR(50),
    `Username` VARCHAR(255),
    `Password` VARCHAR(255),
    `AdminRights` BOOLEAN
);
-- random names generated
INSERT INTO `Users` (`UserID`,`Givenname`,`Surname`,`Username`,`Password`,`AdminRights`) VALUES (1, "Lucca","Hays","lucca","Bai2xoo0yi8Eizu", TRUE);
INSERT INTO `Users` (`UserID`,`Givenname`,`Surname`,`Username`,`Password`,`AdminRights`) VALUES (2, "Krista","Stamp","krista","UChee8ul", FALSE);
INSERT INTO `Users` (`UserID`,`Givenname`,`Surname`,`Username`,`Password`,`AdminRights`) VALUES (3, "Carinna","Logan","carinna","Jah4iyae", FALSE);

CREATE TABLE `Capabilities` (
    `CapabilityID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `Description` VARCHAR(255),
    `UserID` INT,
     CONSTRAINT
     FOREIGN KEY (`UserID`)
        REFERENCES `Users` (`UserID`)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
INSERT INTO `Capabilities` (`Description`,`UserID`) VALUES ("communication skills",1);
INSERT INTO `Capabilities` (`Description`,`UserID`) VALUES ("charism",1);
INSERT INTO `Capabilities` (`Description`,`UserID`) VALUES ("charism",2);;
INSERT INTO `Capabilities` (`Description`,`UserID`) VALUES ("resilience",1);
INSERT INTO `Capabilities` (`Description`,`UserID`) VALUES ("resilience",3);

-- *******************************************************
-- Generic Information Disclosure example
-- *******************************************************

CREATE DATABASE `employeedb`;
GRANT ALL PRIVILEGES ON `employeedb`.* TO 'user'@'%';
USE employeedb;

CREATE TABLE `Timesheets` (`EmployeeID` int NOT NULL AUTO_INCREMENT, `EmployeeName` VARCHAR(255) NOT NULL, `Date` DATE NOT NULL , PRIMARY KEY (`EmployeeID`));
INSERT INTO `Timesheets` (`EmployeeName`, `Date`) VALUES ("Thoko", STR_TO_DATE('August 10 2019', '%M %d %Y'));
INSERT INTO `Timesheets` (`EmployeeName`, `Date`) VALUES ("Luise", STR_TO_DATE('August 13 2019', '%M %d %Y'));
INSERT INTO `Timesheets` (`EmployeeName`, `Date`) VALUES ("Edmund", STR_TO_DATE('September 13 2019', '%M %d %Y'));

CREATE TABLE `Thoko-1-2019-08-10` (`Topic` VARCHAR(255),`JobDetails` VARCHAR(255),`DayRate` DECIMAL (7,2),`WorkingHours` DECIMAL(5,2));
INSERT INTO `Thoko-1-2019-08-10` (`Topic`, `JobDetails`,`DayRate`,`WorkingHours`) VALUES ("Topic 1", "Job Details 1",2000,8.2);
CREATE TABLE `Luise-2-2019-08-13` (`Topic` VARCHAR(255),`JobDetails` VARCHAR(255),`DayRate` DECIMAL (7,2),`WorkingHours` DECIMAL(5,2));
INSERT INTO `Luise-2-2019-08-13` (`Topic`, `JobDetails`,`DayRate`,`WorkingHours`) VALUES ("Topic 1", "Job Details 1",2400,6);
CREATE TABLE `Edmund-3-2019-09-13` (`Topic` VARCHAR(255),`JobDetails` VARCHAR(255),`DayRate` DECIMAL (7,2),`WorkingHours` DECIMAL(5,2));
INSERT INTO `Edmund-3-2019-09-13` (`Topic`, `JobDetails`,`DayRate`,`WorkingHours`) VALUES ("Topic 1", "Job Details 1",1900,4);

-- Create Login Data to show inf disclosure
CREATE TABLE `Users` (`UserID` int NOT NULL AUTO_INCREMENT, `User` VARCHAR(255), `Password` VARCHAR(255), `AdminRights` BOOLEAN, PRIMARY KEY (`UserID`));
INSERT INTO `Users` (`User`,`Password`,`AdminRights`) VALUES ("admin","admin", TRUE);
INSERT INTO `Users` (`User`,`Password`,`AdminRights`) VALUES ("kariuki","mypassword", FALSE);

-- *******************************************************
-- Login SQLi     example
-- File Inclusion example
-- *******************************************************
CREATE DATABASE `cmsuserdb`;
GRANT ALL PRIVILEGES ON `cmsuserdb`.* TO 'user'@'%';
GRANT FILE           ON *.*           TO 'user'@'%';
USE cmsuserdb;
CREATE TABLE `CMSUsers` (`UserID` int NOT NULL AUTO_INCREMENT, `User` VARCHAR(255), `Password` VARCHAR(255), PRIMARY KEY (`UserID`));
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("admin","admin");
INSERT INTO `CMSUsers` (`User`,`Password`) VALUES ("tola","mypass");
