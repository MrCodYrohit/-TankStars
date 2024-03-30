package Tanks;

import Managers.InputManager.gameKeys;
import Objects.myTextBoxManager;
import Player.player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.MyGame;

public class tankManager {

    private ShapeRenderer sr;
    private SpriteBatch sb;

    private myTextBoxManager tbm;

    private int tankNo;
    private tank[] tanks;
    private int no_of_tanks;

    public tankManager() {

        tbm = new myTextBoxManager("Exo-VariableFont_wght.ttf");

        sr = new ShapeRenderer();
        sb = new SpriteBatch();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("Exo-VariableFont_wght.ttf")
        );

        tankNo = 0;
        no_of_tanks = 3;

        tanks = new tank[no_of_tanks];
        tanks[0] = new tank_ABRAMS("tank.png");
        tanks[1] = new tank_COALITION("badlogic.jpg");
        tanks[2] = new tank_ABRAMS("wings.jpg");

    }

    public void allotTank(player player) {
        player.allotTank(tanks[tankNo]);
    }

    public void draw(int tankNo, float x, float y, int size) {
        if (tankNo<0) tankNo=no_of_tanks-1;
        else if (tankNo>=no_of_tanks) tankNo=0;
        tanks[tankNo].draw(x,y,size,0);
    }

    public String getTankName() {
        return tanks[tankNo].getName();
    }

    public int getNo_of_tanks() {
        return no_of_tanks;
    }

    public void setTankNo(int tankNo) {
        this.tankNo = tankNo;
    }

}
