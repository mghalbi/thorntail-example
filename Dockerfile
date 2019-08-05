FROM openjdk:jre-alpine

ADD keystore.jks /opt/keystore.jks

ENV CONNECTION_DASH_URL='jdbc:mysql://db:3307/test?useSSL=false'

ADD target/thorntail-example-thorntail.jar /opt/thorntail.jar

EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar", "-Djava.net.preferIPv4Stack=true -Dswarm.datasources.data-sources.someDS.connection-url=$CONNECTION_DASH_URL"]
