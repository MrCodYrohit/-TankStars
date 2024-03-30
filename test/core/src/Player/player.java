package Player;

import Managers.InputManager.gameKeys;
import Objects.myObject;
import Objects.myShapeRenderer;
import Tanks.tank;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygame.MyGame;

public class player extends myObject {

    private playerManager playerManager;

    private boolean paused;

    private String name;
    private int playerNo;
    private tank tank;
    private float posX1;
    private float posX2;
    private float posY1;
    private float posY2;
    private float posX;
    private float posY;
    private float rotation; // deg
    private int health;
    private float fuel;
    private int cannonDir; // REmovee

    public player(String name, int playerNo) {
        this.name = name;
        this.playerNo = playerNo;
    }

    public void allotTank(tank tank) {
        this.tank = tank;
    }

    public void init(playerManager playerManager) {

        this.playerManager = playerManager;
        if (name.equals("Player 1")) posX1 = 200;

        else posX1 =480;
        posY1 = playerManager.getGround((int)posX1);

        tank.init(posX,posY);

        paused = false;

        rotation = 0;
        float min_r = 0;
        float r;
        float err = 100;
        for (r=-90;r<90;r+=0.1) {
            float x2 = (float) (posX1 + tank.getWidth()*Math.cos((rotation+r)*Math.PI/180));
            float y2 = (float) (posY1 + tank.getWidth()*Math.sin((rotation+r)*Math.PI/180));

            if (err > Math.abs(playerManager.getGround((int)x2)-y2)) {
                err = Math.abs(playerManager.getGround((int)x2)-y2);
                min_r = r;
            }
        }

        rotation+=min_r;
        posX2 = (float) (posX1 + tank.getWidth()*Math.cos((rotation)*Math.PI/180));
        posY2 = (float) (posY1 + tank.getWidth()*Math.sin((rotation)*Math.PI/180));

        posX = (posX1 + posX2)/2;
        posY = (posY1 + posY2)/2;

        tank.updateCannon(min_r,posX,posY);

        health = 100;
        fuel = 100;
        cannonDir = 0;

        setSize(Math.max(tank.getWidth(), tank.getHeight()));
        init();
    }

    public void pause() {
        paused = true;
    }

    public void update() {
        update(posX,posY,rotation);
    }

    public String getName() {
        return name;
    }

    public void updatePosition(int x) {

        float h = playerManager.getGround((int)posX1+x )
                - playerManager.getGround((int)posX1 );

        posX1 += x / Math.sqrt(1 + h*h);
        posY1 += h / Math.sqrt(1 + h*h);

        float min_r = 0;
        float r;
        float err = 100;
        for (r=-90;r<90;r+=0.1) {
            float x2 = (float) (posX1 + tank.getWidth()*Math.cos((rotation+r)*Math.PI/180));
            float y2 = (float) (posY1 + tank.getWidth()*Math.sin((rotation+r)*Math.PI/180));

            if (err > Math.abs(playerManager.getGround((int)x2)-y2)) {
                err = Math.abs(playerManager.getGround((int)x2)-y2);
                min_r = r;
            }
        }

        rotation+=min_r;
        posX2 = (float) (posX1 + tank.getWidth()*Math.cos((rotation)*Math.PI/180));
        posY2 = (float) (posY1 + tank.getWidth()*Math.sin((rotation)*Math.PI/180));

        posX = (posX1 + posX2)/2;
        posY = (posY1 + posY2)/2;

        tank.updateCannon(min_r,posX,posY);

        if (rotation>360) rotation-=360;
        else if (rotation<0) rotation+=360;

    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void updateFuel(int k) {
        if (k==0) fuel=100;
        if (k==1) fuel-=0.5;
    }

    public void updateHealth(float damage) {
        health-=(int)damage;
        if (health<=0) dead();
    }

    private void dead() {
        playerManager.playerDead(playerNo);
    }

    public void move(int gameKey) {
        if (fuel>0 && (int)posX1>10 && gameKey==gameKeys.LEFT && collidesWith(playerManager.players[1-playerNo])!=1) {  // FIX
            updatePosition(-1);
            updateFuel(1);
        }
        else if (fuel>0 && (int)posX2< MyGame.WIDTH-50 && gameKey==gameKeys.RIGHT && collidesWith(playerManager.players[1-playerNo])!=-1) {  // FIX
            updatePosition(1);
            updateFuel(1);
        }
    }

    public void moveCannon(int gameKey) {
        if (gameKey==gameKeys.UP) {  // FIX
            if (cannonDir<180) {
                cannonDir++;
                tank.rotateCannon(1);
            }
        }
        else if (gameKey==gameKeys.DOWN) {  // FIX
            if (cannonDir>0) {
                cannonDir--;
                tank.rotateCannon(-1);
            }
        }
    }

    public void fire() {
        tank.fire(playerManager.getDamageManager());
    }

    public void draw() {

        // draw tank
        tank.draw(posX1,posY1,30,rotation);
        ShapeRenderer sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.end();

        // draw health
        int k1 = 1-playerNo;
        int k2 = 2*playerNo-1;

        myShapeRenderer msr = new myShapeRenderer();
        msr.roundedRect(MyGame.WIDTH/2+k2*50-k1*200,450,
                205,20,5, Color.BROWN);

        if (health>0) msr.roundedRect(MyGame.WIDTH/2+k2*50-2*k1*health, 450,
                2*health+5, 20, 5, Color.RED);

    }

    public void drawFeatures() {

        // draw fuel
        myShapeRenderer msr = new myShapeRenderer();
        msr.roundedRect(100,50,105,20,5, Color.BROWN);
        if (fuel>0) msr.roundedRect(100, 50, fuel+5, 20, 5, Color.ORANGE);



    }

    public boolean animate(float deltaTime) {
        return tank.animate(deltaTime);
    }

}
