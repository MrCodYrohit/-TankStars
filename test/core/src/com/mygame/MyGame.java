package com.mygame;

import Managers.InputManager.gameKeys;
import Managers.InputManager.keyManager;
import Managers.GameStateManager.gameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyGame extends ApplicationAdapter {
	public static int WIDTH = 800;
	public static int HEIGHT = 480;

	public static OrthographicCamera cam;

	private static gameStateManager gsm;

	@Override
	public void create () {
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();

		gsm = new gameStateManager();
		gsm.setGameState(0);

		Gdx.input.setInputProcessor(new keyManager());
	}

	@Override
	public void render () {

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();

		gameKeys.update();
	}
	
	@Override
	public void dispose () {

	}
}
