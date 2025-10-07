package cajeroweb.modelo.dao;

import java.util.List;

import cajeroweb.modelo.entidades.Movimiento;

public interface MovimientoDao{
	
	Movimiento insertUno(Movimiento movimiento);
	List<Movimiento> findByMovimientosPorCuenta(int idCuenta);
}
