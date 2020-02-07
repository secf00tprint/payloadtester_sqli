docker build -t sql_victim_lamp .
docker run -p 127.0.0.1:8782:80 -d sql_victim_lamp
