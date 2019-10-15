FROM adoptopenjdk/openjdk11:alpine-slim

RUN addgroup -g 1001 -S app && adduser -u 1001 -S app -G app

VOLUME /Switch