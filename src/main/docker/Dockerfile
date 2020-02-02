FROM openjdk

VOLUME /tmp
ADD maven/twitter-clone-0.0.1-SNAPSHOT.jar myapp.jar
RUN sh -c 'touch /myapp.jar'
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar","/myapp.jar"]