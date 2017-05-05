
package BeanPeliculas;

public class Peliculas {

    private String id;
    private String titulo;
    private String archivo;
    private String anioEstreno;
    private String genero;
    private String director;
    private String error;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Peliculas() {
    }

    /**
     * 
     * @param id
     * @param genero
     * @param titulo
     * @param archivo
     * @param director
     * @param anioEstreno
     */
    public Peliculas(String id, String titulo, String archivo, String anioEstreno, String genero, String director) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.archivo = archivo;
        this.anioEstreno = anioEstreno;
        this.genero = genero;
        this.director = director;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(String anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setError(String error){
    	this.error = error;
    }
    
    public String getError(){
    	return this.error;
    }
}
