package Acciones;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import BeanPeliculas.Peliculas;
import DAO.ProcesoDao;
import controlador.Accion;
import modelo.Modelo;

public class AccionInsertar implements Accion{
	
	private Object modelo;
	private ProcesoDao proceso;
	private ServletContext sc; 
	
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean resultado = true; 
		Peliculas pelicula = new Peliculas();
		String msg = null;
		String titulo = (String) request.getParameter("titulo");
		String archivo = (String) request.getParameter("archivo");
		String genero = request.getParameter("genero");
		String director = request.getParameter("director");
		String estreno =  request.getParameter("estreno");
		Modelo modeloAjax = new Modelo();
		ProcesoDao proceso = new ProcesoDao();
		modeloAjax.setContentType("application/json;charset=UTF-8");
		if(!proceso.ComprobarTitulo("titulo", titulo)){
			pelicula.setTitulo(titulo);
			pelicula.setAnioEstreno(estreno);
			pelicula.setGenero(genero);
			pelicula.setDirector(director);
			pelicula.setArchivo(archivo);
			if(!proceso.InsertarPelicula(pelicula)){
				msg="Error en la actualización";
			}
		}else{
			msg = "El título ya existe";
		}
		
		if(msg !=null){
			pelicula.setError(msg);
		}
		modeloAjax.setRespuesta(new Gson().toJson(pelicula));
		this.setModelo(modeloAjax);
		
		return resultado;
	}

	@Override
	public String getVista() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getModelo() {
		// TODO Auto-generated method stub
		return this.modelo;
	}

	@Override
	public void setSc(ServletContext sc) {
		// TODO Auto-generated method stub
		this.sc=sc;
	}

	@Override
	public Exception getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDS(DataSource ds) {
		// TODO Auto-generated method stub
		
	}
	
	private void setModelo(Object modelo) {
		this.modelo =  modelo;
	}

}
