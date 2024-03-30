package Managers.GameStateManager;

import Player.playerManager;

public class gameStateManager {

    private int gameState;
    private gameStates state;
    public final int MENU = 0;
    public final int VSFRIEND = 1;
    public final int PLAY = 2;
    public final int ENDGAME = 3;
    private playerManager playerManager = new playerManager();

    public void update(float dt) {
        state.update(dt);
    }

    public void draw() {
        state.draw();
    }

    public void setGameState(int State) {

        if (state!=null) state.dispose();
        gameState = State;

        if (gameState==MENU) {
            state = new menuState(this);
        }
        else if (gameState==VSFRIEND) {
            state = new vsFriend(this, playerManager);
        }
        else if (gameState==PLAY) {
            state = new play(this, playerManager);
        }
        else if (gameState==ENDGAME) {
            state = new endGame(this);
        }

        state.init();
    }

}
