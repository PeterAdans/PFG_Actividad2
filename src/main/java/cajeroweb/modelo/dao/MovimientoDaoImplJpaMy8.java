package cajeroweb.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajeroweb.modelo.entidades.Movimiento;
import cajeroweb.modelo.repository.MovimientoRepository;

@Repository
public class MovimientoDaoImplJpaMy8 implements MovimientoDao{
	
	@Autowired
	private MovimientoRepository mrepo;
        
	
	@Override
	public Movimiento insertUno(Movimiento movimiento) {
        try {
            return mrepo.save(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
	
	@Override
	public List<Movimiento> findByMovimientosPorCuenta(int idCuenta) {
		return mrepo.findByMovimientosPorCuenta(idCuenta);
		
	}

}
