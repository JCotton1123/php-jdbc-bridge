#!/bin/sh

mkdir -p lib class tmp

wget \
  -O tmp/commons-daemon-1.0.15-bin.tar.gz \
  "http://apache.claz.org//commons/daemon/binaries/commons-daemon-1.0.15-bin.tar.gz"
tar -C lib/ \
  --strip-components=1 \
  -zxvf tmp/commons-daemon-1.0.15-bin.tar.gz \
  commons-daemon-1.0.15/commons-daemon-1.0.15.jar

javac -cp lib/commons-daemon-1.0.15.jar -d class/ src/*
jar cfe lib/pjbridge.jar Server -C class .
