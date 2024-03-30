package Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class tank_COALITION extends tank {

    private String name;
    private int width;

    private cannon cannon;

    private SpriteBatch sb;

    private Texture texture;

    private Sprite sprite;

    public tank_COALITION(String imgPath) {

        name = "COALITION";

        sb = new SpriteBatch();

        texture = new Texture(Gdx.files.internal(imgPath));

        sprite = new Sprite(texture,0,0,500,500);

    }

    public void init(float posX, float posY) {

    }

    public void rotateCannon(float dir) {

    }

    public void updateCannon(float dir, float x, float y) {

    }

    public void fire(damageManager damageManager) {
        cannon.fire(damageManager);
    }

    public void draw(float x, float y, int size, float rotation) {
        sb.begin();
        sprite.setPosition(x-25,y-25);
        sprite.setSize(size,size);
        sprite.draw(sb);
        sb.end();
    }

    public boolean animate(float deltaTime) {
        return true;
    }

    public String getName() {
        return name;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return 0;
    }

}

