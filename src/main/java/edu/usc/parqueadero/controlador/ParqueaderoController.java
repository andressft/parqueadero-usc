package edu.usc.parqueadero.controlador;

import edu.usc.parqueadero.modelo.Parqueadero;
import edu.usc.parqueadero.modelo.Puesto;
import edu.usc.parqueadero.modelo.Ticket;
import edu.usc.parqueadero.modelo.Vehiculo;
import edu.usc.parqueadero.modelo.VehiculoFactory;
import edu.usc.parqueadero.persistencia.ArchivoHistorial;

/**
 * Recibe lo que el usuario hizo en la vista, valida los datos y le pide al modelo que actúe.
 * Cada método devuelve el mensaje que la vista debe mostrar.
 */
public class ParqueaderoController {

    private Parqueadero modelo = Parqueadero.getInstancia();
    private ArchivoHistorial archivo = new ArchivoHistorial("historial.txt");

    public String registrarIngreso(String placa, String tipo) {
        placa = placa.trim().toUpperCase();
        if (placa.isEmpty()) {
            return "Escribe la placa del vehículo.";
        }
        if (modelo.estaParqueado(placa)) {
            return "La placa " + placa + " ya está adentro.";
        }
        Vehiculo vehiculo = VehiculoFactory.crear(tipo, placa);
        Puesto puesto = modelo.registrarIngreso(vehiculo);
        if (puesto == null) {
            return "El parqueadero está lleno.";
        }
        return vehiculo.getTipo() + " " + placa + " entró al puesto " + puesto.getNumero() + ".";
    }

    public String registrarSalida(String placa) {
        placa = placa.trim().toUpperCase();
        if (placa.isEmpty()) {
            return "Escribe la placa que va a salir.";
        }
        Ticket ticket = modelo.registrarSalida(placa);
        if (ticket == null) {
            return "No hay ningún vehículo con la placa " + placa + ".";
        }
        archivo.guardar(ticket);
        return "Salió " + placa + ". Cobrar $" + ticket.getValor() + ".";
    }

    public void avanzarTiempo() {
        modelo.avanzarTiempo(30);
    }
}
