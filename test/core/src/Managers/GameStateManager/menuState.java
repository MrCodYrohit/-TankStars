package Managers.GameStateManager;

import Managers.InputManager.gameKeys;
import Objects.myTextBoxManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.MyGame;

public class menuState extends gameStates {

    private static final int gameState = 0;

    private exitState exitState;
    boolean exit;

    Color bgColor;

    private SpriteBatch sb;

    private BitmapFont titleFont1;
    private BitmapFont titleFont2;
    private BitmapFont font;

    GlyphLayout layout = new GlyphLayout();

    private float title_width1;
    private float title_width2;
    private float font_width;
    private float font_height;

    private final String title1 = "Tank";
    private final String title2 = "Stars";

    private int no_of_items;
    private int currentItem;
    private String[] menuItems;

    private Texture menuBg;

    private myTextBoxManager tbm;

    public menuState(gameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        bgColor = Color.MAROON;
        currentItem = 0;

        exitState = new exitState();
        exit = false;

        sb = new SpriteBatch();
        tbm = new myTextBoxManager("Exo-VariableFont_wght.ttf");

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("Exo-VariableFont_wght.ttf")
        );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter_title1 =
                new FreeTypeFontGenerator.FreeTypeFontParameter(),
                parameter_title2 = new FreeTypeFontGenerator.FreeTypeFontParameter(),
                parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter_title1.size = 50;
        parameter_title1.color = Color.ORANGE;
        parameter_title1.spaceX = 2;
        parameter_title1.borderWidth=5;
        parameter_title1.borderColor=Color.ORANGE;

        parameter_title2.size = 35;
        parameter_title2.color = Color.ORANGE;
        parameter_title2.spaceX = 3;
        parameter_title2.borderWidth=3;
        parameter_title2.borderColor=Color.ORANGE;

        parameter.size = 12;
        parameter.color = Color.BROWN;
        parameter.spaceX = 3;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BROWN;

        titleFont1 = gen.generateFont(parameter_title1);
        titleFont2 = gen.generateFont(parameter_title2);
        font = gen.generateFont(parameter);

        layout.setText(titleFont1, title1);
        title_width1 = layout.width;
        layout.setText(titleFont2, title2);
        title_width2 = layout.width;

        menuItems = new String[] {"VS FRIEND","EXIT"};
        no_of_items = menuItems.length;

        for (int i=0;i<no_of_items;i++) {
            layout.setText(font,menuItems[i]);
            if (font_width< layout.width) font_width = layout.width;
        }
        font_height = layout.height;

        menuBg = new Texture("29699681_7566512.jpg");

    }

    public void update(float dt) {

        if (exit) {
            exit = exitState.update();
            return;
        }

        ScreenUtils.clear(bgColor);
        handleInput();
    }

    public void handleInput() {

        if (gameKeys.isPressed(gameKeys.UP)) {
            if(currentItem==0) currentItem=no_of_items-1;
            else currentItem-=1;
        }
        else if (gameKeys.isPressed(gameKeys.DOWN)) {
            if (currentItem==no_of_items-1) currentItem=0;
            else currentItem+=1;
        }
        else if (gameKeys.isPressed(gameKeys.ENTER)) {
            select();
        }

    }

    private void select() {
        if (currentItem==0) {
            gsm.setGameState(gsm.VSFRIEND);
        }
        else if (currentItem==1) {
//            Gdx.app.exit();
            exit = true;
        }
    }

    public void draw() {

        sb.setProjectionMatrix(MyGame.cam.combined);

        sb.begin();

        //MENU BACKGROUND
        sb.draw(menuBg,MyGame.WIDTH*3/5,0,MyGame.WIDTH*2/5,MyGame.HEIGHT);

        // TITLE
        titleFont1.draw(sb,title1, (MyGame.WIDTH*3/5-title_width1)/2, MyGame.HEIGHT-100);
        titleFont2.draw(sb,title2, (MyGame.WIDTH*3/5-title_width2)/2, MyGame.HEIGHT-150);

        // MENU
        tbm.setTextBox(MyGame.WIDTH*4/5,250,15,100, 2,15,
                menuItems,currentItem,Color.ORANGE,Color.WHITE,Color.YELLOW);

        sb.end();

        if (exit) exitState.draw();

    }

    @Override
    public void dispose() {

    }

}
