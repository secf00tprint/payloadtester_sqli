FROM mariadb:10.4.6

LABEL maintainer="secf00tprint@gmail.com"

#ENV TZ="Europe/Berlin"
#ENV MYSQL_ALLOW_EMPTY_PASSWORD="no"
#ENV MYSQL_ROOT_PASSWORD="rootpwd"
#ENV MYSQL_USER='user'
#ENV MYSQL_PASSWORD='password'
#ENV MYSQL_DATABASE='productdb'

COPY scripts/ /
CMD /bin/bash -c "mysql -u root --password='rootpwd' < init.sql"
