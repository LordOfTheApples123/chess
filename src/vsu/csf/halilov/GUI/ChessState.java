package vsu.csf.halilov.GUI;

import vsu.csf.halilov.Game.Chess;
import vsu.csf.halilov.enums.GameState;
import vsu.csf.halilov.enums.PColor;

public class ChessState {
    private PColor currPlayer = PColor.WHITE;
    private PColor currCheck = null;
    private GameState gameState = GameState.PLAYING;

    public PColor getCurrPlayer() {
        return currPlayer;
    }

    public PColor getCurrCheck() {
        return currCheck;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setCurrPlayer(PColor currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void setCurrCheck(PColor currCheck) {
        this.currCheck = currCheck;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getStat(){
        if(gameState == GameState.MATE){
            return "Мат. Победа " + currPlayer.toString();
        }
        String res = "Ход " + currPlayer.toString();
        if(currCheck == currPlayer){
            res += ". Шах";
        }
        return res;
    }
}
