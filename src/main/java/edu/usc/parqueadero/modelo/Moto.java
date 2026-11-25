package edu.usc.parqueadero.modelo;

public class Moto extends Vehiculo {

    public Moto(String placa) {
        super(placa);
    }

    // Los primeros 15 minutos son gratis. Después, $1.500 por hora o fracción.
    @Override
    public int calcularTarifa() {
        if (getMinutos() <= 15) {
            return 0;
        }
        return horasCobradas() * 1500;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
}
