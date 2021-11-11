# selecciono la base de datos sobre la cual voy a hacer modificaciones
USE banco;

INSERT INTO Cliente (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,fecha_nac) VALUES ("Alvarez", "Fermin", "DNI", "1111111", "Direccion Fermin","239222222", "2000/10/06");
INSERT INTO Cliente  (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,fecha_nac) VALUES ("Alvarez", "Hernan", "DNI", "2222222", "Direccion Hernan","2391234", "2002/12/03");
INSERT INTO Cliente (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,fecha_nac) VALUES ("Alvarez", "Daniel", "DNI", "3333333", "Direccion Daniel","23944332", "2001/11/02");
INSERT INTO Cliente (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,fecha_nac) VALUES ("Gonzalo", "Selene", "DNI", "4444444", "Direccion Selene","2392234234", "2003/11/08");


INSERT INTO Caja_Ahorro(CBU,saldo) VALUES (123,1250);
INSERT INTO Caja_Ahorro(CBU,saldo) VALUES (785,14000);
INSERT INTO Caja_Ahorro(CBU,saldo) VALUES (245,25000);
INSERT INTO Caja_Ahorro(CBU,saldo) VALUES (424,250);

INSERT INTO Cliente_CA VALUES (1,1);
INSERT INTO Cliente_CA VALUES (2,2);
INSERT INTO Cliente_CA VALUES (3,3);
INSERT INTO Cliente_CA VALUES (4,4);

INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();

-- Para Ventanilla
INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();

-- Para ATM
INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();
INSERT INTO Caja VALUES ();
INSERT INTO Caja(cod_caja) VALUES(100);

INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/9/19", "00:01:20", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/4/23", "00:11:05", 1200);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/9/09", "00:21:08", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2010/5/19", "01:01:06", 120010);
INSERT INTO Debito  VALUES (1,"Mi primer debito", 1, 1);
INSERT INTO Debito  VALUES (2,"Mi segundo debito", 2, 2);
INSERT INTO Debito  VALUES (3,"Mi tercer debito", 3, 3);
INSERT INTO Debito  VALUES (4,"Mi cuarto debito", 4, 4);

INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/9/19", "00:01:20", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/4/23", "00:11:05", 1200);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/9/09", "00:21:08", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2010/5/19", "01:01:06", 120010);
INSERT INTO Transaccion_por_caja VALUES (5, 1);
INSERT INTO Transaccion_por_caja VALUES (6, 2);
INSERT INTO Transaccion_por_caja VALUES (7, 3);
INSERT INTO Transaccion_por_caja VALUES (8, 4);
INSERT INTO Transferencia VALUES (5, 1, 1, 2);
INSERT INTO Transferencia VALUES (6, 2, 2, 3);
INSERT INTO Transferencia VALUES (7, 3, 3, 4);
INSERT INTO Transferencia VALUES (8, 4, 4, 1);





INSERT INTO Transaccion (fecha,hora,monto) VALUES ("2021/9/19", "00:01:20", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2021/4/23", "00:11:05", 1200);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2021/9/09", "00:21:08", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2010/5/19", "01:01:06", 120010);
INSERT INTO Transaccion_por_caja VALUES (9, 1);
INSERT INTO Transaccion_por_caja VALUES (10, 2);
INSERT INTO Transaccion_por_caja VALUES (11, 3);
INSERT INTO Transaccion_por_caja VALUES (12, 4);
INSERT INTO Extraccion VALUES (9, 1, 1);
INSERT INTO Extraccion VALUES (10, 2, 2);
INSERT INTO Extraccion VALUES (11, 3, 3);
INSERT INTO Extraccion VALUES (12, 4, 4);



INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2021/9/19", "00:01:20", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2021/4/23", "00:11:05", 1200);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2021/9/09", "00:21:08", 450);
INSERT INTO Transaccion (fecha,hora,monto) VALUES ( "2010/5/19", "01:01:06", 120010);
INSERT INTO Transaccion_por_caja VALUES (13, 1);
INSERT INTO Transaccion_por_caja VALUES (14, 2);
INSERT INTO Transaccion_por_caja VALUES (15, 3);
INSERT INTO Transaccion_por_caja VALUES (16, 4);
INSERT INTO Deposito VALUES (13,1);
INSERT INTO Deposito VALUES (14,2);
INSERT INTO Deposito VALUES (15,3);
INSERT INTO Deposito VALUES (16,4);


INSERT INTO Ciudad VALUES (6339, "Salliquelo");
INSERT INTO Ciudad VALUES (8000, "Bahia Blanca");
INSERT INTO Ciudad VALUES (6500, "Tres lomas");


