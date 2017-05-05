package Acciones;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import BeanDirectores.Directores;
import BeanPeliculas.Peliculas;
import DAO.ProcesoDao;
import controlador.Accion;
import modelo.Modelo;

public class AccionPeliculas implements Accion {
	private Object modelo;
	private ProcesoDao proceso;
	private ServletContext sc; 
	
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean resultado = true;
		Peliculas peli = new Peliculas();
		ArrayList<Peliculas> datPelicula = new ArrayList<Peliculas>();
		Modelo modeloAjax = new Modelo();
		ProcesoDao proceso = new ProcesoDao();
		String idDirector = request.getParameter("director");
		modeloAjax.setContentType("application/json;charset=UTF-8");
		if(idDirector!=null){
			datPelicula = proceso.getPeliculas("director", idDirector);
		}else{
			datPelicula = proceso.getPeliculas();
		}
		if(datPelicula.size()>0){
			modeloAjax.setRespuesta(new Gson().toJson(datPelicula));
		}else{
			peli.setError("No hay películas");
			modeloAjax.setRespuesta(new Gson().toJson(peli));
		}
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
		this.sc = sc;
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
