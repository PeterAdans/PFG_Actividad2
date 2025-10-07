package cajeroweb.modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="cuentas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuenta implements Serializable{
	@EqualsAndHashCode.Include
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_cuenta")
	private int idCuenta;
	private double saldo;
	@Column(name="tipo_cuenta")
	private String tipoCuenta;
	
	
	//métodos propios
	
	public void ingresar(double cantidad) {
	    saldo += cantidad;  // Aumenta el saldo por la cantidad ingresada
	}

	public void extraer(double cantidad) {
	    saldo -= cantidad;  // Reduce el saldo por la cantidad extraída
	}


}
