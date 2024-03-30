package Managers.GameStateManager;

public abstract class gameStates {

    protected gameStateManager gsm;

    public gameStates(gameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void init();
    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void draw();
    public abstract void dispose();

}
