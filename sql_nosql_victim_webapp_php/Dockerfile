FROM php:7.3.7-apache

LABEL maintainer="secf00tprint@gmail.com"


RUN apt-get update && apt-get install -y vim iputils-ping mysql*
RUN docker-php-ext-install pdo pdo_mysql mysqli

EXPOSE 80
