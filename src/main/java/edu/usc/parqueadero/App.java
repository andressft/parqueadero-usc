package edu.usc.parqueadero;

import edu.usc.parqueadero.controlador.ParqueaderoController;
import edu.usc.parqueadero.modelo.Parqueadero;
import edu.usc.parqueadero.vista.ParqueaderoView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Punto de entrada: aquí se conectan el modelo, la vista y el controlador. */
public class App extends Application {

    @Override
    public void start(Stage escenario) {
        ParqueaderoController controlador = new ParqueaderoController();
        ParqueaderoView vista = new ParqueaderoView(controlador);

        // La vista se suscribe al modelo (Observer) para repintarse sola
        Parqueadero.getInstancia().agregarObservador(vista);

        escenario.setTitle("Parqueadero USC");
        escenario.setScene(new Scene(vista, 920, 620));
        escenario.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
