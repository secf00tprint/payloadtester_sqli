#! /bin/bash
echo "[] Start mysql"
service mysql start
# Init Database
mysql -uroot < /startup.sql
echo "[] Start apache2"
apache2-foreground

