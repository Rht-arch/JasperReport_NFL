package org.example.jasperreport;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    public void initialize() {
        // Opciones del ComboBox
        reportComboBox.getItems().addAll("Información de equipos", "Información de jugadores");
        reportComboBox.getSelectionModel().selectFirst(); // Selección predeterminada

    }
    @FXML
    public void generateReport() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/nfl","root","");

            Map parametros = new HashMap();
            parametros.put("Estado",reportComboBox.getSelectionModel().getSelectedItem());

            JasperPrint print = JasperFillManager.fillReport("C:\\Users\\34658\\IdeaProjects\\JasperReport\\Informes\\NFL_Equipo.jasper", parametros , conexion);
            JasperExportManager.exportReportToPdfFile(print,"C:\\Users\\34658\\IdeaProjects\\JasperReport\\Informes\\NFL_Equipo.pdf");
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}