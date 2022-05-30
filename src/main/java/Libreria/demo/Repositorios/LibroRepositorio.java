//Basicamente esto hace que se conecte a la Base de Datos

package Libreria.demo.Repositorios;

import Libreria.demo.Entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    
    // Este metodo debe buscar todos los libros con ese titulo.
     @Query("SELECT u FROM Libro u WHERE u.titulo = :titulo ")
    public List<Libro> buscarPorNombre(@Param("titulo") String titulo);

}
