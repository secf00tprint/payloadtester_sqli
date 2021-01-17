#! /bin/bash
echo "[] Start mysql"
service mysql start
# Init Database
mysql -uroot < $(printenv scriptroot)root.sql
mysql -uroot -prootpwd < $(printenv scriptroot)startup.sql
./gradlew build
cd $(printenv docroot);java -jar build/libs/webapp_java_spring-0.0.1-SNAPSHOT.jar
