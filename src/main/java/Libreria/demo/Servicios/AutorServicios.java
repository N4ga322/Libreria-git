package Libreria.demo.Servicios;

import Libreria.demo.Entidades.Autor;
import Libreria.demo.Errores.ErrorServicios;
import Libreria.demo.Repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicios {

    @Autowired
    private AutorRepositorio autorRepositorio;

    // Hago un metodo para validar que el objeto no este vacio. (Es al pedo con este pero es para agarrarle la mano)
    private void validar(String nombre) throws ErrorServicios {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicios("El Nombre no puede estar vacio");
        }
    }
    //Voy a dejar este metodo ahi como recordatorio. Cuando le meta mas atributos lo empezare a emplear

    //Metodo para  crear Autores, por ahora solo le ponemos el nombre
    @Transactional
    public void crearAutor(String nombre) throws ErrorServicios {

        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(Boolean.TRUE);

        autorRepositorio.save(autor);

    }

    // Aca vamos a poner para modificar el Autor 
    @Transactional
    public void modificarAutor(String id, String nombre) throws ErrorServicios {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicios("No se encontro el Autor solicitado");
        }

    }
// Aca podemos Deshabilitar el Autor, solo se le cambia el boolean. Mas adelante le pondremos la fecha

    @Transactional
    public void deshabilitarAutor(String id) throws ErrorServicios {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicios("No se encontro el Autor solicitado");
        }
    }

    // Aca podemos Habilitar el Autor, solo se le cambia el boolean. Mas adelante le pondremos la fecha y mas bolucedes
    @Transactional
    public void habilitarAutor(String id) throws ErrorServicios {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicios("No se encontro el Autor solicitado");
        }

    }

    @Transactional
    public List<Autor> mostrarAutores() throws ErrorServicios {
        if(autorRepositorio.findAll().isEmpty()){
            throw new ErrorServicios("No hay autores que mostrar");
        }else{
          return autorRepositorio.findAll();  
        }
      
    }
    
    @Transactional
    public Autor consultarAutor(String id) throws ErrorServicios {
        Optional<Autor> respuesta = autorRepositorio.findById(id); //busca al autor y verifica su id
        if (respuesta.isPresent()) { //si existe un autor con ese id entra al if
            Autor autor = respuesta.get(); //trae o llama al autor que busco mas arriba por el Id
            return autor;
        } else {
            throw new ErrorServicios("No se encontro el autor");

        }
    }
    
    
    
}
