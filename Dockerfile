FROM maven:3.8.1-openjdk-17-slim AS build
RUN mkdir /home/cbook
COPY . /home/cbook
RUN cd /home/cbook && mvn package
RUN cp /home/cbook/target/*.jar cbook.jar
ENTRYPOINT [ "java","-jar","cbook.jar" ]
EXPOSE 8089