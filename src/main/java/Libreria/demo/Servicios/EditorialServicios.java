package Libreria.demo.Servicios;

import Libreria.demo.Entidades.Editorial;
import Libreria.demo.Errores.ErrorServicios;
import Libreria.demo.Repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicios {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    // Hago un metodo para validar que el objeto no este vacio. (Es al pedo con este pero es para agarrarle la mano)
    private void validar(String nombre) throws ErrorServicios {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicios("El Nombre no puede estar vacio");
        }
    }
    //Voy a dejar este metodo ahi como recordatorio. Cuando le meta mas atributos lo empezare a emplear

    //Metodo para  crear Editoriales, por ahora solo le ponemos el nombre
    @Transactional
    public void crearEditorial(String nombre) throws ErrorServicios {

        validar(nombre);
        Editorial edit = new Editorial();
        edit.setNombre(nombre);
        edit.setAlta(Boolean.TRUE);

        editorialRepositorio.save(edit);

    }

    // Aca vamos a poner para modificar la Editorial 
    @Transactional
    public void modificarEdit(String id, String nombre) throws ErrorServicios {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial edit = respuesta.get();
            edit.setNombre(nombre);
            editorialRepositorio.save(edit);
        } else {
            throw new ErrorServicios("No se encontro la Editorial solicitada");
        }

    }
// Aca podemos Deshabilitar la Editorial, solo se le cambia el boolean. Mas adelante le pondremos la fecha

    @Transactional
    public void deshabilitarEdit(String id) throws ErrorServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial edit = respuesta.get();
            edit.setAlta(Boolean.FALSE);

            editorialRepositorio.save(edit);
        } else {
            throw new ErrorServicios("No se encontro la Editorial solicitada");
        }
    }

    // Aca podemos Habilitar la Editorial, solo se le cambia el boolean. Mas adelante le pondremos la fecha y mas bolucedes
    @Transactional
    public void habilitarEdit(String id) throws ErrorServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial edit = respuesta.get();
            edit.setAlta(Boolean.TRUE);

            editorialRepositorio.save(edit);
        } else {
            throw new ErrorServicios("No se encontro la Editorial solicitada");
        }

    }

    @Transactional
    public Editorial consultarEditorial(String id) throws ErrorServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            return editorial;
        } else {
            throw new ErrorServicios("No se encontro la editorial");
        }
    }
    
}
