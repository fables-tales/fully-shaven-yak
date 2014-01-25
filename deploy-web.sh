#!/bin/bash
cd ggj2014-html/war
exec rsync --verbose --recursive --delete . hatamari:/var/www/hatamari.funandplausible.com/

