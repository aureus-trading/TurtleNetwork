#!/bin/bash

# Create user
groupadd -r tn --gid=999
useradd -r -g tn --uid=999 --home-dir=$WVDATA --shell=/bin/bash TN

# Install DEB packages
dpkg -i /tmp/tn.deb || exit 1
if [[ $ENABLE_GRPC == "true" ]]; then
  echo "Installing gRPC server"
  dpkg -i /tmp/tn-grpc-server.deb || exit 1
fi

# Set permissions
chown -R tn:tn $WVDATA $WVLOG && chmod 777 $WVDATA $WVLOG

rm /etc/tn/tn.conf # Remove example config
cp /tmp/entrypoint.sh /usr/share/tn/bin/entrypoint.sh
chmod +x /usr/share/tn/bin/entrypoint.sh

# Cleanup
rm -rf /tmp/*
