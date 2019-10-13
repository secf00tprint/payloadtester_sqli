#! /bin/bash
echo "[] Stopping mysql"
service mysql stop
echo "[] Setting permissions"
mysqld_safe --skip-grant-tables --skip-networking &
echo "[] Start mysql"
service mysql start
# Init Database
mysql -u root < /startup.sql
echo "[] Start apache2"
apache2-foreground
