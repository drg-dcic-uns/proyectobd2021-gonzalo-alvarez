package banco.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Clase con métodos estáticos para convertir diferentes formatos de fechas
public class Fechas
{
   private static Logger logger = LoggerFactory.getLogger(Fechas.class);

   public static java.util.Date convertirStringADate(String p_fecha) throws Exception
   {
      java.util.Date retorno = null;
      if (p_fecha != null)
      {
         try
         {
            retorno = (new SimpleDateFormat("yyyy-MM-dd")).parse(p_fecha);
         }
         catch (ParseException ex)
         {
        	logger.error("Se produjo un error en la conversión del string {} a fecha mensaje {}",p_fecha,ex.getMessage());
        	throw new Exception("Se produjo un error en la conversión del string a fecha.");
        	
         }
      }
      return retorno;
   }

   public static java.util.Date convertirStringADate(String p_fecha, String p_hora) throws Exception
   {
      java.util.Date retorno = null;
      if ((p_fecha != null) && (p_hora != null))
      {
         try
         {
            retorno = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(p_fecha + " " + p_hora);
         }
         catch (ParseException ex)
         {
         	logger.error("Se produjo un error en la conversión del string {} a fecha mensaje {}",p_fecha,ex.getMessage());
         	throw new Exception("Se produjo un error en la conversión del string a fecha.");
         }
      }
      return retorno;
   }
   
   
   public static String convertirDateAString(java.util.Date p_fecha)
   {
      String retorno = null;
      if (p_fecha != null)
      {
         retorno = (new SimpleDateFormat("dd/MM/yyyy")).format(p_fecha);
      }
      return retorno;
   }

   public static String convertirDateAHoraString(java.util.Date p_fecha)
   {
      String retorno = null;
      if (p_fecha != null)
      {
         retorno = (new SimpleDateFormat("HH:mm:ss")).format(p_fecha);
      }
      return retorno;
   }
   
   public static String convertirDateAStringDB(java.util.Date p_fecha)
   {
      String retorno = null;
      if (p_fecha != null)
      {
         retorno = (new SimpleDateFormat("yyyy-MM-dd")).format(p_fecha);
      }
      return retorno;
   }

   public static java.sql.Date convertirDateADateSQL(java.util.Date p_fecha)
   {
      java.sql.Date retorno = null;
      if (p_fecha != null)
      {
         retorno = java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(p_fecha));
      }
      return retorno;
   }


   public static java.sql.Date convertirStringADateSQL(String p_fecha) throws Exception 
   {
      java.sql.Date retorno = null;
      if (p_fecha != null)
      {
         retorno = Fechas.convertirDateADateSQL(Fechas.convertirStringADate(p_fecha));
      }
      return retorno;
   }

   public static boolean validar(String p_fecha) throws Exception
   {
      if (p_fecha != null)
      {
         try
         {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	sdf.setLenient(false);
        	sdf.parse(p_fecha);
            return true;
         }
         catch (ParseException ex) {
         	logger.error("Se produjo un error en la conversión del string {} a fecha mensaje {}",p_fecha,ex.getMessage()); 
         	throw new Exception("Se produjo un error en la conversión del string a fecha.");
         }
      }
      return false;
   }
   
   public static String convertirStringSQL(String p_fecha)
   {
	   if (p_fecha==null)
	   {
		   return null;
	   }
	   else 
	   {
		   return ("" + p_fecha.charAt(8) + p_fecha.charAt(9) +"/"+
  					p_fecha.charAt(5) + p_fecha.charAt(6) +"/"+
  					p_fecha.charAt(0) + p_fecha.charAt(1) + p_fecha.charAt(2) + p_fecha.charAt(3));
	   }
   }
   
}
