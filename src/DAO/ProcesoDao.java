package DAO;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.corba.se.spi.ior.ObjectId;

import BeanDirectores.Directores;
import BeanDirectores.Peliculas;
import BeanGeneros.Generos;
import BeanPeliculas.*;

public class ProcesoDao {
	
	private MongoClient mongo;
	private MongoDatabase dataBase;
	private String nameBase="bdfotogramas";
	private String coleccionDirector = "Directores";
	private String coleccionPeliculas = "Peliculas";
	private String coleccionGeneros = "Generos";

	public ProcesoDao(){
		this.mongo = new MongoClient();
		this.dataBase = this.mongo.getDatabase(nameBase);
	}
	
	public ArrayList<Directores> getDirectores(){
		Document doc;
		ArrayList<Directores> datos = new ArrayList<Directores>();
		Directores director = new Directores();
		//Asignamos la colección a utilizar
		MongoCollection<Document> collection = dataBase.getCollection(coleccionDirector);
		//Creamos el cursos para obtener los documentos.
		MongoCursor<Document> cursor = collection.find().iterator();
		//Recorremos los elementos encontramos y lo creamos en un ArryList
		while(cursor.hasNext()){
			doc = cursor.next();
			director.setId(doc.getObjectId("_id").toString());
			director.setNombre(doc.getString("nombre"));
			director.setApellidos(doc.getString("apellidos"));
			director.setPeliculas(doc.get("pelicula", Peliculas.class));
			datos.add(director);
			director = new Directores();
		}
		
		return datos;
	}
	
	public ArrayList<Generos> getGeneros(){
		Document doc;
		ArrayList<Generos> datos = new ArrayList<Generos>();
		Generos genero = new Generos();
		//Asignamos la colección a utilizar
		MongoCollection<Document> collection = dataBase.getCollection(coleccionGeneros);
		//Creamos el cursos para obtener los documentos.
		MongoCursor<Document> cursor = collection.find().iterator();
		//Recorremos los elementos encontramos y lo creamos en un ArryList
		while(cursor.hasNext()){
			doc = cursor.next();
			System.out.println(doc.getString("nombre"));
			genero.setId(doc.getObjectId("_id").toString());
			genero.setNombre(doc.getString("nombre"));
			datos.add(genero);
			genero = new Generos();
		}
		return datos;
		
	}
	
	public ArrayList<BeanPeliculas.Peliculas> getPeliculas(){
		Document doc;
		ArrayList<BeanPeliculas.Peliculas> datos = new ArrayList<BeanPeliculas.Peliculas>();
		BeanPeliculas.Peliculas pelicula = new BeanPeliculas.Peliculas();
		//Asignamos la colección a utilizar
		MongoCollection<Document> collection = dataBase.getCollection(coleccionPeliculas);
		//Creamos el cursos para obtener los documentos.
		MongoCursor<Document> cursor = collection.find().iterator();
		//Recorremos los elementos encontramos y lo creamos en un ArryList
		while(cursor.hasNext()){
			doc = cursor.next();
			pelicula.setId(doc.getObjectId("_id").toString());
			pelicula.setTitulo(doc.getString("titulo"));
			pelicula.setAnioEstreno(doc.getString("anioEstreno"));
			pelicula.setArchivo(doc.getString("archivo"));
			pelicula.setDirector(doc.getString("director"));
			pelicula.setGenero(doc.getString("genero"));
			datos.add(pelicula);
			pelicula = new BeanPeliculas.Peliculas();
		}
		
		return datos;
	}
	
	//Mdiante este método obtendremos las películas según el campo y el valor que le pasemos por parámetros 
	public ArrayList<BeanPeliculas.Peliculas> getPeliculas(String campo, String valor){
		Document doc = new Document(campo, valor);
		ArrayList<BeanPeliculas.Peliculas> datos = new ArrayList<BeanPeliculas.Peliculas>();
		BeanPeliculas.Peliculas pelicula = new BeanPeliculas.Peliculas();
		//Asignamos la colección a utilizar
		MongoCollection<Document> collection = dataBase.getCollection(coleccionPeliculas);
		//Creamos el cursos para obtener los documentos.
		MongoCursor<Document> cursor = collection.find(doc).iterator();
		//Limpiamos la variable de documento
		doc = new Document();
		//Recorremos los elementos encontramos y lo creamos en un ArryList
		while(cursor.hasNext()){
			doc = cursor.next();
			pelicula.setId(doc.getObjectId("_id").toString());
			pelicula.setTitulo(doc.getString("titulo"));
			pelicula.setAnioEstreno(doc.getString("anioEstreno"));
			pelicula.setArchivo(doc.getString("archivo"));
			pelicula.setDirector(doc.getString("director"));
			pelicula.setGenero(doc.getString("genero"));
			datos.add(pelicula);
			pelicula = new BeanPeliculas.Peliculas();
		}
		
		return datos;
	}
	
	public boolean ComprobarTitulo(String campo, String valor){
		boolean existe = false;
		Document doc = new Document (campo, valor);
		//Asignamos la colección a utilizar
		MongoCollection<Document> collection = dataBase.getCollection(coleccionPeliculas);
		//Creamos el cursos para obtener los documentos.
		FindIterable<Document> result = collection.find(doc);
		//Comprobamos si se encuentra 
		if(result.first()!=null){
			existe = true;
		}
		return existe;
	}
	
	public boolean InsertarPelicula(BeanPeliculas.Peliculas peli){
		boolean correcto = false;
		Document doc = new Document ("titulo", peli.getTitulo())
				.append("archivo", peli.getArchivo())
				.append("anioEstreno", peli.getAnioEstreno())
				.append("genero", peli.getGenero())
				.append("director", peli.getDirector());		
		//Asignamos la colección a utilizar
		MongoCollection<Document> collection; 
	    dataBase.getCollection(coleccionPeliculas).insertOne(doc);
		//collection.insertOne(doc);
		if(ComprobarTitulo("titulo", peli.getTitulo())){
			doc = new Document("titulo", peli.getTitulo());
			collection = dataBase.getCollection(coleccionPeliculas);
			FindIterable<Document> result = collection.find(doc);
			System.out.println(result.first().getObjectId("_id").toString());
			//ActualizarDirector(peli.getDirector(), result.first().getObjectId("_id").toString());
			correcto = true;
		}
		return correcto;
	}
	
	public void ActualizarDirector(String idDirector, String idPelicula){
		org.bson.types.ObjectId id = new org.bson.types.ObjectId(idDirector);
		//Document docFind = new Document ("_id", id);
		//Document docNuevo = new Document("ipPelicula", idPelicula);
		//Creamos el cursos para obtener los documentos.
		BasicDBObject objFind = new BasicDBObject();
		BasicDBObject peliculas = new BasicDBObject();
		BasicDBObject docNuevo = new BasicDBObject();
		objFind.put("_id", id);
		DB db = mongo.getDB(nameBase);
		//Asignamos la colección a utilizar
		DBCollection collection = db.getCollection(coleccionDirector);
		peliculas.append("ipPelicula", idPelicula);
		//docNuevo.put("nombre", cursor.next().get("nombre"));
		//docNuevo.put("apellidos", cursor.next().get("apellidos"));
		docNuevo.append("peliculas", peliculas);
		//Actualizamos el documento.
		 collection.update(objFind, docNuevo, false, true);
	}
	
	
}
