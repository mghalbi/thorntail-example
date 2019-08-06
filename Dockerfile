FROM openjdk:jre-alpine

ADD keystore.jks /opt/keystore.jks

ADD target/thorntail-example-thorntail.jar /opt/thorntail.jar

EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar", "-Djava.net.preferIPv4Stack=true"]

