package Tanks;

import Player.player;
import Player.playerManager;
import Terrain.terrainManager;

public class damageManager {

    private playerManager playerManager;
    private terrainManager terrainManager;

    public damageManager(playerManager playerManager, terrainManager terrainManager) {
        this.playerManager = playerManager;
        this.terrainManager = terrainManager;
    }

    public player getPlayer(int playerNo) {
        return playerManager.getPlayer(playerNo);
    }

    public float getGround(int x) {
        return terrainManager.getGround(x);
    }

    public void updateGround(int x, float damage) {
        terrainManager.updateGround(x,damage);
    }

    public void doDamage(int damage, int x, int y, boolean p1Hit, boolean p2Hit) {

        if (p1Hit) {
            getPlayer(0).updateHealth(damage);
        }
        else {
            float p1Dist = (float) Math.sqrt(Math.pow(x - getPlayer(0).getPosX(), 2) +
                    Math.pow(y - getPlayer(0).getPosY(), 2));
            if (p1Dist < damage) getPlayer(0).updateHealth((int) (damage - p1Dist));
        }

        if (p2Hit) {
            getPlayer(1).updateHealth(damage);
        }
        else {
            float p2Dist = (float) Math.sqrt(Math.pow(x - getPlayer(1).getPosX(), 2) +
                    Math.pow(y - getPlayer(1).getPosY(), 2));
            if (p2Dist < damage) getPlayer(1).updateHealth((int) (damage - p2Dist));
        }

    }

}
