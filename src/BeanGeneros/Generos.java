
package BeanGeneros;

public class Generos {

    private String id;
    private String nombre;
    private String error;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Generos() {
    }

    /**
     * 
     * @param nombre
     * @param id
     */
    public Generos(String id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    private void setError(String error){
    	this.error = error;
    }
    
    private String getError(){
    	return this.error;
    }

}
