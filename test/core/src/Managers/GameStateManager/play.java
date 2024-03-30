package Managers.GameStateManager;

import Player.playerManager;
import Tanks.damageManager;
import Terrain.terrainManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.MyGame;

public class play extends gameStates {

    private terrainManager terrainManager;
    private playerManager playerManager;
    private damageManager damageManager;

    private boolean paused;

    private boolean animation;

    private SpriteBatch sb;

    private Texture texture;

    private Sprite sprite;

    public play(gameStateManager gsm, playerManager playerManager) {
        super(gsm);
        this.playerManager = playerManager;
    }

    @Override
    public void init() {
        texture = new Texture(Gdx.files.internal("bg_blue.png"));
        sb = new SpriteBatch();

        animation = false;

        paused = false;

        terrainManager = new terrainManager();
        terrainManager.generateTerrain(6,20,150,300,0.3F,0.5F);

        damageManager = new damageManager(playerManager,terrainManager);

        playerManager.setDamageManager(damageManager);
        playerManager.setTerrainManager(terrainManager);
        playerManager.setPlayState(this);
        playerManager.init();

    }

    @Override
    public void update(float dt) {
        ScreenUtils.clear(Color.BLUE);
        sb.begin();
        sb.draw(texture,0,0, MyGame.WIDTH,MyGame.HEIGHT);
        sb.end();
        animation = playerManager.animate(dt);
        if (!animation) {
            playerManager.update();
            handleInput();
        }
    }

    public void pause() {
        paused = true;
    }

    public void end(String winnerName) {
        gsm.setGameState(3);
        dispose();
    }

    @Override
    public void handleInput() {
        playerManager.handleInput();
    }

    @Override
    public void draw() {
        terrainManager.draw(Color.BROWN);
        playerManager.draw();
        if (paused) {

        }
    }

    @Override
    public void dispose() {

    }

}
