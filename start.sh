#!/bin/bash

# build jar file
./gradlew clean build

# ensure that docker-compose stopped
docker-compose stop

# start new deployment
docker-compose up -d