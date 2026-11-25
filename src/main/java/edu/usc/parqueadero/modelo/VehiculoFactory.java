package edu.usc.parqueadero.modelo;

/** Patrón Factory: un solo lugar decide qué clase crear según el tipo elegido. */
public class VehiculoFactory {

    public static Vehiculo crear(String tipo, String placa) {
        if (tipo.equals("Moto")) {
            return new Moto(placa);
        }
        if (tipo.equals("Bicicleta")) {
            return new Bicicleta(placa);
        }
        return new Carro(placa);
    }
}
