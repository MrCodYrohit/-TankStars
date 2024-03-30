package Terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygame.MyGame;

import java.util.Random;

public class terrainGenerator {

    Random rand = new Random();

    private float[] heightMap;

    private float[] origin; // plain terrain
    private float[] height; //      ''
    private float[] length; //      ''

    private int n;
    private float r;

    private float terrainMaxHeight;
    private float terrainMinHeight;

    public float[] generateTerrain(int n, float r, float y1, float y2, float m1, float m2) {
        this.n = n;
        this.r = r;
        generateGround(y1, y2, m1, m2);
        generateGroundData();
        return this.heightMap;
    }

    public void draw(Color col) {
        newfillColor(col);
    }

    private void generateGround(float y1, float y2, float m1, float m2) {

        terrainMaxHeight = y2;
        terrainMinHeight = y1;

        origin = new float[n];
        height = new float[n];
        length = new float[n];

        float regionPerTerrain = MyGame.WIDTH/n;

        for (int i=0;i<n;i++) {

            // Length
            length[i] = rand.nextFloat(
                    regionPerTerrain / 2 - 10,
                    regionPerTerrain / 2 + 10
            );

            // Origin and Height
            if (i==0) {
                origin[i] = rand.nextFloat(
                        regionPerTerrain * i,
                        regionPerTerrain*i + regionPerTerrain/4
                );
                height[i] = rand.nextFloat(
                        y1,
                        y2
                );
                length[i] = rand.nextFloat(
                        regionPerTerrain / 2 - 10,
                        regionPerTerrain / 2 + 10
                );
            }

            else {
                origin[i] = rand.nextFloat(
                        regionPerTerrain * i,
                        regionPerTerrain*i + regionPerTerrain/4
                );

                float slope = rand.nextFloat(m1,m2);
                boolean k = rand.nextBoolean();
                if (k) slope*=-1;

                height[i] = height[i-1] + slope*(origin[i]-origin[i-1]);
                if (height[i]>y2 || height[i]<y1) height[i] = height[i-1] - slope*(origin[i]-origin[i-1]);
            }

        }

    }

    private void generateGroundData() {

        heightMap = new float[MyGame.WIDTH];

        for (int i=0;i<n;i++) {

            float _height;
            float _origin;
            float _length;
            float height;
            float origin;
            float length;
            float height_;
            float origin_;

            if (i==0) {
                _height = (terrainMinHeight+terrainMaxHeight)/2;
                _origin = 0;
                _length = 0;
                height = this.height[i];
                origin = this.origin[i];
                length = this.length[i];
                height_ = this.height[i+1];
                origin_ = this.origin[i+1];
            }
            else if (i==n-1) {
                height_ = (terrainMinHeight+terrainMaxHeight)/2;
                origin_ = MyGame.WIDTH;
                height = this.height[i];
                origin = this.origin[i];
                length = this.length[i];
                _height = this.height[i-1];
                _origin = this.origin[i-1];
                _length = this.length[i-1];
            }
            else {
                _height = this.height[i-1];
                _origin = this.origin[i-1];
                _length = this.length[i-1];
                height = this.height[i];
                origin = this.origin[i];
                length = this.length[i];
                height_ = this.height[i+1];
                origin_ = this.origin[i+1];
            }


            float slope = (_height-height) / (_origin + _length - origin);
            float ang = (float) (Math.atan(-Math.abs(slope)) * 180 / Math.PI) + 180F;
            float del = (float) (r / Math.tan(ang/2*Math.PI/180));

            if (i==0) {
                for (int j = 0; j < origin; j++) {
                    slope = (height - (terrainMinHeight + terrainMaxHeight) / 2) / origin;
                    heightMap[j] = height - slope * (origin - j);
                }
            }

            // Line with m=0
            for (int j=(int)(origin);j<origin+length;j++) {
                heightMap[j] = height;
            }

            // Previous curve around lines
            for (int j=0; j<(int)(r*Math.sin(ang*Math.PI/180)); j++) {
                System.out.println("(("+origin+", "+del+", "+j);
                heightMap[(int) (origin+del)-j] = height - (slope/Math.abs(slope))*( r - (float) Math.sqrt(r*r-j*j) );
            }

            slope = (height_-height) / (origin_ - origin - length);
            ang = (float) (Math.atan(-Math.abs(slope)) * 180 / Math.PI) + 180F;
            del = (float) (r / Math.tan(ang/2*Math.PI/180));

            // Line with m!=0
            for (int j=(int) (origin+length); j<origin_;j++) {
                heightMap[j] = height + slope * (j-origin-length);
            }

            // Next curve around lines
            for (int j =0; j<(int) (r*Math.sin(ang*Math.PI/180)); j++) {
                heightMap[(int) (origin+length-del)+j] = height + (slope/Math.abs(slope))*( r - (float) Math.sqrt(r*r-j*j) );
            }

        }

    }

    private void newfillColor(Color col) {

        Texture texture = new Texture(Gdx.files.internal("groundTex.png"));
        SpriteBatch sb = new SpriteBatch();
        sb.begin();

        for (int x=0;x<MyGame.WIDTH;x++) {
            sb.draw(texture,x,0,1,heightMap[x]);
        }

        sb.end();

    }

