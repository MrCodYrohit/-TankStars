package Managers.GameStateManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class endGame extends gameStates {

    private Color bgColor;
    private int currentItem;

    public endGame(gameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        bgColor = Color.MAROON;
        currentItem = 0;
    }

    @Override
    public void update(float dt) {
        ScreenUtils.clear(bgColor);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void dispose() {

    }
}
