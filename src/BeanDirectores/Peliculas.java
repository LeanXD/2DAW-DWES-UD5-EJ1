
package BeanDirectores;


public class Peliculas {

    private String ipPelicula;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Peliculas() {
    }

    /**
     * 
     * @param ipPelicula
     */
    public Peliculas(String ipPelicula) {
        super();
        this.ipPelicula = ipPelicula;
    }

    public String getIpPelicula() {
        return ipPelicula;
    }

    public void setIpPelicula(String ipPelicula) {
        this.ipPelicula = ipPelicula;
    }

}
