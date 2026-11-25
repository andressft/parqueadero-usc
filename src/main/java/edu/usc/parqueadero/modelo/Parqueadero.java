package edu.usc.parqueadero.modelo;

import java.util.ArrayList;

/**
 * Patrón Singleton: solo existe un parqueadero en toda la aplicación.
 * Patrón Observer: avisa a sus observadores cada vez que algo cambia.
 */
public class Parqueadero {

    private static final int CANTIDAD_PUESTOS = 12;
    private static Parqueadero instancia;

    private ArrayList<Puesto> puestos = new ArrayList<>();
    private ArrayList<Ticket> historial = new ArrayList<>();
    private ArrayList<Observador> observadores = new ArrayList<>();

    // Constructor privado: nadie por fuera puede hacer "new Parqueadero()"
    private Parqueadero() {
        // COMPOSICIÓN: el parqueadero crea sus propios puestos
        for (int i = 1; i <= CANTIDAD_PUESTOS; i++) {
            puestos.add(new Puesto(i));
        }
    }

    public static Parqueadero getInstancia() {
        if (instancia == null) {
            instancia = new Parqueadero();
        }
        return instancia;
    }

    // Devuelve el puesto asignado, o null si el parqueadero está lleno
    public Puesto registrarIngreso(Vehiculo vehiculo) {
        for (Puesto puesto : puestos) {
            if (puesto.estaLibre()) {
                puesto.ocupar(vehiculo); // AGREGACIÓN: el vehículo viene de afuera
                notificar();
                return puesto;
            }
        }
        return null;
    }

    // Devuelve el ticket con el cobro, o null si la placa no está adentro
    public Ticket registrarSalida(String placa) {
        Puesto puesto = buscarPorPlaca(placa);
        if (puesto == null) {
            return null;
        }
        Ticket ticket = new Ticket(puesto.liberar());
        historial.add(ticket);
        notificar();
        return ticket;
    }

    // Simula el paso del tiempo para todos los vehículos que están adentro
    public void avanzarTiempo(int minutos) {
        for (Puesto puesto : puestos) {
            if (!puesto.estaLibre()) {
                puesto.getVehiculo().sumarMinutos(minutos);
            }
        }
        notificar();
    }

    public boolean estaParqueado(String placa) {
        return buscarPorPlaca(placa) != null;
    }

    private Puesto buscarPorPlaca(String placa) {
        for (Puesto puesto : puestos) {
            if (!puesto.estaLibre() && puesto.getVehiculo().getPlaca().equals(placa)) {
                return puesto;
            }
        }
        return null;
    }

    public int contarLibres() {
        int libres = 0;
        for (Puesto puesto : puestos) {
            if (puesto.estaLibre()) {
                libres++;
            }
        }
        return libres;
    }

    public int getRecaudo() {
        int total = 0;
        for (Ticket ticket : historial) {
            total = total + ticket.getValor();
        }
        return total;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public ArrayList<Ticket> getHistorial() {
        return historial;
    }

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    private void notificar() {
        for (Observador observador : observadores) {
            observador.alCambiar();
        }
    }
}
