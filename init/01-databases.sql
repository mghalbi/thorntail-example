CREATE DATABASE IF NOT EXISTS `keycloak`;

# create keycloak user and grant rights
CREATE USER 'keycloak'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'keycloak'@'%';
