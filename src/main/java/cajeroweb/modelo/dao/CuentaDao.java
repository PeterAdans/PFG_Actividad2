package cajeroweb.modelo.dao;

import cajeroweb.modelo.entidades.Cuenta;

public interface CuentaDao {

	 int updateUno(Cuenta cuenta);
	 Cuenta buscarUno(int cuenta);
	void ingreso(Cuenta cuenta, double cantidad);
	void extraer(Cuenta cuenta, double cantidad);
}
