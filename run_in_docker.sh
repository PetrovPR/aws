#!/bin/sh
mvn clean install
docker build -t http_server .
docker run -p 8080:8080 http_server
