package org.example.jasperreport;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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

            Map parametros = new HashMap();
            parametros.put("equipo", teamComboBox.getSelectionModel().getSelectedItem());
            parametros.put("draft", yearTextField.getText());
            parametros.put("jugador", playerNameTextField.getText());
            if(reportComboBox.getSelectionModel().getSelectedItem().equals("Equipos por año")) {
                JasperPrint print = JasperFillManager.fillReport("/home/alumno/NFL/Informes/NFL_Equipo.jasper", parametros, conexion);
                JasperExportManager.exportReportToPdfFile(print, "/home/alumno/NFL/Informes/NFL_Equipo.pdf");
            }else if (reportComboBox.getSelectionModel().getSelectedItem().equals("Estadisticas de un jugador")) {
                JasperPrint print = JasperFillManager.fillReport("/home/alumno/NFL/Informes/NFL_Jugador.jasper", parametros, conexion);
                JasperExportManager.exportReportToPdfFile(print, "/home/alumno/NFL/Informes/NFL_Jugador.pdf");
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}