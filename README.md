# Twitter-Clone
Spring Boot Project to create clone of [Twitter.com](http://Twitter.com)

This project was created so that it can be used as a reference to quickly create Spring Boot Project Web Application.

### Tech
This project is built using below technologies:
* [Docker](https://www.docker.com/)
* [MySql](https://www.mysql.com/) Database 
* [Thymeleaf](https://www.thymeleaf.org/) Template Engine
* [Jasypt](http://www.jasypt.org/) encryption.
* [Mapstruct](https://mapstruct.org/) for mapping Java Beans.
* [Bootstrap](https://getbootstrap.com/) for User Interface
* [Project Lombok](https://projectlombok.org/)
* [Hibernate](https://hibernate.org/)

### Installtion
This project depends on running mysql instance.
Execute below statements before you run this application.
```sh
$ create database twitter_clone;
$ use twitter_clone;

$ CREATE USER 'appuser'@'%' IDENTIFIED BY 'apppassword';
$ GRANT ALL PRIVILEGES ON twitter_clone.* TO 'appuser'@'%';

$ FLUSH PRIVILEGES;
```

### Development
Please feel free to submit a pull request.

**Free Software, Hell Yeah!**
