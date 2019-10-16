docker build -t lamp .
docker run -p 127.0.0.1:8782:80 -d lamp
