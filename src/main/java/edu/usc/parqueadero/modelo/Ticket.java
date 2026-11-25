package edu.usc.parqueadero.modelo;

/** El recibo que se genera cuando un vehículo sale. */
public class Ticket implements Guardable {

    private Vehiculo vehiculo;
    private int valor;

    public Ticket(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        // POLIMORFISMO: Ticket no sabe si es Carro, Moto o Bicicleta.
        // Java ejecuta el calcularTarifa() de la clase real del objeto.
        this.valor = vehiculo.calcularTarifa();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toLinea() {
        return vehiculo.getPlaca() + ";" + vehiculo.getTipo() + ";" + vehiculo.getMinutos() + ";" + valor;
    }

    @Override
    public String toString() {
        return vehiculo.getPlaca() + "  |  " + vehiculo.getTipo() + "  |  "
                + vehiculo.getMinutos() + " min  |  $" + valor;
    }
}
