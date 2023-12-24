package app.c_e.engine;

import app.c_e.util.StageMover;
import chessModel.Game;
import chessModel.GameHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MAIN extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GameHelper.initialize(Game.board);
        FXMLLoader fxmlLoader = new FXMLLoader(MAIN.class.getResource("/app/c_e/whiteBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Pane root = fxmlLoader.getRoot();
        stage.setTitle("Chess-Game");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        new StageMover(root, stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
