# fetch basic image
FROM maven:3.3.9-jdk-8

# application placed into /opt/app
RUN mkdir -p /opt/apps/pad
WORKDIR /opt/apps/pad

# selectively add the POM file and
# install dependencies
COPY pom.xml /opt/apps/pad
RUN mvn install

# rest of the project
COPY src /opt/apps/pad/src
RUN mvn package

# local application port
EXPOSE 8080

# execute it
CMD ["mvn", "exec:java"]
