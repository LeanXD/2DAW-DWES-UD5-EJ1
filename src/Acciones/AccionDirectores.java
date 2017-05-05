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
import DAO.ProcesoDao;
import controlador.Accion;
import modelo.Modelo;

public class AccionDirectores implements Accion {
	
	private Object modelo;
	private ProcesoDao proceso;
	private ServletContext sc; 

	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean resultado = true;
		ArrayList<Directores> datDirectores = new ArrayList<Directores>();
		Modelo modeloAjax = new Modelo();
		modeloAjax.setContentType("application/json;charset=UTF-8");
		proceso = new ProcesoDao();
		//En este caso seleccionamos todos los directores.
		datDirectores = proceso.getDirectores();
		if(datDirectores.size()>0){
			modeloAjax.setRespuesta(new Gson().toJson(datDirectores));
		}else{
			modeloAjax.setError("No hay directores");
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
