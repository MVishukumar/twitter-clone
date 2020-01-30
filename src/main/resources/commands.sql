create database twitter_clone;

use twitter_clone;


CREATE USER 'appuser'@'%' IDENTIFIED BY 'apppassword';
GRANT ALL PRIVILEGES ON twitter_clone.* TO 'appuser'@'%';


FLUSH PRIVILEGES;