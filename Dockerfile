# fetch basic image
FROM maven:3.3.9-jdk-8

# application placed into /opt/app
RUN mkdir -p /opt/apps/pad
WORKDIR /opt/apps/pad

# selectively add the POM file and
# install dependencies
COPY pom.xml /opt/apps/pad
COPY src /opt/apps/pad/src
RUN mvn install

# local application port
EXPOSE 10000

# execute it
CMD ["java", "-jar", "target/pad-jar-with-dependencies.jar"]
