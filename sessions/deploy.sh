#!/usr/bin/env bash

set -e

redis=redis-bus

cf services | grep ${redis} &&  echo "found ${redis}"  || cf cs rediscloud 30mb ${redis}

cf push