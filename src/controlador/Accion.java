package controlador;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public interface Accion {

	  public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException;
	 
	  public String getVista();

	  public Object getModelo();

	  public void setSc(ServletContext sc);

	  public Exception getError();

	  public void setDS(DataSource ds);

	}