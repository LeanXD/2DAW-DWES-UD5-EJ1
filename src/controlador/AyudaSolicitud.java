package controlador;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AyudaSolicitud {
	

	HttpServletRequest request;

	public AyudaSolicitud(HttpServletRequest request)
	    throws ServletException
	  {
	    this.request = request;
	  }

	public Accion getAccion(String archivoProp)
	  {
	    String accion = (String) request.getParameter("accion");
		if(accion!=null){

			return FactoriaAcciones.creaAccion(accion, archivoProp);
		}else{
		   return FactoriaAcciones.creaAccion("controlador", "AccionDirector");
		}
	  }

}
