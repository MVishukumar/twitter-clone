--Run mysql docker
docker run --name m-mysql -p 3306:3306 -h localhost -e MYSQL_ROOT_PASSWORD=root -d mysql:latest

--Enter into the docker mysql shell
docker exec -it m-mysql bash

--Connect to databse
mysql -u root -p

--Below commands will handle database creation, user creation and user privileges
create database twitter_clone;

use twitter_clone;


CREATE USER 'appuser'@'%' IDENTIFIED BY 'apppassword';
GRANT ALL PRIVILEGES ON twitter_clone.* TO 'appuser'@'%';


FLUSH PRIVILEGES;

COMMIT;