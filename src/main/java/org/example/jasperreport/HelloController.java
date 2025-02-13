package org.example.jasperreport;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
   @FXML
    private ComboBox<String> reportComboBox;
    @FXML
    private Button generateReportButton;
    @FXML
    private ComboBox<String> teamComboBox;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField playerNameTextField;

    @FXML
    public void initialize() {
        // Opciones del ComboBox
        reportComboBox.getItems().addAll("Equipos por año", "Estadisticas de un jugador");
        reportComboBox.getSelectionModel().selectFirst(); // Selección predeterminada
        teamComboBox.getItems().addAll("BUF","DAL","DAL", "MIA", "PHI", "SF", "MIA", "DET", "SF", "DET", "BAL", "PHI", "GB", "LA", "KC", "NO", "SF", "HOU", "NO", "JAX", "NYJ", "JAX", "HOU", "LA", "CIN");
        teamComboBox.getSelectionModel().selectFirst();
        reportComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Estadisticas de un jugador".equals(newValue)) {
                yearTextField.setVisible(false);
                teamComboBox.setVisible(false);
                playerNameTextField.setVisible(true);
            } else {
                yearTextField.setVisible(true);
                teamComboBox.setVisible(true);
                playerNameTextField.setVisible(false);
            }
        });

    }
    @FXML
    public void generateReport() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/nfl","root","");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            Map parametros = new HashMap();
            parametros.put("equipo", teamComboBox.getSelectionModel().getSelectedItem());
            parametros.put("draft", yearTextField.getText());
            parametros.put("jugador", playerNameTextField.getText());
            if(reportComboBox.getSelectionModel().getSelectedItem().equals("Equipos por año") && selectedFile != null) {
                JasperPrint print = JasperFillManager.fillReport("Informes/NFL_Equipo.jasper", parametros, conexion);
                JasperExportManager.exportReportToPdfFile(print, selectedFile.getAbsolutePath());
            }else if (reportComboBox.getSelectionModel().getSelectedItem().equals("Estadisticas de un jugador") && selectedFile != null) {
                JasperPrint print = JasperFillManager.fillReport("Informes/NFL_Jugador.jasper", parametros, conexion);
                JasperExportManager.exportReportToPdfFile(print, selectedFile.getAbsolutePath());
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}


