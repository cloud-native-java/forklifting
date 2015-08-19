#!/bin/bash
mvn clean install
cf cs sendgrid free cf-sendgrid 
cf push