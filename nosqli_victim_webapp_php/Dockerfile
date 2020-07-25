FROM php:7.3.7-apache

LABEL maintainer="secf00tprint@gmail.com"


RUN apt-get update && apt-get install -y vim iputils-ping libcurl4-openssl-dev pkg-config libssl-dev
RUN pecl install mongodb
RUN echo 'extension=mongodb.so' > $PHP_INI_DIR/conf.d/docker-php-ext-mongo.ini

EXPOSE 80
