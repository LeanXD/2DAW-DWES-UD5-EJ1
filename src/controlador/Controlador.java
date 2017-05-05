package controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import controlador.Accion;
import controlador.AyudaSolicitud;
import modelo.Modelo;

public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private DataSource dsBdfotogramas;

  	private String archivoPropiedades;
  	
	private ServletContext sc; 

	public void init(ServletConfig config) throws ServletException {
	// Imprescindible llamar a super.init(config) para tener acceso a la configuraciÃ³n
	// del servlet a nivel de contenedor web.
	    super.init(config);

	    try {
	    	sc = config.getServletContext();
	    	Context contexto = new InitialContext();
	    	//setDsBdfotogramas((DataSource) contexto.lookup(sc.getInitParameter("ds")));
	    	/*if (getDsBdfotogramas()==null)
	    		System.out.println("dsBdfotogramas es null.");
	    	sc.setAttribute("dsBdfotogramas", getDsBdfotogramas());*/
	    	
	    	archivoPropiedades = config.getInitParameter("archivoPropiedades");
	    } 
	    catch(NamingException ne)
	    {
	        System.out.println("Error: Método init(). tipo NamingException.");
	    } 
	}

	public void destroy() {
      sc.removeAttribute("dsBdfotogramas");
      sc = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request,response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Se obtiene el objeto de ï¿½mbito sesiï¿½n
		HttpSession sesion = request.getSession();
		
		// Obtener referencia archivo propiedades acciones-clases asociadas
		String archivoProp = sc.getRealPath("/WEB-INF/"+archivoPropiedades); 
	    // Obtener un objeto de ayuda para la solicitud
	    AyudaSolicitud ayudaSol = new AyudaSolicitud(request);
	    // Crear un objeto de acciï¿½n partiendo de los parï¿½metros asociados a la solicitud
	    Accion accion = ayudaSol.getAccion(archivoProp);
	    // Se proporciona el contexto del servlet (ï¿½mbito aplicaciï¿½n) a la acciï¿½n
	    accion.setSc(sc);
	    // Se proporciona el DataSource asociado al servlet a la acciï¿½n
	    //accion.setDS (dsBdfotogramas);
	    // Se procesa la solicitud (lï¿½gica de empresa)
	    if (accion.ejecutar(request,response))
	    {
	    // Si es correcto, obtener el componente relativo a la vista
	     // String vista = accion.getVista();
	      Modelo modelo = (Modelo) accion.getModelo();

	      PrintWriter out = null;
		  try {
				out = response.getWriter();
				response.setContentType(modelo.getContentType());
				out.println(modelo.getRespuesta());
			} catch (IOException e) {
				System.out.println("Error al obtener el flujo de salida.");
		  }
		  finally {
			  out.close();
		  }
	    
	    }
	    else
	    {
	    // Si la ejecuciï¿½n ha generado un error, procesarlo mediante el gestor centralizado de errores
	      gesError(accion.getVista(),accion.getError(),request,response);
	    }
	    
	}
	
  private void gesError(String vistaError, Exception excepcion, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    RequestDispatcher rd = request.getRequestDispatcher(vistaError);
    request.setAttribute("error",excepcion);
    rd.forward(request,response);
  }
  
	public void setDsBdfotogramas(DataSource dsBdfotogramas) {
		this.dsBdfotogramas = dsBdfotogramas;
	}

	public DataSource getDsBdfotogramas() {
		return dsBdfotogramas;
	}
}
