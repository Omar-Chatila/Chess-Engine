module app.c_e {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires java.base;
    requires org.junit.jupiter.api;
    requires org.testng;

    opens app.c_e to javafx.fxml;
    //exports app.c_e;
    exports app.c_e.engine;
    exports app.c_e.tests;
    exports chessModel;
}