//Basicamente esto hace que se conecte a la Base de Datos

package Libreria.demo.Repositorios;

import Libreria.demo.Entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    
    // Este metodo debe buscar todos las EDITORIALES con ese nombre.
     @Query("SELECT u FROM Editorial u WHERE u.nombre = :nombre ")
    public List<Editorial> buscarPorNombre(@Param("nombre") String nombre);

}
