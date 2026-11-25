package edu.usc.parqueadero.modelo;

/**
 * Clase abstracta: no se puede hacer "new Vehiculo(...)".
 * Obliga a cada hija a decir cómo cobra (calcularTarifa) y qué tipo es (getTipo).
 */
public abstract class Vehiculo {

    private String placa;
    private int minutos; // tiempo que lleva parqueado (simulado con un botón)

    public Vehiculo(String placa) {
        this.placa = placa;
        this.minutos = 0;
    }

    public String getPlaca() {
        return placa;
    }

    public int getMinutos() {
        return minutos;
    }

    public void sumarMinutos(int cantidad) {
        minutos = minutos + cantidad;
    }

    // Una hora empezada se cobra como hora completa. Es protected para que lo usen las hijas.
    protected int horasCobradas() {
        int horas = minutos / 60;
        if (minutos % 60 > 0) {
            horas++;
        }
        return horas;
    }

    // Métodos abstractos: cada tipo de vehículo los implementa a su manera
    public abstract int calcularTarifa();

    public abstract String getTipo();
}
