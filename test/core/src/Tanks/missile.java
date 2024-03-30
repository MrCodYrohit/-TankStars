package Tanks;

import Objects.myObject;
import Player.player;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class missile extends myObject {

    private damageManager damageManager;
    private int damage;
    private boolean p1Hit;
    private boolean p2Hit;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float rotation;
    private int pathDir;
    private boolean animate;
    private boolean doDamage;
    private final float gravity = 3F;
    private final int time = 7;

    public missile(float posX, float posY, damageManager damageManager) {
        this.damageManager = damageManager;
        animate = false;
        doDamage = false;
        this.x=posX;
        this.y=posY;
        setSize(5);
        damage=20;
        init();
        update(x,y,rotation);
    }

    public void fire(int v, float ang) {

        float vx = (float) (v * Math.cos(ang*Math.PI/180));
        float vy = (float) (v * Math.sin(ang*Math.PI/180));

        this.vx = vx;
        this.vy = vy;

        if (vx>=0) pathDir=1;
        else pathDir=-1;

        p1Hit=false;
        p2Hit=false;

        animate = true;
        doDamage = true;

    }

    public boolean hitPlayer(player player) {
        return collidesWith(player)!=0;
    }

    public boolean hitGround(int x, int y) {
        return y<=damageManager.getGround(x);
    }

    public void doDamage() {

        for (int i=-damage; i<damage; i++) {
            for (int j = (int) Math.sqrt(damage*damage-i*i); j>-Math.sqrt(damage*damage-i*i); j--) {
                if (hitGround((int)posX+i,(int)posY+j)) {
                    damageManager.updateGround((int)posX+i, (float) (Math.sqrt(damage*damage-i*i)+j)/2);
                    break;
                }
            }
        }
        damageManager.doDamage(damage,(int)posX,(int)posY,p1Hit,p2Hit);
    }

    public void draw() {
        ShapeRenderer sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x,y,size);
        sr.end();
    }

    public boolean animate(float deltaTime) {
        if (animate) {
            x+=vx*time*deltaTime;
            y+=vy*time*deltaTime-0.5*gravity*time*time*deltaTime;
            vy-=gravity*time*deltaTime;
            update(x,y,rotation);
            draw();
            if (hitGround((int)posX,(int)posY)) {
                animate = false;
            }
            if (hitPlayer(damageManager.getPlayer(0))) {
                p1Hit=true;
            }
            if (hitPlayer(damageManager.getPlayer(1))) {
                p2Hit=true;
            }
            if (p1Hit||p2Hit) animate=false;

        }
        else if (doDamage) {
            doDamage = false;
            doDamage();
        }
        return animate||doDamage;
    }

}
