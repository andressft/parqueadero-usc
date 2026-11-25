package edu.usc.parqueadero.modelo;

public class Puesto {

    private int numero;
    private Vehiculo vehiculo; // null cuando el puesto está libre (cardinalidad 0..1)

    public Puesto(int numero) {
        this.numero = numero;
        this.vehiculo = null;
    }

    public int getNumero() {
        return numero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public boolean estaLibre() {
        return vehiculo == null;
    }

    public void ocupar(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    // Saca el vehículo del puesto y lo devuelve
    public Vehiculo liberar() {
        Vehiculo sale = vehiculo;
        vehiculo = null;
        return sale;
    }
}
