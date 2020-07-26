FLUSH PRIVILEGES;
update mysql.user set password=password('rootpwd') where user='root';
