package Objects;

import Tanks.tankManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygame.MyGame;

public class myTextBoxManager {

    private SpriteBatch sb;

    private GlyphLayout layout;

    private BitmapFont font;

    private FreeTypeFontGenerator gen;

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private myShapeRenderer sr;

    private Texture texture;

    float font_width;

    public myTextBoxManager(String fontPath) {

        sb = new SpriteBatch();

        layout = new GlyphLayout();

        font = new BitmapFont();

        gen = new FreeTypeFontGenerator(Gdx.files.internal("Exo-VariableFont_wght.ttf"));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        sr = new myShapeRenderer();

    }

    public void setTextBox(int center_x, int y, int padding, int margin, int borderWidth, int fontSize, String[] items,
                           int currentItem, Color col1, Color col2, Color col3) {

        parameter.size = fontSize;
        parameter.color = col1;
        parameter.spaceX = 3;
        parameter.borderWidth=borderWidth;
        parameter.borderColor = col1;

        font = gen.generateFont(parameter);

        font_width=0;

        int len = items.length;

        for (int i=0;i<len;i++) {
            layout.setText(font,items[i]);
            if (font_width< layout.width) font_width= layout.width;
        }

        // Text Box

        for (int i=0;i<len;i++) {

            if (i==0) {
                sr.roundedRect(center_x - font_width / 2 - padding,
                        y - layout.height - padding,
                        font_width + 2 * padding,
                        layout.height + 2 * padding,
                        10,
                        col3
                );
            }
            else {
                sr.roundedRect(center_x - font_width / 2 - padding,
                        y - margin*i - layout.height*(i+1) - padding,
                        font_width + 2 * padding,
                        layout.height + 2 * padding,
                        10,
                        col3
                );
            }
        }

        // TEXT

        sb.begin();

        for (int i=0;i<len;i++) {

            layout.setText(font,items[i]);

            if (currentItem==i) font.setColor(col2);
            else font.setColor(col1);

            if (i==0) {
                font.draw(sb,
                        items[i],
                        center_x - layout.width/2,
                        y
                );
            }
            else {
                font.draw(sb,
                        items[i],
                        center_x - layout.width/2,
                        y - (layout.height + margin)*i
                );
            }

        }

        sb.end();

    }

    public void setBackBox(String back, float x, float y, float width, float radius, float padding,
                           float padding2, boolean b, Color col1, Color col2, Color col3) {

        // Drawing Background Shape
        if (b) sr.halfRoundedRect(x,y,width,radius,padding2,col1,col2);
        else sr.halfRoundedRect(x,y,width,radius, padding,col1,col2);

        // Drawing Text
        if (b) parameter.size=(int)(radius+2*padding)*2;
        else parameter.size = (int)(radius+2*padding2)*2;
        parameter.color = col3;
        parameter.spaceX = 3;
        parameter.borderWidth=3;
        parameter.borderColor = col3;

        font = gen.generateFont(parameter);

        layout.setText(font, back);

        sb.begin();
        font.draw(sb,
                back,
                x + width - layout.width/2,
                y + layout.height/2 + radius
        );
        sb.end();

    }

    public void selectBox(boolean b, int currentTank, int size, Color col, tankManager tankManager) {

        // Center Circle
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (b) {
            sr.setColor(new Color(1F, 1F, 1F, 1F));
            sr.circle(MyGame.WIDTH * 4 / 5, MyGame.HEIGHT / 2, 45);
        }

        sr.setColor(new Color(0.292F,0.0F,0.507F,1F));
        sr.circle(MyGame.WIDTH*4/5,MyGame.HEIGHT/2,40);

        // Left Circle
        sr.setColor(new Color(0.292F,0.0F,0.507F,1F));
        sr.circle(MyGame.WIDTH*7/10 ,MyGame.HEIGHT/2,30);

        // Right Circle
        sr.setColor(new Color(0.292F,0.0F,0.507F,1F));
        sr.circle(MyGame.WIDTH*9/10 ,MyGame.HEIGHT/2,30);

        sr.end();

        tankManager.draw(currentTank,MyGame.WIDTH*4/5,MyGame.HEIGHT/2,40);
        tankManager.draw(currentTank-1,MyGame.WIDTH*7/10,MyGame.HEIGHT/2,30);
        tankManager.draw(currentTank+1,MyGame.WIDTH*9/10,MyGame.HEIGHT/2,30);

        // Left Arrow
        sb.begin();
        parameter.size = size;
        font = gen.generateFont(parameter);
        layout.setText(font,"<");
        font.setColor(col);
        font.draw(sb,"<",MyGame.WIDTH*3/5 + layout.width,MyGame.HEIGHT/2);

        // Right Arrow
        font.setColor(col);
        font.draw(sb,">",MyGame.WIDTH - 2*layout.width,MyGame.HEIGHT/2);

        sb.end();

    }

}
