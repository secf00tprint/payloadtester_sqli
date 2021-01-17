#! /bin/bash
echo "[] Start mysql"
service mysql start
# Init Database
mysql -uroot < /root.sql
mysql -uroot -prootpwd < /startup.sql
./gradlew build
cd $(printenv docroot);java -jar build/libs/webapp_java_spring-0.0.1-SNAPSHOT.jar
