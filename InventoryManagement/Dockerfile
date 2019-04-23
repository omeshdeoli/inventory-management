FROM java:8-jdk-alpine

COPY ./target/InventoryManagement-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch InventoryManagement-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","InventoryManagement-0.0.1-SNAPSHOT.jar"]  