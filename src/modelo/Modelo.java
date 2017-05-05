package modelo;

public class Modelo {
	
	private String contentType;

	private Object respuesta;
	
	private String error;
	

	public Modelo() {
		
	}
	
	public Modelo(String contentType, String respuesta) {
		this.contentType = contentType;
		this.respuesta = respuesta;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setRespuesta(Object respuesta) {
		this.respuesta = respuesta;
	}
	
	public Object getRespuesta() {
		return respuesta;
	}
	
	public String getError(){
		return this.error;
	}
	
	public void setError(String error){
		this.error = error;
	}

	
}
