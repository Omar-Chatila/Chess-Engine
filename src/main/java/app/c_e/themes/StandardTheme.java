package app.c_e.themes;

public class StandardTheme extends Theme {
    public String getLightSquareStyle() {
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-radius: 0;" + "-fx-background-color: #DEE3E6;";
    }

    @Override
    public String getDarkSquareStyle() {
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-radius: 0;" + "-fx-background-color: #8CA2AD;";
    }

    @Override
    public String getLightPastStyle() {
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-radius: 0;" + "-fx-background-color: #E3E9EC;";
    }

    @Override
    public String getDarkPastStyle() {
        return "-fx-background-insets: 0; -fx-border-width: 0;" + "-fx-background-radius: 0;" + "-fx-background-color: #A9B4BD;";
    }

    @Override
    public String getLastMoveLight() {
        /* Green color */
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
        return "-fx-background-color: linear-gradient(to bottom, #1E3050, #1E3050E0); -fx-background-size: cover;";
    }

    @Override
    public String getPiecesPath() {
        return "standard/";
    }

    @Override
    public String getLoginViewPaneStyle() {
        return "-fx-background-color:  #1E3050;";
    }

    @Override
    public String getSettingsStyle() {
        return getLoginViewPaneStyle();
    }
}
