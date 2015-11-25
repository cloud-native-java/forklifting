#!/usr/bin/env bash

set -e

redis=redis-sessions

cf services | grep ${redis} &&  echo "found ${redis}"  || cf cs rediscloud 30mb ${redis}

cf push