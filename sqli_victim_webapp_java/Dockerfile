FROM gradle:jdk11

ENV docroot /usr/src/
LABEL maintainer="secf00tprint@gmail.com"

RUN apt-get update && apt-get install -y vim iputils-ping 'mariadb-server=1:10.3.22-1ubuntu1'

EXPOSE 8080

COPY scripts/* /

WORKDIR $docroot
RUN chmod u+x /startup.sh
CMD ["/startup.sh"]
