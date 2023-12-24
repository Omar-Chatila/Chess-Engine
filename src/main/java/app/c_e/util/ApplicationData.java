package app.c_e.util;


import chessModel.ChessboardController;


import java.util.Arrays;

public class ApplicationData {
    private static final ApplicationData instance = new ApplicationData();

    private ChessboardController chessboardController;
    private boolean illegalMove;
    private String promotedPiece;


    private ApplicationData() {
    }

    public static ApplicationData getInstance() {
        return instance;
    }



    public ChessboardController getChessboardController() {
        return chessboardController;
    }

    public void setChessboardController(ChessboardController chessboardController) {
        this.chessboardController = chessboardController;
    }

    public boolean isIllegalMove() {
        return illegalMove;
    }

    public void setIllegalMove(boolean illegalMove) {
        this.illegalMove = illegalMove;
    }



    public String getPromotedPiece() {
        return promotedPiece;
    }

    public void setPromotedPiece(String promotedPiece) {
        this.promotedPiece = promotedPiece;
    }





}
