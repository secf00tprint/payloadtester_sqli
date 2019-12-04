docker build -t victim_server_sqli_lamp .
docker run -p 127.0.0.1:8782:80 -d victim_server_sqli_lamp
