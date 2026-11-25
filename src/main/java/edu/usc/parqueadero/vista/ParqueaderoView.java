package edu.usc.parqueadero.vista;

import edu.usc.parqueadero.controlador.ParqueaderoController;
import edu.usc.parqueadero.modelo.Observador;
import edu.usc.parqueadero.modelo.Parqueadero;
import edu.usc.parqueadero.modelo.Puesto;
import edu.usc.parqueadero.modelo.Ticket;
import edu.usc.parqueadero.modelo.Vehiculo;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * La ventana. Solo dibuja y captura clics: no calcula nada.
 * Lee el modelo para pintarse, pero para cambiarlo siempre pasa por el controlador.
 */
public class ParqueaderoView extends BorderPane implements Observador {

    private static final String ESTILO_LIBRE = "-fx-background-color: #BBF7D0; -fx-border-color: #16A34A;"
            + " -fx-font-size: 13px; -fx-text-alignment: center;";
    private static final String ESTILO_OCUPADO = "-fx-background-color: #FECACA; -fx-border-color: #DC2626;"
            + " -fx-font-size: 13px; -fx-text-alignment: center;";

    private ParqueaderoController controlador;

    private Label lblLibres = new Label();
    private Label lblRecaudo = new Label();
    private Label lblMensaje = new Label("Bienvenido.");
    private TextField txtPlacaIngreso = new TextField();
    private ComboBox<String> cmbTipo = new ComboBox<>();
    private TextField txtPlacaSalida = new TextField();
    private TextArea txtHistorial = new TextArea();
    private ArrayList<Button> botonesPuestos = new ArrayList<>();

    public ParqueaderoView(ParqueaderoController controlador) {
        this.controlador = controlador;
        setTop(crearBarraSuperior());
        setLeft(crearFormulario());
        setCenter(crearMapa());
        setBottom(crearHistorial());
        alCambiar(); // primer dibujo de la ventana
    }

    private HBox crearBarraSuperior() {
        lblLibres.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        lblRecaudo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        HBox barra = new HBox(60, lblLibres, lblRecaudo);
        barra.setPadding(new Insets(12, 20, 12, 20));
        barra.setStyle("-fx-background-color: #1E3A8A;");
        return barra;
    }

    private VBox crearFormulario() {
        Label tituloIngreso = new Label("Ingreso");
        tituloIngreso.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        txtPlacaIngreso.setPromptText("Ej: ABC123");
        cmbTipo.getItems().addAll("Carro", "Moto", "Bicicleta");
        cmbTipo.setValue("Carro");
        cmbTipo.setPrefWidth(200);
        Button btnIngresar = new Button("Registrar ingreso");
        btnIngresar.setPrefWidth(200);
        btnIngresar.setOnAction(e -> {
            lblMensaje.setText(controlador.registrarIngreso(txtPlacaIngreso.getText(), cmbTipo.getValue()));
            txtPlacaIngreso.setText("");
        });

        Label tituloSalida = new Label("Salida");
        tituloSalida.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        txtPlacaSalida.setPromptText("Placa (o clic en el puesto)");
        Button btnSalida = new Button("Cobrar y liberar");
        btnSalida.setPrefWidth(200);
        btnSalida.setOnAction(e -> {
            lblMensaje.setText(controlador.registrarSalida(txtPlacaSalida.getText()));
            txtPlacaSalida.setText("");
        });

        Button btnTiempo = new Button("Avanzar 30 minutos");
        btnTiempo.setPrefWidth(200);
        btnTiempo.setOnAction(e -> {
            controlador.avanzarTiempo();
            lblMensaje.setText("Pasaron 30 minutos.");
        });

        lblMensaje.setWrapText(true);
        lblMensaje.setStyle("-fx-text-fill: #1E3A8A; -fx-font-weight: bold;");

        VBox formulario = new VBox(8,
                tituloIngreso, new Label("Placa:"), txtPlacaIngreso, new Label("Tipo:"), cmbTipo, btnIngresar,
                new Label(" "), tituloSalida, txtPlacaSalida, btnSalida,
                new Label(" "), btnTiempo, lblMensaje);
        formulario.setPadding(new Insets(15));
        formulario.setPrefWidth(230);
        return formulario;
    }

    private GridPane crearMapa() {
        GridPane grilla = new GridPane();
        grilla.setHgap(10);
        grilla.setVgap(10);
        grilla.setPadding(new Insets(15));
        int cantidad = Parqueadero.getInstancia().getPuestos().size();
        for (int i = 0; i < cantidad; i++) {
            Button boton = new Button();
            boton.setPrefSize(150, 95);
            final int indice = i; // la lambda necesita una variable final, como en la calculadora
            boton.setOnAction(e -> seleccionarPuesto(indice));
            botonesPuestos.add(boton);
            grilla.add(boton, i % 4, i / 4); // 4 columnas: (columna, fila)
        }
        return grilla;
    }

    private VBox crearHistorial() {
        Label titulo = new Label("Historial de salidas");
        titulo.setStyle("-fx-font-weight: bold;");
        txtHistorial.setEditable(false);
        txtHistorial.setPrefRowCount(5);
        VBox caja = new VBox(5, titulo, txtHistorial);
        caja.setPadding(new Insets(0, 15, 15, 15));
        return caja;
    }

    // Al hacer clic en un puesto ocupado, su placa pasa al campo de salida
    private void seleccionarPuesto(int indice) {
        Puesto puesto = Parqueadero.getInstancia().getPuestos().get(indice);
        if (!puesto.estaLibre()) {
            txtPlacaSalida.setText(puesto.getVehiculo().getPlaca());
        }
    }

    // Patrón Observer: el parqueadero llama este método cada vez que cambia algo
    @Override
    public void alCambiar() {
        Parqueadero modelo = Parqueadero.getInstancia();

        for (int i = 0; i < botonesPuestos.size(); i++) {
            Puesto puesto = modelo.getPuestos().get(i);
            Button boton = botonesPuestos.get(i);
            if (puesto.estaLibre()) {
                boton.setText("P" + puesto.getNumero() + "\nLibre");
                boton.setStyle(ESTILO_LIBRE);
            } else {
                Vehiculo v = puesto.getVehiculo();
                boton.setText("P" + puesto.getNumero() + "\n" + v.getPlaca() + "\n"
                        + v.getTipo() + " - " + v.getMinutos() + " min");
                boton.setStyle(ESTILO_OCUPADO);
            }
        }

        lblLibres.setText("Puestos libres: " + modelo.contarLibres() + " de " + modelo.getPuestos().size());
        lblRecaudo.setText("Recaudo del día: $" + modelo.getRecaudo());

        String texto = "";
        for (Ticket ticket : modelo.getHistorial()) {
            texto = ticket + "\n" + texto; // el más reciente queda arriba
        }
        txtHistorial.setText(texto);
    }
}
