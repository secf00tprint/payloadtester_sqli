FROM php:7.3.7-apache

LABEL maintainer="secf00tprint@gmail.com"

RUN apt-get update && apt-get install -y vim iputils-ping mariadb-server
RUN docker-php-ext-install pdo pdo_mysql mysqli

#COPY html/ /var/www/html/
EXPOSE 80

COPY scripts/* /
RUN chmod u+x /startup.sh
CMD ["/bin/bash","/startup.sh"]
