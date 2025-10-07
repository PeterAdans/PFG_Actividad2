package cajeroweb.modelo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajeroweb.modelo.entidades.Cuenta;
import cajeroweb.modelo.repository.CuentaRepository;
@Repository
public class CuentaDaoImplJpaMy8 implements CuentaDao{
	
	@Autowired
	private CuentaRepository crepo;

	@Override
	public Cuenta buscarUno(int idCuenta) {
		// TODO Auto-generated method stub
		return crepo.findById(idCuenta).orElse(null);
	}

	@Override
    public int updateUno(Cuenta entidad) {
        try {
            if (crepo.existsById(entidad.getIdCuenta())) {
                crepo.save(entidad);
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

	@Override
	public  void ingreso(Cuenta cuenta, double cantidad) {
		if (cuenta != null) {
			cuenta.setSaldo(cuenta.getSaldo() + cantidad);
			updateUno(cuenta);
		}
		
	}

	@Override
	public  void extraer(Cuenta cuenta, double cantidad) {
		if (cuenta != null) {
			cuenta.setSaldo(cuenta.getSaldo() - cantidad);
			updateUno(cuenta);
		}
	}

	
	
	
}