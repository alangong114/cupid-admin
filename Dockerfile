FROM maven:3-jdk-8-alpine
LABEL maintainer="gongmc"

WORKDIR /opt/cupid
ADD . /project
ENV TZ=Asia/Shanghai \
DB_USER="root" \
DB_PASSWORD="root"

RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone
# && mvn package -Pci
RUN cd /project && mvn package -Pci && mv target/dist/cupid/* /opt/cupid/ \
    && rm -rf /tmp/* && rm -rf ~/.m2

EXPOSE 8090

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/cupid/cupid-latest.jar","--spring.datasource.username=${DB_USER}","--spring.datasource.password=${DB_PASSWORD}"]
