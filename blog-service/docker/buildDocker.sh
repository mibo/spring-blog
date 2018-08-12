#!/bin/bash

BUILD_TAG="latest"

if [[ ! -z $1 ]]; then
  echo "Use $1 as build tag..."
  BUILD_TAG=$1
fi

CMD="docker build -t mibo/pg-blog-service:$BUILD_TAG -f Dockerfile .."
echo "run $CMD"
$CMD

