package Managers.InputManager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class keyManager extends InputAdapter {

    public boolean keyDown(int k) {
        if(k == Input.Keys.UP) {
            gameKeys.setKey(gameKeys.UP, true);
        }
        if(k == Input.Keys.LEFT) {
            gameKeys.setKey(gameKeys.LEFT, true);
        }
        if(k == Input.Keys.DOWN) {
            gameKeys.setKey(gameKeys.DOWN, true);
        }
        if(k == Input.Keys.RIGHT) {
            gameKeys.setKey(gameKeys.RIGHT, true);
        }
        if(k == Input.Keys.ENTER) {
            gameKeys.setKey(gameKeys.ENTER, true);
        }
        if(k == Input.Keys.ESCAPE) {
            gameKeys.setKey(gameKeys.ESC, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if(k == Input.Keys.UP) {
            gameKeys.setKey(gameKeys.UP, false);
        }
        if(k == Input.Keys.LEFT) {
            gameKeys.setKey(gameKeys.LEFT, false);
        }
        if(k == Input.Keys.DOWN) {
            gameKeys.setKey(gameKeys.DOWN, false);
        }
        if(k == Input.Keys.RIGHT) {
            gameKeys.setKey(gameKeys.RIGHT, false);
        }
        if(k == Input.Keys.ENTER) {
            gameKeys.setKey(gameKeys.ENTER, false);
        }
        if(k == Input.Keys.ESCAPE) {
            gameKeys.setKey(gameKeys.ESC, false);
        }
        return true;
    }

}
