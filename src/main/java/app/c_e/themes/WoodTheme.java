package app.c_e.themes;

import java.util.Objects;

public class WoodTheme extends Theme {

    public String getLightSquareStyle() {
        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/libra/lightwood.png")).toExternalForm();
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;";
    }

    @Override
    public String getDarkSquareStyle() {
        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/libra/darkwood.png")).toExternalForm();
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;";
    }

    @Override
    public String getLightPastStyle() {
        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/libra/lightwood.png")).toExternalForm();
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover; -fx-background-color: rgba(255, 255, 255, 0.7);";
    }

    @Override
    public String getDarkPastStyle() {
        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/libra/darkwood.png")).toExternalForm();
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover; -fx-background-color: rgba(255, 255, 255, 0.7);";
    }

    @Override
    public String getLastMoveLight() {
        if (isNoHighlighting()) {
            return "-fx-background-color: transparent;";
        }
        return "-fx-background-color: #d1f0d5;";
    }

    @Override
    public String getLastMoveDark() {
        if (isNoHighlighting()) {
            return "-fx-background-color: transparent;";
        }
        return "-fx-background-color: derive(#d1f0d5, -20%);";
    }

    @Override
    public String getHoveredXStyle() {
        if (isNoHighlighting()) {
            return "-fx-background-color: transparent;";
        }
        return "-fx-background-color: rgba(236,88,88,0.44);" + "-fx-background-radius: 0;";
    }

    @Override
    public String getHoveredStyle() {
        if (isNoHighlighting()) {
            return "-fx-background-color: transparent;";
        }
        return "-fx-background-color: #87CEEB80;" + "-fx-background-radius: 0;";
    }

    @Override
    public String getKingCheckedStyle() {
        return "-fx-background-color: rgba(255, 0, 0, 0.5);" + "-fx-background-radius: 0;";
    }

    @Override
    public String getBackGround() {
        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/libra/landscape.jpg")).toExternalForm();
        return "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;";
    }

    @Override
    public String getPiecesPath() {
        return "libra/";
    }

    @Override
    public String getLoginViewPaneStyle() {
        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/libra/woodwp.jpg")).toExternalForm();
        return "-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;";
    }

    @Override
    public String getSettingsStyle() {
        return getLoginViewPaneStyle();
    }
}
