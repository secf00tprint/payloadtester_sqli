FROM gradle:jdk11

ENV docroot /usr/
ENV scriptroot /scripts/
LABEL maintainer="secf00tprint@gmail.com"

RUN apt-get update && apt-get install -y vim iputils-ping mariadb-server

EXPOSE 8080

RUN mkdir $scriptroot
COPY scripts/* $scriptroot
COPY webapp_java_spring/ $docroot 

WORKDIR $docroot
RUN chmod u+x $scriptroot/startup.sh
CMD ["bash","-c","${scriptroot}/startup.sh"]
