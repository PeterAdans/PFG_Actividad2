// src/main/java/cajeroweb/modelo/repository/MovimientoRepository.java
package cajeroweb.modelo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cajeroweb.modelo.entidades.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.idCuenta = :idCuenta ORDER BY m.fecha DESC")
    List<Movimiento> findByMovimientosPorCuenta(@Param("idCuenta") int idCuenta);
}
