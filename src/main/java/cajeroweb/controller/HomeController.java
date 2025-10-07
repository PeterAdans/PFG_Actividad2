package cajeroweb.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cajeroweb.modelo.dao.CuentaDao;
import cajeroweb.modelo.dao.MovimientoDao;
import cajeroweb.modelo.entidades.Cuenta;
import cajeroweb.modelo.entidades.Movimiento;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	
	@Autowired
	private CuentaDao cdao;
	@Autowired
	private MovimientoDao mdao;
	
	
	
	@GetMapping("/transferir")
    public String mostrarFormtransferencia() {
        return "transferir";
    }
	
	@PostMapping("/transferir")
	public String proctransferencia(@RequestParam int idCuenta, @RequestParam double cantidad,
			HttpSession sesion, RedirectAttributes ratt) {
		Cuenta cuentaOrigen = (Cuenta) sesion.getAttribute("cuenta");
		
		//validar cuenta
		if (cuentaOrigen == null) {
			ratt.addFlashAttribute("mensaje", "Cuenta no existe");
			return "redirect:/transferir";
		}
		//validar que no se transfiera a la misma cuenta
		if (cuentaOrigen.getIdCuenta() == idCuenta) {
			ratt.addFlashAttribute("mensaje", "No puede transferir dinero a la misma cuenta");
			return "redirect:/transferir";
		}
		//validar cantidad mayor que 0
		if (cantidad <= 0) {
			ratt.addFlashAttribute("mensaje", "La cantidad debe ser mayor a 0");
			return "redirect:/transferir";
		}
		//validar saldo suficiente
		if (cuentaOrigen.getSaldo() < cantidad) {
			ratt.addFlashAttribute("mensaje", "Saldo insuficiente, cancelada la operación");
			return "redirect:/transferir";
		}
		//buscar cuenta
		Cuenta cuentaDestino = cdao.buscarUno(idCuenta);
		if (cuentaDestino == null) {
			ratt.addFlashAttribute("mensaje", "La cuenta no existe");
			return "redirect:/transferir";
		}
		
        
       //registramos los movimientos para la cueunta de origen
        Movimiento movOrigen = new Movimiento();
        movOrigen.setOperacion("EXTRACCIÓN POR TRANSFERENCIA");
        movOrigen.setIdMovimiento(0);
        movOrigen.setCuenta(cuentaOrigen);
        movOrigen.setFecha(new Date());
        movOrigen.setCantidad(- cantidad);
        mdao.insertUno(movOrigen);
        
        //registramos los movimientos para la ceunta de destino
        Movimiento movDestino = new Movimiento();
        movDestino.setOperacion("INGRESO POR TRANSFERENCIA");
        movDestino.setIdMovimiento(0);
        movDestino.setCuenta(cuentaDestino);
        movDestino.setFecha(new Date());
        movDestino.setCantidad(cantidad);
        mdao.insertUno(movDestino);
        
        cdao.extraer(cuentaOrigen, cantidad);
        cdao.ingreso(cuentaDestino, cantidad);
        
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - cantidad);
        sesion.setAttribute("cuenta", cuentaOrigen);

        ratt.addFlashAttribute("mensaje", "Transferencia realizada con éxito.");
        return "redirect:/home";
    }
        
       
        
        
       
    
	
	
	// Mostrar formulario para ingresar dinero
	@GetMapping("/ingresar")
    public String ingreso() {
        return "ingresar";
    }

    
    @PostMapping("/ingresar")
    public String procesarIngreso(@RequestParam double cantidad, RedirectAttributes ratt, HttpSession sesion) {
        Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");

        // Validamos que la cuenta no sea nula y que la cantidad sea positiva
        if (cuenta == null || cantidad <= 0) {
            ratt.addFlashAttribute("mensaje", "Operación incorrecta: cantidad incorrecta.");
            return "redirect:/ingresar";
        }
        // Registramos el movimiento en la tabla de movimientos
        Movimiento mov = new Movimiento();
        mov.setOperacion("INGRESO");
        mov.setIdMovimiento(0);
        mov.setCuenta(cuenta);
        mov.setFecha(new Date());
        mov.setCantidad(cantidad);
        
        //Insertar movimiento
        mdao.insertUno(mov);
        cdao.ingreso(cuenta, cantidad);
        ratt.addFlashAttribute("mensaje", "Ingreso realizado con éxito");
        
        return "redirect:/home";
    }
	
     @GetMapping("/extraer")
    public String extraerDinero() {
        return "extraer";
    }

     @PostMapping("/extraer")
     public String procExtraer(@RequestParam double cantidad, RedirectAttributes ratt, HttpSession sesion) {
         Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");

         if (cantidad <= 0) {
             ratt.addFlashAttribute("mensaje", "La cantidad debe ser mayor a cero");
             return "redirect:/extraer"; // ← CORTA aquí
         }
         if (cuenta.getSaldo() < cantidad) {
             ratt.addFlashAttribute("mensaje", "saldo insuficiente para realizar la operación");
             return "redirect:/extraer"; // ← CORTA aquí
         }

         Movimiento mov = new Movimiento();
         mov.setOperacion("EXTRACCION");
         mov.setIdMovimiento(0);
         mov.setCuenta(cuenta);
         mov.setFecha(new Date());
         mov.setCantidad(-cantidad); // ← extracción en NEGATIVO

         mdao.insertUno(mov);
         cdao.extraer(cuenta, cantidad);

         ratt.addFlashAttribute("mensaje", "Extracción realizada con éxito");
         return "redirect:/";
     }

    
    @GetMapping("/movimientos")
    public String verMovimientos(HttpSession sesion, Model model) {
        Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");

        if (cuenta == null) {
            return "redirect:/login";
        }

        // Obtener movimientos de la cuenta
        List<Movimiento> movimientos = mdao.findByMovimientosPorCuenta(cuenta.getIdCuenta());
        model.addAttribute("movimientos", movimientos);
        model.addAttribute("saldo", cuenta.getSaldo());
        return "movimientos";
    }

	
	@GetMapping({"","/","/home"})
	public String inicio() {
		return "home";
	}
	
	@GetMapping("/login")
	public String introducirCuenta() {
	
		return "FormIntroC";
	}
	
	@PostMapping("/login")
	public String procesarFormLogin(@RequestParam int idCuenta, HttpSession sesion,
			RedirectAttributes ratt ){
		
		
		Cuenta cuenta = cdao.buscarUno(idCuenta);
		
		if (cuenta != null) {
		
            sesion.setAttribute("cuenta", cuenta);
            return "redirect:/";
        } else {
        	ratt.addFlashAttribute("mensaje", "Esta cuenta no existe");
        
            return "redirect:/login";
            
        }
    }
	
	@GetMapping("/logout")
	public String cerrarSesion(HttpSession sesion) {
		sesion.removeAttribute("cuenta");
		sesion.invalidate();
	
		return "redirect:/login";
	}
	
	
	

}
