FROM eclipse-temurin:17-jdk-alpine 
VOLUME /tmp 
COPY run.sh . 
COPY /target/springboot-blog-rest-api-0.0.1.jar springboot-blog-rest-api-0.0.1.jar 
ENTRYPOINT ["run.sh"]