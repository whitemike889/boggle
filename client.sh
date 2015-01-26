#!/bin/bash

mvn --offline -P client exec:java -Dexec.args="$@"
