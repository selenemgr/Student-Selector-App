module testfall.selenemunoz_comp228testfall2023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens testfall.selenemunoz_comp228testfall2023 to javafx.fxml;
    exports testfall.selenemunoz_comp228testfall2023;
}