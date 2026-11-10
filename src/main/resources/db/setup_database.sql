create database campusworkshop;

create user 'campusworkshop'@'localhost' identified by 'workshop123';
grant all privileges on campusworkshop.* to 'campusworkshop'@'localhost';