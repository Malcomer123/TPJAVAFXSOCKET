module ma.enset.tpsocketjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens ma.enset.tpsocketjavafx to javafx.fxml;
    exports ma.enset.tpsocketjavafx;
}