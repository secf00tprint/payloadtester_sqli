#!/bin/bash

DOCKER_COMPOSE_FILE=docker-compose.yml

if [[ "$OSTYPE" == "linux-gnu"* ]]
then
    if ! [[ "$(groups)" =~ (^|[[:space:]])docker($|[[:space:]]) ]]
    then
        echo "You need to be root to run this application"
        echo "Add docker group to user (for sudo to root)"
        exit
    fi
fi

# Check if docker, docker-machine and docker-compse is installed
command -v docker >/dev/null 2>&1 || { echo >&2 "docker needed (see https://www.docker.com)"; exit 1; }
command -v docker-compose >/dev/null 2>&1 || { echo >&2 "docker-compose needed (see https://docs.docker.com/compose/install/)"; exit 1; }


# Create a trap to clean up network at shut down
function cleanup {
    docker-compose -f $DOCKER_COMPOSE_FILE down
}
trap cleanup EXIT

# Start network
docker-compose -f $DOCKER_COMPOSE_FILE up --build
