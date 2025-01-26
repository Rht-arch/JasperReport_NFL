module org.example.jasperreport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;


    opens org.example.jasperreport to javafx.fxml;
    exports org.example.jasperreport;
}