/*
 * MySQL Database Initialization Script
 * 
 */
create database `productdb`;
GRANT ALL PRIVILEGES ON `productdb`.* TO 'user'@'%';
-- Create data 
use productdb;
create table `Products` (`ProductID` int NOT NULL AUTO_INCREMENT, `ProductName` VARCHAR(255), `Price` DECIMAL(5,2), PRIMARY KEY (`ProductID`));
insert into `Products` (`ProductName`, `Price`) values ("Product A", 70.1);
insert into `Products` (`ProductName`, `Price`) values ("Product B", 100.2);
insert into `Products` (`ProductName`, `Price`) values ("Product C", 1.2);
