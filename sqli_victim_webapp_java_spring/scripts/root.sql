FLUSH PRIVILEGES;
update mysql.user set password=password('rootpwd') where user='root';
-- FLUSH PRIVILEGES;
-- update mysql.user set plugin='mysql_native_password' where user='root';
-- FLUSH PRIVILEGES;
