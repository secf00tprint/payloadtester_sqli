#! /bin/bash
#echo "[] Stopping mysql"
#service mysql stop

#echo "[] Setting permissions"
#mysqld_safe --skip-grant-tables --skip-networking &
echo "[] Start mysql"
service mysql start
# Init Database
mysql -uroot < root.sql
mysql -uroot -prootpwd < startup.sql
#mysqld_safe
./gradlew build
java -jar build/libs/webapp_java_spring-0.0.1-SNAPSHOT.jar

# while :; do sleep 1; done
