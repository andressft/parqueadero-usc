package edu.usc.parqueadero.modelo;

public class Bicicleta extends Vehiculo {

    public Bicicleta(String placa) {
        super(placa);
    }

    // Tarifa plana: siempre $1.000, sin importar el tiempo.
    @Override
    public int calcularTarifa() {
        return 1000;
    }

    @Override
    public String getTipo() {
        return "Bicicleta";
    }
}
