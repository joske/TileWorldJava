FROM maven

RUN microdnf upgrade

RUN microdnf install -y xorg-x11-server-Xvfb libXrender libXtst

WORKDIR /app

COPY . .

RUN mvn install

CMD ["java", "-jar", "target/TileWorld-1.0-SNAPSHOT.jar"]