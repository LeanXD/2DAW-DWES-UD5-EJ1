package controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import controlador.*;

public abstract class FactoriaAcciones {


	@SuppressWarnings("unchecked")
	public static Accion creaAccion(String accion, String archivoProp) {
		Properties propAcciones = new Properties();
    	Class<Accion> claseAccion = null;
    	Accion objetoAccion = null;
    	String accionPedida = null;
    	
    	try {
			propAcciones.load(new FileInputStream(archivoProp));
			accionPedida = propAcciones.getProperty(accion);
			if (accionPedida!=null)
				try {
					claseAccion = (Class<Accion>) Class.forName(accionPedida);
					objetoAccion = claseAccion.newInstance();
				} catch (ClassNotFoundException excepcion) {
					excepcion.printStackTrace();
				} catch (InstantiationException excepcion) {
					excepcion.printStackTrace();
				} catch (IllegalAccessException excepcion) {
					excepcion.printStackTrace();
				}				
		} catch (FileNotFoundException excepcion) {
			excepcion.printStackTrace();
		} catch (IOException excepcion) {
			excepcion.printStackTrace();
		}
		return objetoAccion;
	}

}
