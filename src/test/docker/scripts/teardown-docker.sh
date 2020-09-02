#!/bin/bash
SCRIPT_RUN_DIR=$(dirname $0)
CONTAINER_NAME=db-inz

cd $SCRIPT_RUN_DIR/..

echo "Stopping containers, script is in $SCRIPT_RUN_DIR, current work dir is $PWD"

echo "Stopping containers if running"
docker-compose --project-name $CONTAINER_NAME down

