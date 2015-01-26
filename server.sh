#!/bin/bash

ARGS="$@"
echo mvn --offline -P server exec:java "\"-Dexec.args=${ARGS}\""
mvn --offline -P server exec:java "\"-Dexec.args=${ARGS}\""
