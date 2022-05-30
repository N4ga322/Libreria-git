//Basicamente esto hace que se conecte a la Base de Datos
package Libreria.demo.Repositorios;

import Libreria.demo.Entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    // Este metodo debe buscar todos los AUTORES con ese nombre.
     @Query("SELECT u FROM Autor u WHERE u.nombre = :nombre ")
    public List<Autor> buscarPorNombre(@Param("nombre") String nombre);

}
