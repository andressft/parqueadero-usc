package edu.usc.parqueadero.modelo;

public class Carro extends Vehiculo {

    public Carro(String placa) {
        super(placa);
    }

    // $3.000 por hora o fracción. Mínimo se cobra una hora.
    @Override
    public int calcularTarifa() {
        int horas = horasCobradas();
        if (horas == 0) {
            horas = 1;
        }
        return horas * 3000;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }
}
