//Cargamos el formulario para la selección y introducción de datos por parte del usuario
function CargarInsertar(){
	$('#divInicio').fadeOut(2000);
	$('#divInsertarPeli').fadeIn(2000);
	CargarSelectGenero($('#genero'));
	CargarSelectDirector($('#director'));
}

//Llamamos a las funciones necesarias para la obtención de todos los datos sobre las películas
function MostrarFotogramas(){
	$('#divInicio').fadeOut(2000);
	$('#divFotogramas').fadeIn(2000);
	ObtenerTodasPeliculas("divTablaTodas");
}

//Cargamos las opciones de selección para la busqueda del usuario sobre películas
function CargarBusqueda(){
	$('#divInicio').fadeOut(2000);
	$('#divFotogramasDirector').fadeIn(2000);
	CargarSelectDirector($('#selectD'));
	
}

//Volver vuelve al estado principal de la pagina 
function Volver(){
	$('#divInicio').fadeIn(2000);
	$('#divInsertarPeli').fadeOut(2000);
	$('#divFotogramas').fadeOut(2000);
	$('#divFotogramasDirector').fadeOut(2000);
	$("#errores").text('');
	
}

//Carga de datos sobre generos las etiquetas select
function CargarSelectGenero(select){
	var i = 0;
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=genero",
        data:null,
        beforeSend: inicioEnvio,
        success: function (datos){
        	if(datos.error!=null){
        		alerte(datos.error);
        	}else{
        		for(i=0; i<datos.length; i++){
        			//Si se encuentra existente en el select lo eliminamos para evitar duplicados
        			$(select).find('option[value="'+datos[i].id+'"]').remove();
        			$(select).prepend('<option value="'+datos[i].id+'">'+datos[i].nombre+'</option>');
        		
            	}
        	}
        },
        timeout: 4000,
        error: problemas
    });
}

//Carga de datos sobre directores las etiquetas select que se 
function CargarSelectDirector(select){
	var i = 0;
	var nombreCompleto = "";
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=director",
        data:null,
        beforeSend: inicioEnvio,
        success: function (datos){
        	if(datos.error!=null){
        		alerte(datos.error);
        	}else{
        		for(i=0; i<datos.length; i++){
        			nombrecompleto = datos[i].nombre+" "+datos[i].apellidos;
        			//Si se encuentra existente en el select lo eliminamos para evitar duplicados
        			$(select).find('option[value="'+datos[i].id+'"]').remove();
        			$(select).append('<option value="'+datos[i].id+'">'+datos[i].nombre+" "+datos[i].apellidos+'</option>');
        			console.log(nombreCompleto);
            	}
        	}
        },
        timeout: 4000,
        error: problemas
    });
}

//Insertamos los datos en el servidor 
function InsertarPelicula(){
	var idGenero = "";
	var idDirector = "";
	
		//Obtenemos el elemento seleccionado
	idGenero =	$('#genero option:selected').attr("value");
	idDirector = $('#director option:selected').attr("value");
	
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=insertar",
        data:{"titulo": $('#titulo').val(), "archivo": $('#archivo').val(), "estreno": $('#estreno').val(), "genero": idGenero, "director": idDirector},
        beforeSend: inicioEnvio,
        success: function (datos){
        	if(datos.error!=null){
        		$("#errores").text(datos.error);
        	}else{
        		alert("Has insertado una película");
        		Volver();
        	}
        },
        timeout: 4000,
        error: problemas
    });
}
//Mediante esta función obtendremos las películas dirigidas por el director seleccionado
function ObtenerPeliculas(){
	var idDirector = "";
	var divMostrar ="divTablaDirector";
	idDirector = $('#selectD option:selected').attr("value");
	
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=peliculas",
        data:{"director":idDirector},
        beforeSend: inicioEnvio,
        success: function (datos){
        	//Si se ecuentra relleno el div donde muestra los datos se limpia ya que hay un error
        	if(datos.error!=null){
        		document.getElementById(divMostrar).innerHTML = "";
        	}
        	ObtenerDirectores(divMostrar, datos);
        	
        },
        timeout: 4000,
        error: problemas
    });
}

//Obtenemos todas las películas sin realizar ningún filtro
function ObtenerTodasPeliculas(divMostrar){
	$.ajax({
        async:true,
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        url:"controlador?accion=peliculas",
        data:null,
        beforeSend: inicioEnvio,
        success: function (datos){
        	ObtenerDirectores(divMostrar, datos);
        },
        timeout: 4000,
        error: problemas
    });
}

