package net.azarquiel.apimetro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*al poner RestController en vez de Controller,
  ya no tenemos que poner la anotacion ResponseBody*/
@RestController
@CrossOrigin
@EnableAutoConfiguration
public class Controller {
    @Autowired
    LineaRepository lineaRepository;

    // Welcome de nuestra api por ejemplo podríamos poner aquí
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String get() {
   	 String cadena = "<h1 style='text-align: center; background-color: #0000c0; color: #C0C0FF;'>Welcome to Metro Api with SpringBoot - Hugo</h1>";
   	 cadena +="<table border='1' style='width: 40%;margin: 0 auto; background-color: #C0C0FF; color:#0000c0;'>";
   	 cadena +="<tr style='background-color: #0000c0; color: #C0C0FF;'><th>Method</th><th>Url</th><th>Description</th></tr>";
   	 cadena +="<tr><td>get </td><td>/lineas</td><td>Lista de lineas</td></tr>";
   	 cadena +="<tr><td>get </td><td>/linea/{linea}</td><td>Linea</td></tr>";
   	 cadena +="<tr><td>post </td><td>/linea</td><td>Inserta linea</td></tr>";
   	 cadena +="<tr><td>delete </td><td>/linea/{linea}</td><td>Borra linea</td></tr>";
    	cadena +="</table>";
   	 return cadena;
    }

    // Get lista con todas lineas
    @RequestMapping(value = "lineas", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getLineas() {
   	 try {
   		 Iterable<Linea> lineas = lineaRepository.findAll();
   		 return new ResponseEntity<>(lineas, HttpStatus.OK);

   	 } catch (Exception ex) {
   		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   	 }

}

    // Get una linea indicando el numero de linea en la url
    @RequestMapping(value = "linea/{linea}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getLineas(@PathVariable(value = "linea") int linea) {
   	 try {
   		 Linea lineaResponse = lineaRepository.findById(linea).orElse(null);
   		 return new ResponseEntity<>(lineaResponse, HttpStatus.OK);

   	 } catch (Exception ex) {
   		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   	 }

    }
    // Insert una linea
    @RequestMapping(value = "linea", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> insertarLinea(@RequestBody Linea linea) {
   	 try {
   		 Linea lineaResponse = lineaRepository.save(linea);
   		 return new ResponseEntity<>(lineaResponse, HttpStatus.CREATED);

   	 } catch (Exception e) {
   		 return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
   	 }
    }
    // Delete la linea a traves del numero de linea
    @RequestMapping(value = "linea/{linea}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Void> borrarLinea(@PathVariable(value = "linea") int linea) {
   	 try {
   		 lineaRepository.deleteById(linea);
   		 return new ResponseEntity<Void>(HttpStatus.OK);
   		 
   	 } catch (Exception ex) {
   		 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
   	 }
    }
}
