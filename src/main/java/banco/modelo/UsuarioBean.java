package banco.modelo;

public interface UsuarioBean {
	
	public String getUsername();
	public String getPassword();
	public String getDisplayname();
	public void setUsername(String username);
	public void setPassword(String password);
	public void setDisplayname(String displayname);
	
	/**
	 * Determina si la clave pasada como parametro es igual al password del usuario
	 * 
	 * @param clave
	 * @return
	 */
	public boolean passwordCoincide(char[] clave);	
	
}
