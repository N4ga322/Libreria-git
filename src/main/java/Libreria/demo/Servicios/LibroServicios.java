package Libreria.demo.Servicios;

import Libreria.demo.Entidades.Autor;
import Libreria.demo.Entidades.Editorial;
import Libreria.demo.Entidades.Libro;
import Libreria.demo.Errores.ErrorServicios;
import Libreria.demo.Repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicios {

    @Autowired
    private LibroRepositorio libroRepositorio;

    // Hay que agregarle el autor y editorial
    public void validar(String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial edit) throws ErrorServicios {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicios("El Titulo no puede estar vacio");
        }
        if (anio == 0) {
            throw new ErrorServicios("El Año no puede estar vacio o ser igual a 0");
        }
        if (ejemplares == 0) {
            throw new ErrorServicios("Ejemplares no puede estar vacio o ser igual a 0");
        }
        if (isbn == 0) {
            throw new ErrorServicios("ISBN no puede estar vacio o ser igual a 0");
        }
        if (ejemplaresPrestados == 0) {
            throw new ErrorServicios("ISBN no puede estar vacio o ser igual a 0");
        }

        if (autor == null) {
            throw new ErrorServicios("Autor no puede estar vacio");
        }
        if (edit == null) {
            throw new ErrorServicios("Editorial no puede estar vacio");
        }
    }

    //Metodo para  crear Autores, por ahora solo le ponemos el nombre
    @Transactional
    public void crearLibro(String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial edit) throws ErrorServicios {

        validar(titulo, isbn, anio, ejemplares, ejemplaresPrestados, autor, edit);

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setEditorial(edit);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados); //Atento con este
        libro.setAlta(Boolean.TRUE);

        libroRepositorio.save(libro);
    }

    // Aca vamos a poner para modificar el Autor 
    @Transactional
    public void modificarLibro(String id, String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial edit) throws ErrorServicios {

        validar(titulo, isbn, anio, ejemplares, ejemplaresPrestados, autor, edit);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setAnio(anio);
            libro.setAutor(autor);
            libro.setEditorial(edit);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
            libro.setAlta(Boolean.TRUE);

            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicios("No se encontro el Libro solicitado");
        }

    }

    @Transactional
    public void deshabilitarLibro(String id) throws ErrorServicios {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicios("No se encontro el Libro solicitado");
        }
    }

    @Transactional
    public void habilitarLibro(String id) throws ErrorServicios {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicios("No se encontro el Libro solicitado");
        }

    }

    public Libro buscarId(String id) throws ErrorServicios {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErrorServicios("No se encontro el Libro solicitado");
        }
    }

    @Transactional
    public List<Libro> mostrarLibros() throws ErrorServicios {
        if (libroRepositorio.findAll().isEmpty()) {
            throw new ErrorServicios("No hay libros que mostrar");
        } else {
            return libroRepositorio.findAll();
        }

    }
    
    
    
    
}
