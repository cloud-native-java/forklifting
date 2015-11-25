#!/usr/bin/env bash

set -e

email=cf-sendgrid

cf services | grep ${email} &&  echo "found ${email}"  || cf cs sendgrid free  ${email}

cf push