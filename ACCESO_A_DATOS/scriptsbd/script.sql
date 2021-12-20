DROP database IF EXISTS concesionario; 
CREATE DATABASE concesionario;
use concesionario;
CREATE TABLE concesionario.EMPLEADO (
	IDEMP integer auto_increment NULL,
	NOMEMP VARCHAR(50) NOT NULL,
	APE1EMP VARCHAR(50) NOT NULL,
	APE2EMP VARCHAR(50) NULL,
	CONSTRAINT EMPLEADO_pk PRIMARY KEY (IDEMP)
);
CREATE TABLE concesionario.MARCA (
	IDMARCA integer auto_increment NOT NULL,	
	DESCMARCA VARCHAR(50) NOT NULL,
	CONSTRAINT MARCA_pk PRIMARY KEY (IDMARCA)
);
CREATE TABLE concesionario.COCHE (
	IDCOCHE integer auto_increment NOT NULL,
	IDMARCA integer NOT NULL,
	CODCOCHE VARCHAR(50) NULL,
	CONSTRAINT COCHE_pk PRIMARY KEY (IDCOCHE),
	CONSTRAINT COCHE_Uk UNIQUE KEY (CODCOCHE),
	CONSTRAINT COCHE_FK FOREIGN KEY (IDMARCA) REFERENCES concesionario.marca(IDMARCA)
);
CREATE TABLE concesionario.VENTA (
	IDEMP integer NOT NULL,
	IDCOCHE integer NOT NULL,
	IMPORTE integer NOT NULL,
	CONSTRAINT VENTA_pk PRIMARY KEY (IDCOCHE),
	CONSTRAINT VENTA_FK FOREIGN KEY (IDCOCHE) REFERENCES concesionario.coche(IDCOCHE),
	CONSTRAINT VENTA_FK_1 FOREIGN KEY (IDEMP) REFERENCES concesionario.empleado(IDEMP)
);
insert into empleado (NOMEMP, APE1EMP, APE2EMP) values ('Luis', 'Beltrán', 'Domínguez');
insert into empleado (NOMEMP, APE1EMP, APE2EMP) values ('Germán', 'Ruiz', 'Domínguez');
insert into empleado (NOMEMP, APE1EMP, APE2EMP) values ('Marta', 'González', 'Gutiérrez');
insert into empleado (NOMEMP, APE1EMP, APE2EMP) values ('Laura', 'Escudero', 'Benítez');
insert into empleado (NOMEMP, APE1EMP, APE2EMP) values ('Pedro', 'Rodríguez', 'Chacón');
insert into marca (DESCMARCA) values ('Ford');
insert into marca (DESCMARCA) values ('Seat');
insert into marca (DESCMARCA) values ('Kia');
insert into marca (DESCMARCA) values ('VolksWagen');
insert into marca (DESCMARCA) values ('Mercedes');
insert into coche (IDMARCA) values (1);
insert into coche (IDMARCA) values (1);
insert into coche (IDMARCA) values (1);
insert into coche (IDMARCA) values (2);
insert into coche (IDMARCA) values (2);
insert into coche (IDMARCA) values (2);
insert into coche (IDMARCA) values (3);
insert into coche (IDMARCA) values (3);
insert into coche (IDMARCA) values (4);
insert into coche (IDMARCA) values (4);
insert into coche (IDMARCA) values (5);
insert into venta values (1, 5, 35000);
insert into venta values (3, 4, 25000);
insert into venta values (3, 3, 22500);
insert into venta values (3, 2, 14500);	
insert into venta values (2, 1, 12500);	
insert into venta values (1, 10, 35000);
insert into venta values (3, 9, 25000);
insert into venta values (1, 8, 22500);
insert into venta values (2, 7, 18000);	
insert into venta values (1, 6, 15000);
commit;