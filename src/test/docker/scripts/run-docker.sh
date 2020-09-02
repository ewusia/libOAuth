#!/bin/bash
SCRIPT_RUN_DIR=$(dirname $0)
CONTAINER_NAME=db-inz

cd $SCRIPT_RUN_DIR/..

echo "Deploying build this script is in $SCRIPT_RUN_DIR, current work dir is $PWD"

echo "Stopping existing containers"
docker-compose --project-name $CONTAINER_NAME down

echo "Starting containers"
docker-compose --project-name $CONTAINER_NAME up -d


