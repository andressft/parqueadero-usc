# Parqueadero USC: proyecto final APO2

Aplicación de escritorio en JavaFX para administrar un parqueadero de 12 puestos.

## Cómo descargarla

Con git: `git clone https://github.com/andressft/parqueadero-usc.git`

Sin git: en GitHub, botón verde **Code** › **Download ZIP**, y descomprimir.

## Cómo ejecutarla

Requisito: JDK 17 o superior. En la terminal de VS Code (Ctrl + `), dentro de esta carpeta:

```
.\mvnw.cmd javafx:run        (Windows, no necesita tener Maven instalado)
mvn javafx:run               (si tienen Maven instalado, como en la práctica)
```

No usen el botón "Run" de VS Code sobre `App.java`: falla con "JavaFX runtime components are missing".

## Cómo se usa

1. **Ingreso:** escribir la placa, elegir el tipo y pulsar "Registrar ingreso". El puesto se pone rojo.
2. **Avanzar 30 minutos:** simula el paso del tiempo para que se vean cobros distintos.
3. **Salida:** clic en un puesto rojo (copia la placa) y luego "Cobrar y liberar".
4. Cada cobro queda en el historial de la ventana y en el archivo `historial.txt`.

Tarifas: carro $3.000 por hora o fracción (mínimo una hora), moto gratis los primeros 15 minutos y luego $1.500 por hora o fracción, bicicleta $1.000 fijos.

## Estructura (MVC)

| Paquete | Clases | Qué hace |
|---|---|---|
| `modelo` | Vehiculo (abstracta), Carro, Moto, Bicicleta, Puesto, Ticket, Parqueadero, VehiculoFactory, Observador y Guardable (interfaces) | Las reglas del negocio. No usa nada de JavaFX. |
| `vista` | ParqueaderoView | La ventana. Solo dibuja y captura clics. |
| `controlador` | ParqueaderoController | Valida lo que escribe el usuario y llama al modelo. |
| `persistencia` | ArchivoHistorial | Guarda cada cobro en `historial.txt`. |
| (raíz) | App | Arranca la aplicación y conecta las partes. |

Patrones: MVC, Singleton (`Parqueadero`), Factory (`VehiculoFactory`) y Observer (`Observador`).

## Antes de entregar

Ejecutar `.\mvnw.cmd clean` para borrar la carpeta `target/` antes de comprimir el proyecto.
