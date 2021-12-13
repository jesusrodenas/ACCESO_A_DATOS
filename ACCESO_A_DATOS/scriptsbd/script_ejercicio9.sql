DROP database IF EXISTS PruebaConexionBD; 
CREATE DATABASE PruebaConexionBD;
USE PruebaConexionBD;
create table ALUMNO (
	id INT PRIMARY KEY auto_increment,
	nombre VARCHAR(50),
	apellido1 VARCHAR(50),
	apellido2 VARCHAR(50),
	email VARCHAR(50),
	edad INT
);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Ingra', 'Cloke', 'Rouby', 'irouby0@wikipedia.org', 44);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Susie', 'Widdop', null, 'sbourget1@liveinternet.ru', 66);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Tracee', 'McQuilkin', null, 'tcuseck2@europa.eu', 84);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Cleve', 'Camilleri', 'Godden', 'cgodden3@gizmodo.com', 72);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Winona', 'Draper', null, 'warnaudi4@cpanel.net', 83);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Heywood', 'Waldram', 'Bullier', 'hbullier5@people.com.cn', 88);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Aron', 'Lydford', 'Dannel', 'adannel6@scientificamerican.com', 88);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Rycca', 'Farquarson', null, 'rivashechkin7@unc.edu', 39);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Eileen', 'Millar', null, 'eriddler8@constantcontact.com', 95);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Kristoforo', 'Schimpke', 'Whittuck', 'kwhittuck9@microsoft.com', 41);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Shanon', 'Pigden', 'Banat', 'sbanata@meetup.com', 74);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Nolana', 'Leney', 'Bartell', 'nbartellb@intel.com', 20);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Melba', 'Ringsell', 'Happs', 'mhappsc@nba.com', 42);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Felecia', 'Gard', 'Fullerd', 'ffullerdd@ox.ac.uk', 99);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Anselm', 'Mea', 'Bargh', 'abarghe@ycombinator.com', 95);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Inness', 'Hawyes', null, 'imountjoyf@skyrock.com', 62);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Ariadne', 'Scarlett', 'Sollowaye', 'asollowayeg@arstechnica.com', 73);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Meredith', 'Fowle', null, 'mwibberleyh@bizjournals.com', 62);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Tull', 'Tyres', 'Denisovo', 'tdenisovoi@cisco.com', 100);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Clari', 'Hawkwood', 'Muddiman', 'cmuddimanj@shutterfly.com', 42);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Dennie', 'Severs', 'Gainfort', 'dgainfortk@blogspot.com', 92);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Micheil', 'Brimfield', 'Dolley', 'mdolleyl@tripadvisor.com', 64);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Philippine', 'MacCague', 'Bister', 'pbisterm@freewebs.com', 42);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Hasheem', 'Olivet', null, 'hmcinneryn@sciencedaily.com', 31);
insert into ALUMNO (nombre, apellido1, apellido2, email, edad) values ('Lorrin', 'Sibbert', 'Cantos', 'lcantoso@un.org', 77);
CREATE TABLE pruebaconexionbd.MODULO (
	CODIGO VARCHAR(8) NULL,
	DESCRIPCION VARCHAR(60) NULL,
	CONSTRAINT MP_pk PRIMARY KEY (CODIGO)
);
INSERT INTO pruebaconexionbd.MODULO (CODIGO,DESCRIPCION)
	VALUES ('ACCDAT','Acceso a datos');
INSERT INTO pruebaconexionbd.MODULO (CODIGO,DESCRIPCION)
	VALUES ('PSP','Programación de Servicios y Procesos');
INSERT INTO pruebaconexionbd.MODULO (CODIGO,DESCRIPCION)
	VALUES ('DI','Desarrollo de Interfaces');
INSERT INTO pruebaconexionbd.MODULO (CODIGO,DESCRIPCION)
	VALUES ('SGEMP','Sistemas de Gestión Empresearial');
INSERT INTO pruebaconexionbd.MODULO (CODIGO,DESCRIPCION)
	VALUES ('EIE','Empresa e Iniciativa Emprendedora');
INSERT INTO pruebaconexionbd.MODULO (CODIGO,DESCRIPCION)
	VALUES ('PDMO','Programación de Dispositivos Móviles');
CREATE TABLE pruebaconexionbd.MATRICULA (
	ID INT NULL,
	CODIGO VARCHAR(8) NULL,
	CONSTRAINT MATRICULA_pk PRIMARY KEY (ID,CODIGO),
	CONSTRAINT MATRICULA_FK FOREIGN KEY (ID) REFERENCES pruebaconexionbd.alumno(id),
	CONSTRAINT MATRICULA_FK_1 FOREIGN KEY (CODIGO) REFERENCES pruebaconexionbd.MODULO(CODIGO)
);	
commit;