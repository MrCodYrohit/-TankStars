package Terrain;

import com.badlogic.gdx.graphics.Color;

public class terrainManager {

    private float[] heightMap;

    private terrainGenerator terrainGenerator;

    public terrainManager() {
        terrainGenerator = new terrainGenerator();
    }

    public void generateTerrain(int n, float r, float y1, float y2, float m1, float m2) {
        this.heightMap = terrainGenerator.generateTerrain(n,r,y1, y2, m1, m2);
    }

    public void draw(Color col) {
        terrainGenerator.draw(col);
    }

    public float getGround(int x) {
        return heightMap[x];
    }

    public void updateGround(int x, float damage) {
        heightMap[x]-=damage;
    }

//    public void printGround() {
//        for (int i=0;i<heightMap.length;i++) {
//            System.out.println(i + " -> " + heightMap[i]);
//        }
//    }

}
