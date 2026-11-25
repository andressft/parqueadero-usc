package edu.usc.parqueadero.persistencia;

import edu.usc.parqueadero.modelo.Guardable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** Escribe cada cobro como una línea nueva al final del archivo. */
public class ArchivoHistorial {

    private String ruta;

    public ArchivoHistorial(String ruta) {
        this.ruta = ruta;
    }

    public void guardar(Guardable dato) {
        // try-with-resources: el archivo se cierra solo. El "true" agrega al final sin borrar lo anterior.
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta, true))) {
            escritor.write(dato.toLinea());
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("No se pudo guardar en el archivo: " + e.getMessage());
        }
    }
}
