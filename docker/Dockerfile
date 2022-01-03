FROM debian:buster-slim
ARG WAVES_LOG_LEVEL="INFO"
ARG WAVES_HEAP_SIZE="2g"
ARG WAVES_NETWORK="mainnet"
ARG ENABLE_GRPC="true"

ENV WAVES_LOG_LEVEL=$WAVES_LOG_LEVEL
ENV WAVES_HEAP_SIZE=$WAVES_HEAP_SIZE
ENV WAVES_NETWORK=$WAVES_NETWORK
ENV ENABLE_GRPC=$ENABLE_GRPC

COPY build-scripts /tmp/

# Additional dependencies
RUN /bin/bash /tmp/setup-deps.sh

# Node DEB files
ENV WVDATA=/var/lib/tn
ENV WVLOG=/var/log/tn
COPY target /tmp/
RUN /bin/bash /tmp/setup-node.sh

EXPOSE 6861 6860 6560 6862 6870
VOLUME /var/lib/tn
VOLUME /var/log/tn
VOLUME /usr/share/tn/lib/plugins
WORKDIR /var/lib/tn

STOPSIGNAL SIGINT
ENTRYPOINT ["/usr/share/tn/bin/entrypoint.sh"]
