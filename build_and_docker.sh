#!/bin/bash

./gradlew clean build -x test
cd docker
docker-compose up --build
