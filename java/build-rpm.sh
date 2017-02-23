#!/bin/sh

version="0.2"

#yum install -y rpm-build

./build.sh
tmpsrc=$(mktemp -d)
mkdir -p $tmpsrc/pjbridge-${version}
cp -R lib $tmpsrc/pjbridge-${version}
tar Czcf $tmpsrc rpm/SOURCES/pjbridge-${version}.tar.gz pjbridge-${version}

rpmbuild \
  --define "version ${version}" \
  --define "_topdir $(pwd)/rpm" \
  --define "_tmppath $(pwd)/rpm/tmp" \
  --bb rpm/specfile
