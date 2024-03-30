package Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class tank_ABRAMS extends tank {

    private String name;

    private int height;
    private int width;

    private cannon cannon;

    private SpriteBatch sb;

    private Texture texture;

    private Sprite sprite;

    public tank_ABRAMS(String imgPath) {

        name = "ABRAMS";
        width = 30;
        height = 30;

        cannon = new cannon();

        sb = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("ABRAMS.png"));

        sprite = new Sprite(texture);
    }

    public void init(float posX, float posY) {
        cannon.init(posX, posY+height);
    }

    public void rotateCannon(float dir) {
        cannon.rotate(dir);
    }

    public void updateCannon(float dir, float posX, float posY) {
        cannon.update(dir, posX, posY, height);
    }

    public void fire(damageManager damageManager) { cannon.fire(damageManager); }

    public void draw(float x, float y, int size, float rotation) {

        // Draw Tank
        sprite.setOrigin(0,0);
        sprite.setPosition(x,y);
        sprite.setSize(size,size);
        sprite.setRotation(rotation);
        sb.begin();
        sprite.draw(sb);
        sb.end();

        // Draw Cannon
        cannon.draw();
    }

    public boolean animate(float deltaTime) {
        return cannon.animate(deltaTime);
    }

    public String getName() {
        return name;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
