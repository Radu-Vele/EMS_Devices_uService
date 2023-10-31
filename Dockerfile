FROM openjdk:17-jdk-alpine
COPY target/DS2023_30442_Vele_Radu-Augustin_Devices_uService-0.0.1-SNAPSHOT.jar energy-ms-devices-us.jar

ENTRYPOINT ["java", "-jar", "energy-ms-devices-us.jar"]
