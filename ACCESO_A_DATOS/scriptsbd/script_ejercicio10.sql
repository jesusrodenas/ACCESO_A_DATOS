ALTER TABLE pruebaconexionbd.alumno ADD numesc integer(20) NULL;
UPDATE ALUMNO SET NUMESC = ID*2;
COMMIT;