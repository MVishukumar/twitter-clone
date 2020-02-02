--Connect to databse
mysql -u root -p

--Below commands will handle database creation, user creation and user privileges
create database twitter_clone;

use twitter_clone;


CREATE USER 'appuser'@'%' IDENTIFIED BY 'apppassword';
GRANT ALL PRIVILEGES ON twitter_clone.* TO 'appuser'@'%';


FLUSH PRIVILEGES;

COMMIT;