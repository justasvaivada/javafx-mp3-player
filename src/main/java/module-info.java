module com.justasvaivada.javafxsimplelogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.kordamp.bootstrapfx.core;

    opens com.justasvaivada.javafxsimplelogin to javafx.fxml;
    exports com.justasvaivada.javafxsimplelogin;
}