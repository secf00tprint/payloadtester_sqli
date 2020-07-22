FROM gradle:jdk11

ENV docroot /usr/
LABEL maintainer="secf00tprint@gmail.com"

RUN apt-get update && apt-get install -y vim iputils-ping mariadb-server

EXPOSE 8080

COPY scripts/* $docroot
COPY webapp_java_spring/ $docroot 

WORKDIR $docroot
RUN chmod u+x $docroot/startup.sh
CMD ["bash","-c","${docroot}/startup.sh"]
