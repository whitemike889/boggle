#!/bin/bash

ARGS="$@"
echo mvn -P server exec:java "\"-Dexec.args=${ARGS}\""
mvn -P server exec:java "\"-Dexec.args=${ARGS}\""
