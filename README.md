Image of this application is also available on Docker Hub you can follow below steps if you want to run that image.

1) Create a folder.
2) Create a docker-compose.yml file in that folder.
3) Paste below data in that docker-compose.yml file and save.

***************************************************************************************************************************
version: '3.8'

services:
  mysql:
    image: mysql:latest
    container_name: mysql-container
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: Rushimehta71@
      MYSQL_DATABASE: GroceryData
    ports:
      - "3307:3306"  # Changed from 3306 to 3307 to avoid conflicts
    networks:
      - grocery-network
    volumes:
      - mysql-data:/var/lib/mysql

  app:
    image: rushimehta71/grocery:latest  # Using the pre-built image
    container_name: grocery-app
    restart: always
    depends_on:
      - mysql
    environment:
      MYSQL_HOST: mysql
      MYSQL_PORT: 3306  # Internal MySQL container port (unchanged)
    ports:
      - "8080:8080"
    networks:
      - grocery-network

networks:
  grocery-network:

volumes:
  mysql-data:
*********************************************************************************************************

4) Now pull this app image from  docker hub  "docker pull rushimehta71/grocery:latest"
5) Run "docker-compose up"  Your application will be up

*********************************************************************************************************
Second way to Run App

1) Pull this code from git
2) In your docker got to the project path and just run "docker-compose up -d --build" your app will be up.
