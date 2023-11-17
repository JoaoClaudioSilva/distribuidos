FROM alpine:latest

RUN apk update && \
    apk add openjdk17 xorg-server fontconfig ttf-dejavu

COPY target/cliente-shaded.jar /app/cliente-shaded.jar

WORKDIR /app

CMD ["java", "-jar", "cliente-shaded.jar"]
