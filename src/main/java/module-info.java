<<<<<<< HEAD
module ma.uiass.eia.giefrontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens ma.uiass.eia.giefrontend to javafx.fxml;
    exports ma.uiass.eia.giefrontend;
}
=======
module com.example.giefrontend1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires okhttp3;
    requires com.google.gson;
    requires java.desktop;

    opens com.example.giefrontend1 to javafx.fxml;
    opens com.example.giefrontend1.Controllers.DTO;

    exports com.example.giefrontend1;
    exports com.example.giefrontend1.Controllers;
    exports com.example.giefrontend1.Controllers.Commercant;
    exports com.example.giefrontend1.Controllers.DTO;
    exports com.example.giefrontend1.Models;
    exports com.example.giefrontend1.Views;
    exports com.example.giefrontend1.Parser;
}
>>>>>>> origin/CRM
