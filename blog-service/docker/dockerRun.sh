#!/bin/bash
docker run -it --rm --hostname blog-service -p 8080:8080 mibo/pg-blog-service
