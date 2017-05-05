
package BeanDirectores;

public class Directores {

    private String id;

    private String nombre;

    private String apellidos;
    
    private Peliculas peliculas;
    
    private String error;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Directores() {
    }

    /**
     * 
     * @param peliculas
     * @param nombre
     * @param id
     * @param apellidos
     */
    public Directores(String id, String nombre, String apellidos, Peliculas peliculas) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.peliculas = peliculas;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Peliculas getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Peliculas peliculas) {
        this.peliculas = peliculas;
    }
    
    private void setError(String error){
    	this.error = error;
    }
    
    private String getError(){
    	return this.error;
    }

}
