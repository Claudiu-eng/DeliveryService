module mypack {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports mypack;
    exports mypack.controller;
    exports mypack.BusinessLayer;
    opens mypack to javafx.fxml;
    opens mypack.controller to javafx.fxml;
    opens mypack.BusinessLayer to javafx.fxml;

}