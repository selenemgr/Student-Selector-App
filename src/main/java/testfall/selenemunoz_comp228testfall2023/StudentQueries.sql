create database SeleneMunoz_COMP228TestFall2023;
use SeleneMunoz_COMP228TestFall2023;

CREATE TABLE Students (
   studentID int auto_increment primary key,
   firstName varchar (255),
   lastName varchar (255),
   address varchar (255),
   city varchar(255),
   province varchar(255),
   postalCode varchar(255)
);

insert into Students(firstName,lastName,address,city,province,postalCode) 
	values('Sam', 'Malone', '10 Somewhere Road', 'Toronto','ON','M1Y2H2');
insert into Students(firstName,lastName,address,city,province,postalCode) 
	values('Vince', 'Feds', '10 Somewhere Road', 'Vaughan','ON','L4K0J5');
insert into Students(firstName,lastName,address,city,province,postalCode) 
	values('Chris', 'Weeks', '10 Somewhere Road', 'Oshawa','ON','L1L0J4');
