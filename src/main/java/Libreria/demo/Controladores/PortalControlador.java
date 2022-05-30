package Libreria.demo.Controladores;

import Libreria.demo.Entidades.Autor;
import Libreria.demo.Entidades.Editorial;
import Libreria.demo.Entidades.Libro;
import Libreria.demo.Errores.ErrorServicios;
import Libreria.demo.Repositorios.AutorRepositorio;
import Libreria.demo.Repositorios.EditorialRepositorio;
import Libreria.demo.Repositorios.LibroRepositorio;
import Libreria.demo.Servicios.AutorServicios;
import Libreria.demo.Servicios.EditorialServicios;
import Libreria.demo.Servicios.LibroServicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private LibroServicios libroServicios;
    @Autowired
    private AutorServicios autorServicios;
    @Autowired
    private EditorialServicios editorialServicios;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;

     @GetMapping("/")
    public String index() {
        return "index.html";
    }
    //----------------------------------LIBRO---------------------------------------
    
    @GetMapping("/libros")
    public String libros(ModelMap modelo) {
        List<Libro> libro = libroRepositorio.findAll();
        modelo.put("libro", libro);
        return "libros.html";
    }
    
    @GetMapping("/subir")
    public String subir(ModelMap modelo) {
        List<Autor> autor = autorRepositorio.findAll();
        List<Editorial> edit = editorialRepositorio.findAll();
        modelo.put("autor", autor);
        modelo.put("edit", edit);
        return "subir.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Autor autor, @RequestParam Editorial edit) {
        try {
            libroServicios.crearLibro(titulo, isbn, anio, ejemplares, ejemplaresPrestados, autor, edit);
        } catch (ErrorServicios ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("isbn", isbn);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("autor", autor);
            modelo.put("edir", edit);
            return "subir.html";

        }
        modelo.put("titulo", "Felicitaciones");
        modelo.put("descripcion", "Su libro se ha guardado Correctamente");

        return "exito.html";

    } 
   
 //Aca esta todo el back para modificar los libros
    @PostMapping("/editarLibro")
    public String editarLibro(ModelMap modelo, String id, @RequestParam String titulo,
            @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam Integer ejemplaresPrestados, @RequestParam Autor autor,
            @RequestParam Editorial edit) {
        
        try {
            System.out.println("ID:   " + id);
            System.out.println("Nuevo titulo " + titulo);
            System.out.println("ISBN " + isbn);
            System.out.println("Año " + anio);
            System.out.println("Ejemplares " + ejemplares);
            System.out.println("Autor" + autor);
            System.out.println("Editorial" + edit);
           // Libro libro = libroServicios.buscarId(id);
            libroServicios.modificarLibro(id, titulo, isbn, anio, ejemplares, ejemplaresPrestados, autor, edit);

            modelo.put("titulo", "Felicitaciones");
            modelo.put("descripcion", "Su libro se ha modificado correctamente");
            return "exito.html";

        } catch (ErrorServicios ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("isbn", isbn);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores", autores);
            List<Editorial> editorial = editorialRepositorio.findAll();
            modelo.put("editorial", editorial);
            return "editarLibro.html";
        }

    }  
    //EDitar libros no anda!!!!!
    @GetMapping("/editarLibro")
    public String editarLibro(ModelMap modelo, String id) throws ErrorServicios {
        Libro libro = libroServicios.buscarId(id);
        modelo.addAttribute("libro", libro);
        modelo.put("id_libro", libro.getId());
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        
        return "editarLibro.html";
    }
    
   
    
    
    //----------------------------------AUTOR---------------------------------------
     @GetMapping("/subirAutor")
    public String formulario() {
        return "subirAutor.html";
    }
    
    @PostMapping("/subirAutor") //Subo el autor
    public String guardar(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorServicios.crearAutor(nombre);
            modelo.put("titulo", "Felicitaciones");
            modelo.put("descripcion", "El Autor se ha guardado Correctamente");
            return "exito.html";
        } catch (ErrorServicios ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "subirAutor.html";
        }
   
    }
    
    @GetMapping("/autores") // Hago una lista de autores
    public String autores(ModelMap modelo) throws ErrorServicios {
        List<Autor> autores = autorServicios.mostrarAutores();
        modelo.put("autores", autores);
        return "autores.html";
    }
     
     @GetMapping("/editarAutores")
    public String autores(ModelMap modelo, @RequestParam String id) throws ErrorServicios {
        List<Autor> autor = autorRepositorio.findAll();
        modelo.put("autor", autor);
        System.out.println("La ID es:" + id);

        return "editarAutores.html";
    }
    
     @PostMapping("/editarAutores")
    public String editarAutores (@RequestParam String id, @RequestParam String nombre , ModelMap modelo) throws ErrorServicios {
        try {
       
            System.out.println("/////   ID: " + id);
            System.out.println("/////   NOMBRE: " + nombre);
            modelo.put("nombre", nombre);
            modelo.put("id", id);
            autorServicios.modificarAutor(id, nombre);
            
            modelo.addAttribute("exito", "Autor Modificado con Éxito");
            modelo.put("exito", "Autor Modificado con Éxito");
            return "exito.html";
            
        } catch (Exception ex) {
       
            modelo.addAttribute("error", "Debe ingresar el nuevo nombre del Autor");
            modelo.put("error", "Debe ingresar el nuevo nombre del Autor");
            return "editarAutores.html";
        }
    }
    
    // ME FALTAIA PONER EL HABILITAR Y DESHABILITAR
    
    
//----------------------------------EDITORIAL---------------------------------------
  @GetMapping("/subirEditorial")
    public String subirEditorial() {

        return "subirEditorial.html";
    }
    @PostMapping("/subirEditorial")
    public String subirEditorial(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicios.crearEditorial(nombre);
        } catch (ErrorServicios ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "subirEditorial.html";

        }
        modelo.put("titulo", "Felicitaciones");
        modelo.put("descripcion", "La Editorial se ha guardado Correctamente");
        return "exito.html";

    }

    @GetMapping("/Editoriales")
    public String editoriales(ModelMap modelo) throws ErrorServicios {
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);

        return "vistaEditorial.html";
    }
    
    @GetMapping("/modificar_editorial")
    public String modificarEditorial(String id, ModelMap modelo) throws ErrorServicios {

        Editorial editorial = editorialRepositorio.getById(id);
        modelo.put("nombre", editorial.getNombre());
        modelo.put("id_editorial", editorial.getId());
        return "modEditorial.html";
    }

    @PostMapping("/modificar_editorial")
    public String modificacionEditorial(ModelMap modelo, @RequestParam String id_editorial, @RequestParam String nombre2) throws ErrorServicios {
        try {
            System.out.println(id_editorial);
            System.out.println(nombre2);
            editorialServicios.modificarEdit(id_editorial, nombre2);
            Editorial edit = editorialServicios.consultarEditorial(id_editorial);

        } catch (Exception e) {
            Editorial edit = editorialServicios.consultarEditorial(id_editorial);
            modelo.put("nombre", edit.getNombre());
            modelo.put("id_editorial", edit.getId());
            modelo.put("error", e.getMessage());
            return "modEditorial.html";
        }

        return "modificacionExitosa.html";
    }
    
    

}
