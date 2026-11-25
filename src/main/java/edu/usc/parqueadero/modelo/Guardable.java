package edu.usc.parqueadero.modelo;

/** Interfaz: lo que "puede guardarse" en un archivo de texto. */
public interface Guardable {

    String toLinea(); // cómo se escribe el objeto en una línea del archivo
}
