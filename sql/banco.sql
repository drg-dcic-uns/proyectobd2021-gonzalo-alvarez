#Archivo batch (batalllas.sql) para la creaci�n de la 
#Base de datos del pr�ctico de SQL

# Creamos de la Base de Datos
CREATE DATABASE banco;

# selecciono la base de datos sobre la cual voy a hacer modificaciones
USE banco;

# CREACION DE TABLAS ENTIDADES -----------------------------------------------------------------------------------------------------------

CREATE TABLE Ciudad (
    cod_postal SMALLINT UNSIGNED NOT NULL, 
    nombre VARCHAR(45) NOT NULL, 
    
    CONSTRAINT pk_ciudad 
    PRIMARY KEY (cod_postal)
) ENGINE=InnoDB;

CREATE TABLE Sucursal (
    nro_suc SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    nombre VARCHAR(45) NOT NULL, 
    direccion VARCHAR(70) NOT NULL, 
    telefono VARCHAR(45) NOT NULL, 
    horario VARCHAR(30) NOT NULL, 
    cod_postal SMALLINT UNSIGNED NOT NULL, 
    
    CONSTRAINT pk_sucursal 
    PRIMARY KEY (nro_suc),

    CONSTRAINT FK_sucur_ubic
    FOREIGN KEY (cod_postal) REFERENCES Ciudad (cod_postal) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Empleado (
    legajo SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    apellido VARCHAR(45) NOT NULL, 
    nombre VARCHAR(45) NOT NULL, 
    tipo_doc VARCHAR(20) NOT NULL, 
    nro_doc INT UNSIGNED NOT NULL, 
    direccion VARCHAR(70) NOT NULL, 
    telefono VARCHAR(45) NOT NULL, 
    cargo VARCHAR(45) NOT NULL, 
    password VARCHAR(32) NOT NULL,
    nro_suc SMALLINT UNSIGNED NOT NULL,

    CONSTRAINT pk_empleado 
    PRIMARY KEY (legajo),

    CONSTRAINT FK_trabaja_en
    FOREIGN KEY (nro_suc) REFERENCES Sucursal (nro_suc) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Cliente (
    nro_cliente MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    apellido VARCHAR(45) NOT NULL, 
    nombre VARCHAR(45) NOT NULL, 
    tipo_doc VARCHAR(20) NOT NULL, 
    nro_doc INT UNSIGNED NOT NULL, 
    direccion VARCHAR(70) NOT NULL, 
    telefono VARCHAR(45) NOT NULL,  
    fecha_nac DATE NOT NULL,

    CONSTRAINT pk_cliente
    PRIMARY KEY (nro_cliente)
) ENGINE=InnoDB;

CREATE TABLE Plazo_Fijo (
    nro_plazo INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    capital DECIMAL(16,2) UNSIGNED NOT NULL, 
    tasa_interes DECIMAL(4,2) UNSIGNED NOT NULL, 
    interes DECIMAL(16,2) UNSIGNED NOT NULL, 
    fecha_inicio DATE NOT NULL, 
    fecha_fin DATE NOT NULL, 
    nro_suc SMALLINT UNSIGNED NOT NULL,


    CONSTRAINT pk_plazo_fijo
    PRIMARY KEY (nro_plazo),

    CONSTRAINT FK_Plazo_Suc
    FOREIGN KEY (nro_suc) REFERENCES Sucursal (nro_suc) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
#SHOW WARNINGS;

CREATE TABLE Tasa_Plazo_Fijo (
    periodo SMALLINT UNSIGNED NOT NULL, 
    monto_inf DECIMAL(16,2) UNSIGNED NOT NULL, 
    monto_sup DECIMAL(16,2) UNSIGNED NOT NULL, 
    tasa DECIMAL(4,2) UNSIGNED NOT NULL, 

    CONSTRAINT pk_tasa_plazo_fijo
    PRIMARY KEY (periodo,monto_inf,monto_sup)
) ENGINE=InnoDB;
#SHOW WARNINGS;

CREATE TABLE Prestamo (
    nro_prestamo INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    fecha DATE NOT NULL, 
    cant_meses TINYINT UNSIGNED NOT NULL, 
    monto DECIMAL(10,2) UNSIGNED NOT NULL,  
    tasa_interes DECIMAL(4,2) UNSIGNED NOT NULL,  
    interes DECIMAL(9,2) UNSIGNED NOT NULL, 
    valor_cuota DECIMAL(9,2) UNSIGNED NOT NULL, 
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    legajo SMALLINT UNSIGNED NOT NULL, 

    CONSTRAINT pk_prestamo
    PRIMARY KEY (nro_prestamo),

    CONSTRAINT FK_Prestamo_Empleado
    FOREIGN KEY (legajo) REFERENCES Empleado (legajo) 
    ON DELETE RESTRICT ON UPDATE CASCADE, 

    CONSTRAINT FK_Prestamo_Cliente
    FOREIGN KEY (nro_cliente) REFERENCES Cliente (nro_cliente) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
#SHOW WARNINGS;

CREATE TABLE Pago (
    nro_pago SMALLINT UNSIGNED NOT NULL, 
    fecha_venc DATE NOT NULL, 
    fecha_pago DATE , 
    nro_prestamo INT UNSIGNED NOT NULL,

    CONSTRAINT pk_pago
    PRIMARY KEY (nro_pago,nro_prestamo),

    CONSTRAINT FK_pago_pr 
    FOREIGN KEY (nro_prestamo) REFERENCES Prestamo (nro_prestamo) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Tasa_Prestamo (
    periodo SMALLINT UNSIGNED NOT NULL, 
    monto_inf DECIMAL(10,2) UNSIGNED NOT NULL, 
    monto_sup DECIMAL(10,2) UNSIGNED NOT NULL, 
    tasa DECIMAL(4,2) UNSIGNED NOT NULL, 

    CONSTRAINT pk_tasa_prestamo
    PRIMARY KEY (periodo,monto_inf,monto_sup)
) ENGINE=InnoDB;
#SHOW WARNINGS;

CREATE TABLE Caja_Ahorro (
    nro_ca INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    CBU BIGINT UNSIGNED NOT NULL, 
    saldo DECIMAL(16,2) UNSIGNED NOT NULL, 

    CONSTRAINT pk_caja_ahorro
    PRIMARY KEY (nro_ca)
) ENGINE=InnoDB;
#SHOW WARNINGS;

CREATE TABLE Cliente_CA ( 
    nro_ca INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    
 
    CONSTRAINT pk_clienteCA
    PRIMARY KEY (nro_cliente,nro_ca),

    CONSTRAINT FK_ClienteCA_CajaAhorro
    FOREIGN KEY (nro_ca) REFERENCES Caja_Ahorro (nro_ca) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
     
    CONSTRAINT FK_ClienteCA_Cliente
    FOREIGN KEY (nro_cliente) REFERENCES Cliente (nro_cliente) 
    ON DELETE RESTRICT ON UPDATE CASCADE
 
) ENGINE=InnoDB;

CREATE TABLE Tarjeta (
    nro_tarjeta BIGINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    PIN VARCHAR(32)  NOT NULL, 
    CVT VARCHAR(32)  NOT NULL, 
    fecha_venc DATE NOT NULL, 
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    nro_ca INT UNSIGNED NOT NULL, 

    CONSTRAINT pk_tarjeta
    PRIMARY KEY (nro_tarjeta),

    CONSTRAINT FK_Tarjeta_Cliente_CA
    FOREIGN KEY (nro_cliente, nro_ca) REFERENCES Cliente_CA (nro_cliente,nro_ca) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Caja (
    cod_caja MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT, 

    CONSTRAINT pk_caja
    PRIMARY KEY (cod_caja)
) ENGINE=InnoDB;

CREATE TABLE Ventanilla (
    cod_caja MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    nro_suc SMALLINT UNSIGNED NOT NULL, 

    CONSTRAINT pk_caja
    PRIMARY KEY (cod_caja),

    CONSTRAINT FK_es_una_caja_ventanilla
    FOREIGN KEY (cod_caja) REFERENCES Caja (cod_caja) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_sucur_vent
    FOREIGN KEY (nro_suc) REFERENCES Sucursal (nro_suc) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE ATM (
    cod_caja MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    direccion VARCHAR(70) NOT NULL, 
    cod_postal SMALLINT UNSIGNED NOT NULL, 

    CONSTRAINT pk_caja
    PRIMARY KEY (cod_caja),

    CONSTRAINT FK_ATM_ubic
    FOREIGN KEY (cod_postal) REFERENCES Ciudad (cod_postal) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_es_una_caja
    FOREIGN KEY (cod_caja) REFERENCES Caja (cod_caja) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Transaccion(
    nro_trans BIGINT UNSIGNED NOT NULL AUTO_INCREMENT, 
    fecha DATE NOT NULL, 
    hora TIME NOT NULL, 
    monto DECIMAL(16,2) UNSIGNED NOT NULL, 

    CONSTRAINT pk_Transaccion
    PRIMARY KEY (nro_trans)
) ENGINE=InnoDB;


CREATE TABLE Debito (
    nro_trans BIGINT UNSIGNED NOT NULL, 
    descripcion TINYTEXT , 
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    nro_ca INT UNSIGNED NOT NULL, 

    CONSTRAINT pk_Debito
    PRIMARY KEY (nro_trans),

    CONSTRAINT FK_Debito_Transaccion
    FOREIGN KEY (nro_trans) REFERENCES Transaccion (nro_trans) 
    ON DELETE RESTRICT ON UPDATE CASCADE,


    CONSTRAINT FK_Debit_Cl
    FOREIGN KEY (nro_cliente,nro_ca) REFERENCES Cliente_CA(nro_cliente,nro_ca) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Transaccion_por_caja (
    nro_trans BIGINT UNSIGNED NOT NULL, 
    cod_caja MEDIUMINT UNSIGNED NOT NULL,

    CONSTRAINT pk_Transaccion_por_caja
    PRIMARY KEY (nro_trans),

    CONSTRAINT FK_Transaccion_TransaccionPorCaja
    FOREIGN KEY (nro_trans) REFERENCES Transaccion (nro_trans) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_Trans_Caja
    FOREIGN KEY (cod_caja) REFERENCES Caja (cod_caja) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Deposito (
    nro_trans BIGINT UNSIGNED NOT NULL, 
    nro_ca INT UNSIGNED NOT NULL, 

    CONSTRAINT pk_Deposito
    PRIMARY KEY (nro_trans),

    CONSTRAINT FK_Deposito_TransaccionPorCaja
    FOREIGN KEY (nro_trans) REFERENCES Transaccion_por_caja (nro_trans) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_Depos_CA
    FOREIGN KEY (nro_ca) REFERENCES Caja_Ahorro (nro_ca) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;


CREATE TABLE Extraccion (
    nro_trans BIGINT UNSIGNED NOT NULL, 
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    nro_ca INT UNSIGNED NOT NULL, 

    CONSTRAINT pk_Extraccion
    PRIMARY KEY (nro_trans),

    CONSTRAINT FK_Extraccion_TransaccionPorCaja
    FOREIGN KEY (nro_trans) REFERENCES Transaccion_por_caja (nro_trans) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_Extrac_Cl
    FOREIGN KEY (nro_cliente,nro_ca) REFERENCES Cliente_CA (nro_cliente,nro_ca) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;


CREATE TABLE Transferencia (
    nro_trans BIGINT UNSIGNED NOT NULL, 
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    origen INT UNSIGNED NOT NULL, 
    destino INT UNSIGNED NOT NULL, 

    CONSTRAINT pk_Transferencia
    PRIMARY KEY (nro_trans),

    CONSTRAINT FK_Transferencia_TransaccionPorCaja
    FOREIGN KEY (nro_trans) REFERENCES Transaccion_por_caja (nro_trans) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_Extrac_ClienteCA
    FOREIGN KEY (origen,nro_cliente) REFERENCES Cliente_CA (nro_ca,nro_cliente) 
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT FK_Extrac_CajaAhorro
    FOREIGN KEY (destino) REFERENCES Caja_ahorro (nro_ca) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;



CREATE TABLE Plazo_Cliente ( 
    nro_plazo INT UNSIGNED NOT NULL,
    nro_cliente MEDIUMINT UNSIGNED NOT NULL, 
    
 
    CONSTRAINT pk_plazoCliente
    PRIMARY KEY (nro_plazo,nro_cliente),

    CONSTRAINT FK_PlazoCliente_PlazoFijo
    FOREIGN KEY (nro_plazo) REFERENCES Plazo_Fijo (nro_plazo) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
     
    CONSTRAINT FK_PlazoCliente_Cliente
    FOREIGN KEY (nro_cliente) REFERENCES Cliente (nro_cliente) 
    ON DELETE RESTRICT ON UPDATE CASCADE
 
) ENGINE=InnoDB;




delimiter !
CREATE PROCEDURE extraer (IN monto DECIMAL(16,2), IN tarjeta INT, IN atm INT)
BEGIN
	DECLARE saldo_actual DECIMAL(16,2);
	DECLARE cliente INT;
	DECLARE numero_cuenta INT;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN # Si se produce una SQLEXCEPTION, se retrocede la transacción con ROLLBACK
			SELECT "SQLEXCEPTION!, transacción abortada" AS resultado;
			ROLLBACK;
		END;
	START TRANSACTION;
		IF EXISTS (SELECT saldo FROM (trans_cajas_ahorro NATURAL JOIN tarjeta)  WHERE nro_tarjeta= tarjeta) THEN
			SELECT saldo, nro_ca, nro_cliente INTO saldo_actual, numero_cuenta, cliente FROM (caja_ahorro NATURAL JOIN tarjeta) WHERE nro_tarjeta = tarjeta FOR UPDATE;
			IF saldo_actual >= monto THEN 
				
				INSERT INTO transaccion (fecha,hora,monto) VALUES (CURDATE(), DATE_FORMAT(NOW(), "%H:%i:%S" ), monto);
				INSERT INTO transaccion_por_caja (nro_trans,cod_caja) VALUES (LAST_INSERT_ID(), atm);
				INSERT INTO extraccion (nro_trans, nro_cliente, nro_ca) VALUES (LAST_INSERT_ID(), cliente, numero_cuenta);
				UPDATE caja_ahorro SET saldo = saldo_actual - monto WHERE nro_ca = numero_cuenta;
				SELECT "Extraccion Exitosa" AS resultado;
			ELSE
				SELECT "Saldo insuficiente" AS resultado;
			END IF;
		ELSE
			SELECT "Error: No existe la cuenta" AS resultado;
		END IF;
		commit;
END; !
delimiter ;


delimiter !
CREATE PROCEDURE transferir(IN monto DECIMAL(16,2), IN tarjeta INT, IN atm INT, IN cuenta_destino INT)
BEGIN
	DECLARE saldo_actual_origen DECIMAL(16,2);
	DECLARE saldo_actual_destino DECIMAL(16,2);
	DECLARE cliente INT;
	DECLARE cuenta_origen INT;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN # Si se produce una SQLEXCEPTION, se retrocede la transacción con ROLLBACK
			SELECT "SQLEXCEPTION!, transacción abortada" AS resultado;
			ROLLBACK;
		END;
	START TRANSACTION;
		IF EXISTS (SELECT * FROM caja_ahorro WHERE nro_ca=cuenta_destino) 
		AND EXISTS (SELECT * FROM (caja_ahorro NATURAL JOIN tarjeta) WHERE nro_tarjeta = tarjeta) THEN

			SELECT saldo,nro_ca,nro_cliente INTO saldo_actual_origen, cuenta_origen, cliente FROM (caja_ahorro NATURAL JOIN tarjeta) WHERE nro_tarjeta = tarjeta FOR UPDATE;
			SELECT saldo INTO saldo_actual_destino FROM (caja_ahorro NATURAL JOIN tarjeta) WHERE nro_ca = cuenta_destino FOR UPDATE;
			IF saldo_actual_origen >= monto THEN 
				
				INSERT INTO transaccion (fecha,hora,monto) VALUES (CURDATE(), DATE_FORMAT(NOW(), "%H:%i:%S" ), monto);
				INSERT INTO deposito (nro_trans, destino) VALUES (LAST_INSERT_ID(), cuenta_destino);
				INSERT INTO transaccion_por_caja (nro_trans,cod_caja) VALUES (LAST_INSERT_ID(), atm);
				INSERT INTO transferencia (nro_trans, nro_cliente, origen, destino) VALUES (LAST_INSERT_ID(), cliente, cuenta_origen, cuenta_destino);
				UPDATE caja_ahorro SET saldo = saldo_actual_origen - monto WHERE nro_ca = cuenta_origen;
				UPDATE caja_ahorro SET saldo = saldo_actual_destino + monto WHERE nro_ca = cuenta_destino;
				SELECT "Transferencia Exitosa" AS resultado;
			ELSE
				SELECT "Saldo insuficiente" AS resultado;
			END IF;
		ELSE
			SELECT "Error: No existe alguna de las cuentas" AS resultado;
		END IF;
		commit;
END; !
delimiter ;




USE banco;
delimiter !
CREATE PROCEDURE pagarCuota(IN prestamo INT, IN pago INT) 
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN # Si se produce una SQLEXCEPTION, se retrocede la transacción con ROLLBACK 
			SELECT 'SQLEXCEPTION!, transacción abortada' AS resultado;
			ROLLBACK;
		END;
START TRANSACTION;
	IF EXISTS (SELECT * FROM Pago WHERE nro_pago=pago and nro_prestamo = prestamo) THEN
		SELECT fecha_pago FROM Pago WHERE nro_pago=pago and nro_prestamo = prestamo and fecha_pago IS NULL FOR UPDATE;
		UPDATE PAGO SET fecha_pago = CURDATE() WHERE fecha_pago IS NULL and nro_pago = pago and nro_prestamo = prestamo;
		SELECT "El pago se realizo con exito" AS resultado;
	ELSE
		SELECT "No se pudo cambiar la fecha de pago." AS resultado;
	END IF; 
	COMMIT; 
END; !

delimiter ;



delimiter !
CREATE TRIGGER insertar_prestamo
AFTER INSERT ON Prestamo
FOR EACH ROW
BEGIN
	DECLARE cuotas INT;
	DECLARE indice INT DEFAULT 1;
	SELECT NEW.cant_meses INTO cuotas;
	WHILE indice <= cuotas DO  
		INSERT INTO Pago(nro_prestamo, nro_pago, fecha_venc, fecha_pago) 
		VALUES (NEW.nro_prestamo, indice, date_add(NEW.fecha, interval indice month), NULL);
		SET indice = indice + 1;
	END WHILE;
END; !
delimiter ;





# CREACION DE USUARIOS -----------------------------------------------------------------------------------------------------------


# Creamos al usuario ADMIN con contraseña admin que puede conectarse solo desde localhost
    CREATE USER 'admin'@'localhost'  IDENTIFIED BY 'admin';
# Otorgamos privilegios correspondientes
    GRANT ALL PRIVILEGES ON banco.* TO 'admin'@'localhost' WITH GRANT OPTION;


#   Eliminamos el usuario vacío
    #DROP user ''@localhost;


# Creamos al usuario EMPLEADO con contraseña empleado
    CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado'; 
# Otorgamos privilegios correspondientes
    
    GRANT SELECT ON banco.Empleado TO 'empleado'@'%';
    GRANT SELECT ON banco.Sucursal TO 'empleado'@'%';
    GRANT SELECT ON banco.Tasa_Plazo_Fijo TO 'empleado'@'%';
    GRANT SELECT ON banco.Tasa_Prestamo TO 'empleado'@'%';

    GRANT SELECT, INSERT ON banco.Prestamo TO 'empleado'@'%';
    GRANT SELECT, INSERT ON banco.Plazo_Fijo TO 'empleado'@'%';
    GRANT SELECT, INSERT ON banco.Plazo_Cliente TO 'empleado'@'%';
    GRANT SELECT, INSERT ON banco.Caja_Ahorro TO 'empleado'@'%';
    GRANT SELECT, INSERT ON banco.Tarjeta TO 'empleado'@'%';

    GRANT SELECT, INSERT, UPDATE ON banco.Cliente_CA TO 'empleado'@'%';
    GRANT SELECT, INSERT, UPDATE ON banco.Cliente TO 'empleado'@'%';
    GRANT SELECT, INSERT, UPDATE ON banco.Pago TO 'empleado'@'%';
    GRANT EXECUTE ON PROCEDURE banco.pagarCuota to 'empleado'@'%';


    #Creamos la vista para atm
    CREATE VIEW trans_cajas_ahorro AS 
    SELECT * FROM (
        -- SELECT EXTRACCION
        (SELECT nro_ca, saldo, nro_trans, fecha, hora, "extraccion" as tipo, monto, cod_caja, nro_cliente, tipo_doc, nro_doc, nombre, apellido, "NULL" AS destino  FROM (Extraccion NATURAL JOIN Transaccion AS SELECTEXTRACCION NATURAL JOIN Transaccion_por_caja)

        NATURAL JOIN

        (SELECT DISTINCT  nro_ca,saldo, nro_cliente, tipo_doc, nro_doc, nombre, apellido FROM ((Caja_ahorro NATURAL JOIN Cliente_CA ) NATURAL JOIN Cliente)) AS SELECTCLIENTE)

        UNION



        -- SELECT DEBITO
        (SELECT nro_ca, saldo, nro_trans, fecha, hora, "debito" as tipo, monto, "NULL" as cod_caja, nro_cliente, tipo_doc, nro_doc, nombre, apellido, "NULL" AS destino  FROM (Debito NATURAL JOIN Transaccion AS SELECTDEBITO)

        NATURAL JOIN

        (SELECT DISTINCT  nro_ca,saldo, nro_cliente, tipo_doc, nro_doc, nombre, apellido FROM ((Caja_ahorro NATURAL JOIN Cliente_CA ) NATURAL JOIN Cliente)) AS SELECTCLIENTE)


        UNION

        -- SELECT TRANSFERENCIA
        (SELECT nro_ca, saldo, nro_trans, fecha, hora, "transferencia" as tipo, monto, cod_caja, CLIENTECA.nro_cliente, tipo_doc, nro_doc, nombre, apellido, destino  FROM
        
            (SELECT * FROM Transferencia NATURAL JOIN Transaccion NATURAL JOIN Transaccion_por_caja) AS TRANSFERENCIATRANSACCION


            JOIN

            (SELECT DISTINCT  nro_ca,saldo, nro_cliente, tipo_doc, nro_doc, nombre, apellido FROM ((Caja_ahorro NATURAL JOIN Cliente_CA) NATURAL JOIN Cliente)) AS CLIENTECA
            
            
            ON
            CLIENTECA.nro_ca = TRANSFERENCIATRANSACCION.origen AND
            CLIENTECA.nro_cliente = TRANSFERENCIATRANSACCION.nro_cliente)

       

        UNION

        -- SELECT DEPOSITO
        (SELECT nro_ca, saldo, nro_trans, fecha, hora, "deposito" as tipo, monto, cod_caja, "NULL" as nro_cliente,"NULL" as tipo_doc,"NULL" as nro_doc,"NULL" as nombre,"NULL" as apellido,"NULL" as destino  FROM (Deposito NATURAL JOIN Transaccion AS SELECTDEPOSITO NATURAL JOIN Transaccion_por_caja)

        NATURAL JOIN

        (SELECT DISTINCT * FROM (Caja_ahorro NATURAL JOIN Cliente_CA )) AS SELECTCLIENTE)) 
        AS SELECT_VISTA

        ORDER BY nro_ca;

# Creamos al usuario ATM
    CREATE USER 'atm'@'localhost' IDENTIFIED BY 'atm'; 

#Otorgamos privilegios correspondientes
    GRANT SELECT, UPDATE ON banco.Tarjeta TO 'atm'@'localhost';
    GRANT SELECT ON banco.caja_ahorro TO 'atm'@'localhost';
    GRANT SELECT ON banco.trans_cajas_ahorro TO 'atm'@'localhost';
    GRANT EXECUTE ON PROCEDURE banco.extraer to 'atm'@'localhost';
    GRANT EXECUTE ON PROCEDURE banco.transferir to 'atm'@'localhost';