//Obtenemos los directores
function ObtenerDirectores(divMostrar, datosPeliculas){
	if(datosPeliculas.error != null){
		$('#errores').text(datosPeliculas.error);
	}else{
		$.ajax({
	        async:true,
	        type: "POST",
	        dataType: "json",
	        contentType: "application/x-www-form-urlencoded",
	        url:"controlador?accion=director",
	        data:null,
	        beforeSend: inicioEnvio,
	        success: function (datos){
	        	ObtenerGeneros(divMostrar, datosPeliculas, datos);
	        },
	        timeout: 4000,
	        error: problemas
	    });
	}
}

//Obtenemos los generos
function ObtenerGeneros(divMostrar, datosPeliculas, datosDirectores){
	if(datosDirectores.error!=null){
		$('#error').text(datosDirectores.error);
	}else{
		$.ajax({
	        async:true,
	        type: "POST",
	        dataType: "json",
	        contentType: "application/x-www-form-urlencoded",
	        url:"controlador?accion=genero",
	        data:null,
	        beforeSend: inicioEnvio,
	        success: function (datos){
	        	MontarTabla(divMostrar, datosPeliculas, datosDirectores, datos);
	        },
	        timeout: 4000,
	        error: problemas
	    });
	}
	
}

//Mediante esta función podremos montar cualquier tabla sobre películas en cualquier div
function MontarTabla(divMostrar, datosPeliculas, datosDirectores, datosGeneros){
	var i;
	var d = 0;
	var g = 0;
	var tab;
	var fila;
	var rutaImagen;
	var encontrado = false;
	if(datosGeneros.error!=null){
		$('#errores').text(datosGeneros.error);
	}else{
		//Creamos la cabecera
		tab = "<table border="+"\""+"5px"+"\""+ " align="+"\""+"center"+"\""+" bordercolor="+"\""+"gray"+"\""+" width="+"\""+"50%"+"\""+ " height="+"\""+"50%"+"\""+"><tr><td align="+"\""+"center"+"\""+"><b>T&iacute;tulo</td>"+
		"<td align="+"\""+"center"+"\""+"><b>A&ntilde;o de Estreno</td>"+
		"<td align="+"\""+"center"+"\""+"><b>Cartel</td>"+
		"<td align="+"\""+"center"+"\""+"><b>Director</td>"+
		"<td align="+"\""+"center"+"\""+"><b>Genero</td>"
		+"</tr>";
		//Recorremos el array con las películas
		for(i=0; i<datosPeliculas.length; i++){
			while(d<datosDirectores.length && !encontrado){
				if(datosPeliculas[i].director == datosDirectores[d].id){
					encontrado = true;
				}else{
					d++;
				}
			}
			//Volvemos al valor inicial de nuestro interruptor
			encontrado = false;
			while(g<datosGeneros.length && !encontrado){
				if(datosPeliculas[i].genero == datosGeneros[g].id){
					encontrado = true;
				}else{
					g++;
				}
			}
			rutaImagen = "images/"+datosPeliculas[i].archivo;
			fila="<tr><td align="+"\""+"center"+"\""+">"+datosPeliculas[i].titulo+"</td>"+
			"<td align="+"\""+"center"+"\""+">"+datosPeliculas[i].anioEstreno+"</td>"+
			"<td align="+"\""+"center"+"\""+"><img src="+"\""+rutaImagen+"\""+" width="+"\""+"150px"+"\""+" height="+"\""+"150px"+"\""+"></td>"+
			"<td align="+"\""+"center"+"\""+">"+datosDirectores[d].nombre+" "+datosDirectores[d].apellidos+"</td>" +
			"<td align="+"\""+"center"+"\""+">"+datosGeneros[g].nombre+"</td></tr>";
			
			tab = tab+fila;
			
			//Limpiamos las variables
			fila = "";
			rutaImagen = "";
			d=0;
			g=0;
			encontrado = false;
		}
		//Cerramos nuestra tabla
		tab = tab + "</table>";
		//Insertamos nuestra tabla
		document.getElementById(divMostrar).innerHTML = tab;
	}
}

function inicioEnvio() {
    //no se hace nada
}

function problemas(datos) {
    $("#errores").text('Problemas en el servidor');
}