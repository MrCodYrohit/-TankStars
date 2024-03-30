package Player;

import Managers.GameStateManager.play;
import Managers.InputManager.gameKeys;
import Tanks.damageManager;
import Terrain.terrainManager;

public class playerManager {

    private terrainManager terrainManager;
    private damageManager damageManager;

    private play play;

    public player[] players; // change

    private boolean swtch = false;
    private boolean animation = false;

    private int currentPlayer;

    public playerManager() {
        players = new player[2];
        currentPlayer = 0;
    }

    public void setPlayer(player player) {
        if (currentPlayer==0) {
            players[0]=player;
            currentPlayer++;
        }
        else {
            players[1]=player;
            currentPlayer=0;
        }
    }

    public int getPlayerNo() {
        return currentPlayer;
    }

    public player getPlayer(int playerNo) {
        if (playerNo<players.length) return players[playerNo];
        else return null;
    }

    public void setTerrainManager(terrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    public void setDamageManager(damageManager damageManger) { this.damageManager = damageManger; }

    public damageManager getDamageManager() { return damageManager; }

    public void setPlayState(play play) { this.play = play; }

    public void init() {
        players[0].init(this);
        players[1].init(this);
    }

    public void update() {
        if (swtch && !animation) {
            swtch = false;
            updateTurn();
            players[0].updatePosition(0);
            players[1].updatePosition(0);
        }
        players[0].update();
        players[1].update();
    }

    public void handleInput() {
        if (gameKeys.isDown(gameKeys.LEFT)) {
            players[currentPlayer].move(gameKeys.LEFT);
        }
        if (gameKeys.isDown(gameKeys.RIGHT)) {
            players[currentPlayer].move(gameKeys.RIGHT);
        }
        if (gameKeys.isPressed(gameKeys.ENTER)) {
            players[currentPlayer].fire();
            swtch = true;
        }
        if (gameKeys.isDown(gameKeys.UP)) {
            players[currentPlayer].moveCannon(gameKeys.UP);
        }
        if (gameKeys.isDown(gameKeys.DOWN)) {
            players[currentPlayer].moveCannon(gameKeys.DOWN);
        }
        if (gameKeys.isPressed(gameKeys.ESC)) {
            players[currentPlayer].pause();
            play.pause();
        }
    }

    public void updateTurn() {
        currentPlayer = 1-currentPlayer;
        players[currentPlayer].updateFuel(0);
    }

    public void draw() {
        players[0].draw();
        players[1].draw();
        players[currentPlayer].drawFeatures();
    }

    public boolean animate(float deltaTime) {
        animation = players[currentPlayer].animate(deltaTime);
        return animation;
    }

    protected float getGround(int pos) {
        return terrainManager.getGround(pos);
    }

    public void playerDead(int playerNo) {
        play.end(players[1-playerNo].getName());
    }

}
