


USE banco;
DROP PROCEDURE extraer;
DROP PROCEDURE pagarCuota;
/*
delimiter !
CREATE PROCEDURE extraer (IN monto DECIMAL(16,2), IN tarjeta INT)
BEGIN
	DECLARE saldo_actual DECIMAL(16,2);
	DECLARE codigo_SQL CHAR(5) DEFAULT '00000';
	DECLARE codigo_MYSQL INT DEFAULT 0;
	DECLARE mensaje_error VARCHAR(500);
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN # Si se produce una SQLEXCEPTION, se retrocede la transacción con ROLLBACK
		GET DIAGNOSTICS CONDITION 1 codigo_MYSQL= MYSQL_ERRNO,	codigo_SQL= RETURNED_SQLSTATE,	mensaje_error= MESSAGE_TEXT;
			SELECT "SQLEXCEPTION!, transacción abortada" AS resultado,codigo_MySQL, codigo_SQL, mensaje_error;
			ROLLBACK;
		END;
		

	START TRANSACTION;
		IF EXISTS (SELECT saldo FROM (trans_cajas_ahorro NATURAL JOIN tarjeta)  WHERE nro_tarjeta= tarjeta) THEN
			IF saldo_actual >= monto THEN 
				INSERT INTO transaccion (fecha,hora,monto) VALUES (CURDATE(), DATE_FORMAT(NOW(), "%H:%i:%S" ),monto );
				INSERT INTO transaccion_por_caja (LAST_INSERT_ID(),_) VALUES ();
				INSERT INTO extraccion (LAST_INSERT_ID(),_,_) VALUES ();
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
*/

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