    private void drawGround(int n, float r) {

        ShapeRenderer sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(Color.BROWN);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        // Drawing lines with slope=0
        for (int i=0;i<n;i++) {
            sr.line(origin[i], height[i],
                    origin[i] + length[i], height[i]);
        }

        // Drawing lines with slope!=0
        sr.line(0,(terrainMaxHeight+terrainMinHeight)/2,origin[0],height[0]);
        for (int i=0;i<n-1;i++) {
            sr.line(origin[i]+length[i],height[i],origin[i+1],height[i+1]);
        }
        sr.line(origin[n-1]+length[n-1],height[n-1],MyGame.WIDTH,(terrainMaxHeight+terrainMinHeight)/2);


        sr.end();

    }

    private void fillColor(int n, float r) {

        ShapeRenderer sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);


        // Fill Color Inside

        sr.setColor(Color.BROWN);

        for (int i=0;i<n;i++) {
            for (int j=0;j<length[i];j+=1) {
                sr.rect(origin[i] + j,0,1,height[i]);
            }
        }

        float slope = (height[0] - (terrainMaxHeight+terrainMinHeight)/2) / origin[0];

        for (int i=0;i<origin[0];i++) {
            sr.rect(i,0,1,(terrainMaxHeight+terrainMinHeight)/2+slope*i);
        }

        for (int i=0;i<n-1;i++) {
            slope = (height[i+1] - height[i]) / (origin[i+1] - origin[i] - length[i]);
            for (int j=0;j<origin[i+1]-origin[i]-length[i];j++) {
                sr.rect(origin[i]+length[i]+j,0,1,height[i]+slope*j);
            }
        }

        slope = ((terrainMaxHeight+terrainMinHeight)/2 - height[n-1]) / (MyGame.WIDTH - origin[n-1] -
                length[n-1]);

        for (int i=0;i<MyGame.WIDTH-origin[n-1]-length[n-1];i++) {
            sr.rect(origin[n-1]+length[n-1]+i,0,1,height[n-1]+slope*i);
        }


        // Fill Outline Corners

        for (int i=0;i<n;i++) {
            
            float _height;
            float _origin;
            float _length;
            float height;
            float origin;
            float length;
            float height_;
            float origin_;

            if (i==0) {
                _height = (terrainMinHeight+terrainMaxHeight)/2;
                _origin = 0;
                _length = 0;
                height = this.height[i];
                origin = this.origin[i];
                length = this.length[i];
                height_ = this.height[i+1];
                origin_ = this.origin[i+1];
            }
            else if (i==n-1) {
                height_ = (terrainMinHeight+terrainMaxHeight)/2;
                origin_ = MyGame.WIDTH;
                height = this.height[i];
                origin = this.origin[i];
                length = this.length[i];
                _height = this.height[i-1];
                _origin = this.origin[i-1];
                _length = this.length[i-1];
            }
            else {
                _height = this.height[i-1];
                _origin = this.origin[i-1];
                _length = this.length[i-1];
                height = this.height[i];
                origin = this.origin[i];
                length = this.length[i];
                height_ = this.height[i+1];
                origin_ = this.origin[i+1];
            }

            if (_height>height) {

                float ang = (float) Math.atan(
                        (_height - height) /
                                (_origin + _length - origin)
                );
                ang = (float) (ang * 180 / Math.PI);
                if (ang<0) ang+=180;

                float del = (float) (r / Math.tan(ang/2*Math.PI/180));

                // Background sand
                sr.setColor(Color.BROWN);
                sr.arc((float) (origin + del),
                        height + r,
                        2*r,
                        ang + 90F,
                        180F - ang
                );

                // Foreground Sky
                sr.setColor(Color.BLUE);
                sr.arc((float) (origin + del),
                        height + r,
                        r,
                        ang + 90F,
                        180F - ang
                );
            }
            else {

                float ang = (float) Math.atan(
                        (height - _height) /
                                (_origin + _length - origin)
                );
                ang = (float) (ang * 180 / Math.PI);
                if (ang<0) ang+=180;

                float del = (float) (r / Math.tan(ang/2*Math.PI/180));

                // Background Sky
                sr.setColor(Color.BLUE);
                sr.arc((float) (origin + del),
                        height - r,
                        2*r,
                        90F,
                        180F - ang
                );

                // Foreground Sand
                sr.setColor(Color.BROWN);
                sr.arc((float) (origin + del),
                        height - r,
                        r,
                        90F,
                        180F - ang
                );
            }

            if (height>height_) {

                float ang = (float) Math.atan(
                        (height - height_) /
                                (origin + length - origin_)
                );
                ang = (float) (ang * 180 / Math.PI);
                if (ang<0) ang+=180;

                float del = (float) (r / Math.tan(ang/2*Math.PI/180));

                // Background Sky
                sr.setColor(Color.BLUE);
                sr.arc((float) (origin + length - del),
                        height - r,
                        2*r,
                        ang - 90F ,
                        180F - ang
                );

                // Foreground Sand
                sr.setColor(Color.BROWN);
                sr.arc((float) (origin + length - del),
                        height - r,
                        r,
                        ang - 90F ,
                        180F - ang
                );
            }
            else {

                float ang = (float) Math.atan(
                        (height_ - height) /
                                (length + origin - origin_)
                );
                ang = (float) (ang * 180 / Math.PI);
                if (ang<0) ang+=180;

                float del = (float) (r / Math.tan(ang/2*Math.PI/180));

                // Background sand
                sr.setColor(Color.BROWN);
                sr.arc((float) (origin + length - del),
                        height + r,
                        2*r,
                        270F ,
                        180F - ang
                );

                // Foreground Sky
                sr.setColor(Color.BLUE);
                sr.arc((float) (origin + length - del),
                        height + r,
                        r,
                        270F ,
                        180F - ang
                );
            }

        }


        sr.end();

    }

}
