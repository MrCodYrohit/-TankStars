package Tanks;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class cannon {

    private float posX;
    private float posY;
    private float absDir;
    private float relDir;
    private int width=3;
    private int length=20;
    private missile m1;

    public void init(float posX, float posY) {
        absDir = 0;
        relDir = 0;
        this.posX = posX;
        this.posY = posY;
    }

    public void update(float rotation, float x, float y, float tankHeight) {

        absDir+=rotation;
        if (absDir>360) absDir-=360;
        else if (absDir<0) absDir+=360;

        posX = x - (float) (tankHeight * Math.sin((absDir-relDir)*Math.PI/180));
        posY = y + (float) (tankHeight * Math.cos((absDir-relDir)*Math.PI/180));

    }

    public void rotate(float dir) {
        this.relDir+=dir;
        this.absDir+=dir;
    }

    public void fire(damageManager damageManager) {
        m1 = new missile(posX+(float)(length*Math.cos(absDir*Math.PI/180)),
                posY+(float)(length*Math.sin(absDir*Math.PI/180)),
                damageManager);
        m1.fire(30,absDir);
    }

    public void draw() {
        ShapeRenderer sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(posX,posY,width/2,width/2,length,width,1,1,absDir);
        sr.end();
    }

    public boolean animate(float deltaTime) {
        if (m1==null) return false;
        return m1.animate(deltaTime);
    }

}
