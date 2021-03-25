#!/bin/bash

# Create user
groupadd -r TN --gid=999
useradd -r -g TN --uid=999 --home-dir=$WVDATA --shell=/bin/bash TN

# Install DEB packages
dpkg -i /tmp/TN.deb || exit 1
if [[ $ENABLE_GRPC == "true" ]]; then
  echo "Installing gRPC server"
  dpkg -i /tmp/grpc-server.deb || exit 1
fi

# Set permissions
chown -R TN:TN $WVDATA $WVLOG && chmod 777 $WVDATA $WVLOG

rm /etc/TN/TN.conf # Remove example config
cp /tmp/entrypoint.sh /usr/share/TN/bin/entrypoint.sh
chmod +x /usr/share/TN/bin/entrypoint.sh

# Cleanup
rm -rf /tmp/*
