module ma.uiass.eia.giefrontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens ma.uiass.eia.giefrontend to javafx.fxml;
    exports ma.uiass.eia.giefrontend;
}