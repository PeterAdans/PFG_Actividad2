package cajeroweb.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cajeroweb.modelo.entidades.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
	 

}
