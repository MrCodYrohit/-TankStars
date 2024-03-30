package Tanks;

public abstract class tank {

    public abstract void init(float posX, float posY);
    public abstract void rotateCannon(float dir);
    public abstract void updateCannon(float dir, float x, float y);
    public abstract void fire(damageManager damageManager) ;
    public abstract void draw(float x, float y, int size, float rotation);
    public abstract boolean animate(float deltaTime);
    public abstract String getName();
    public abstract float getWidth();
    public abstract float getHeight();

}