INSERT INTO Sucursal (nombre,direccion,telefono,horario,cod_postal) VALUES  ("Sucursal 1","Alem 11", "239244444", "09hs a 15hs", 6339);
INSERT INTO Sucursal (nombre,direccion,telefono,horario,cod_postal) VALUES ("Sucursal Bahia","Alem 1001", "291446545", "09hs a 15hs", 8000);
INSERT INTO Sucursal (nombre,direccion,telefono,horario,cod_postal) VALUES ("Sucursal Tres lomas","Stroeder 450", "2392444242", "09hs a 12hs", 6500);

INSERT INTO Empleado (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,cargo,password,nro_suc) VALUES ("Alvarez","Fermin","DNI",42461930,"Panama 1550", "2392409941","Administrador",md5("Fermin300"),1);
INSERT INTO Empleado (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,cargo,password,nro_suc) VALUES ("Gonzalo","Selene","DNI",41414141,"Panama 100", "23922521","Ejecutivo",md5("Selene123"),2);
INSERT INTO Empleado (apellido,nombre,tipo_doc,nro_doc,direccion,telefono,cargo,password,nro_suc) VALUES ("Rogelio","Miranda","DNI",12312333,"Cordoba 100", "232940400","Limpieza",md5("limpiando123"),3);


INSERT INTO Plazo_Fijo (capital,tasa_interes,interes,fecha_inicio,fecha_fin,nro_suc) VALUES (1550.50, 3.15, 5.28, "2018/4/12", "2019/9/19", 1);
INSERT INTO Plazo_Fijo (capital,tasa_interes,interes,fecha_inicio,fecha_fin,nro_suc) VALUES (4550.20, 3.2, 7.28, "2017/5/6", "2017/10/10", 2);
INSERT INTO Plazo_Fijo (capital,tasa_interes,interes,fecha_inicio,fecha_fin,nro_suc) VALUES (6800.90, 4.15, 8.28, "2018/8/3", "2019/4/4", 3);




INSERT INTO Tasa_Plazo_Fijo VALUES (30, 1000.00, 4500.00, 45.00);
INSERT INTO Tasa_Plazo_Fijo VALUES (30, 1000.00, 1500.00, 15.00);
INSERT INTO Tasa_Plazo_Fijo VALUES (30, 8000.00, 3500.00, 10.00);

INSERT INTO Plazo_Cliente VALUES (1, 1);
INSERT INTO Plazo_Cliente VALUES (2, 2);
INSERT INTO Plazo_Cliente VALUES (3, 3);

INSERT INTO Prestamo (fecha,cant_meses,monto,tasa_interes,interes,valor_cuota,nro_cliente,legajo) VALUES ("2015/12/12", 4, 12000.50, 4.5, 8, 2000.00, 1, 1);
INSERT INTO Prestamo (fecha,cant_meses,monto,tasa_interes,interes,valor_cuota,nro_cliente,legajo) VALUES ("2016/04/12", 2, 20000.5, 5.4, 8, 1000.00, 2, 2);
INSERT INTO Prestamo (fecha,cant_meses,monto,tasa_interes,interes,valor_cuota,nro_cliente,legajo) VALUES ("2017/12/4", 7, 2400.67, 4.5, 8, 240.00, 3, 3);


INSERT INTO Pago VALUES (1,"2016/01/02", "2017/01/01", 1);
INSERT INTO Pago VALUES (2,"2017/01/02", "2017/01/01", 2);
INSERT INTO Pago VALUES (3,"2018/01/02","2018/01/01", 3);


INSERT INTO Tasa_Prestamo VALUES (31,1200.00,1300.00, 4.1 );
INSERT INTO Tasa_Prestamo VALUES (32,2300.04,2500.00, 5.6 );
INSERT INTO Tasa_Prestamo VALUES (33,3400.05,3600.00, 6.5 );


INSERT INTO Tarjeta (PIN,CVT,fecha_venc,nro_cliente,nro_ca) VALUES (md5("Tarjeta1PIN"),md5("Tarjeta1CVT"),"2018/01/02", 1, 1);
INSERT INTO Tarjeta (PIN,CVT,fecha_venc,nro_cliente,nro_ca) VALUES (md5("Tarjeta2PIN"),md5("Tarjeta2CVT"),"2019/01/02", 2, 2);
INSERT INTO Tarjeta (PIN,CVT,fecha_venc,nro_cliente,nro_ca) VALUES (md5("Tarjeta3PIN"),md5("Tarjeta3CVT"),"2014/01/02", 3, 3);


INSERT INTO Ventanilla VALUES (5, 1);
INSERT INTO Ventanilla VALUES (6, 2);
INSERT INTO Ventanilla VALUES (7, 3);

INSERT INTO ATM VALUES (8, "Direccion ATM 1", 6339);
INSERT INTO ATM VALUES (9, "Direccion ATM 2", 8000);
INSERT INTO ATM VALUES (10, "Direccion ATM 3", 6500);
INSERT INTO atm(cod_caja,direccion,cod_postal) VALUES(100,"Direccion ATM 4",6339);