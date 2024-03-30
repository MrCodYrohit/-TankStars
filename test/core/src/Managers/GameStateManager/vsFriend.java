package Managers.GameStateManager;

import Managers.InputManager.gameKeys;
import Objects.myShapeRenderer;
import Objects.myTextBoxManager;
import Player.player;
import Player.playerManager;
import Tanks.tankManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.MyGame;

public class vsFriend extends gameStates {

    private playerManager playerManager;
    private Player.player player;

    private static final int gameState = 1;

    Color bgColor;

    private SpriteBatch sb;

    private BitmapFont titleFont;
    private BitmapFont optionFont;

    GlyphLayout layout = new GlyphLayout();

    private float title_width;
    private float options_width;
    private float font_height;

    private String title;

    private int currentTank;

    private int no_of_options;
    private String[] options;
    private int currentOption;

    private Sprite sprite;
    private Texture menuBg;

    private ShapeRenderer sr;
    private myTextBoxManager tbm;

    private Tanks.tankManager tankManager;

    public vsFriend(gameStateManager gsm, playerManager playerManager) {
        super(gsm);
        this.playerManager = playerManager;
    }

    @Override
    public void init() {

        player = new player("Player " + (playerManager.getPlayerNo()+1),
                playerManager.getPlayerNo());

        bgColor = Color.MAROON;

        currentTank = 0;

        tankManager = new tankManager();

        title = tankManager.getTankName();

        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        tbm = new myTextBoxManager("Exo-VariableFont_wght.ttf");

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("Exo-VariableFont_wght.ttf")
        );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter_title =
                new FreeTypeFontGenerator.FreeTypeFontParameter(),
                parameter_option = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter_title.size = 25;
        parameter_title.color = Color.ORANGE;
        parameter_title.spaceX = 2;
        parameter_title.borderWidth=3;
        parameter_title.borderColor=Color.ORANGE;

        parameter_option.size = 15;
        parameter_option.color = Color.BROWN;
        parameter_option.spaceX = 2;
        parameter_option.borderWidth=1;
        parameter_option.borderColor=Color.BROWN;

        titleFont = gen.generateFont(parameter_title);
        optionFont = gen.generateFont(parameter_option);

        layout.setText(titleFont, title);
        title_width = layout.width;

        layout.setText(titleFont,title);
        font_height = layout.height;

        menuBg = new Texture("29699681_7566512.jpg");

        int playerNo = 0;

        if (playerManager.getPlayerNo()==0) options = new String[] {"<","TANKS","CHOOSE","UPGRADE"};
        else options = new String[] {"<","TANKS","PLAY","UPGRADE"};
        no_of_options = options.length;
        currentOption = 1;

        for (int i=2;i<no_of_options;i++) {
            layout.setText(optionFont,options[i]);
            if (options_width< layout.width) options_width = layout.width;
        }
        font_height = layout.height;

    }

    @Override
    public void update(float dt) {

        title = tankManager.getTankName();

        ScreenUtils.clear(bgColor);
        handleInput();

    }

    @Override
    public void handleInput() {

        if (gameKeys.isPressed(gameKeys.UP)) {
            if(currentOption>0) currentOption-=1;
        }
        else if (gameKeys.isPressed(gameKeys.DOWN)) {
            if (currentOption<no_of_options-1) currentOption+=1;
        }
        else if (currentOption==1 && gameKeys.isPressed(gameKeys.LEFT)) {
            if (currentTank==0) currentTank=tankManager.getNo_of_tanks()-1;
            else currentTank--;
            tankManager.setTankNo(currentTank);
        }
        else if (currentOption==1 && gameKeys.isPressed(gameKeys.RIGHT)) {
            if (currentTank==tankManager.getNo_of_tanks()-1) currentTank=0;
            else currentTank++;
            tankManager.setTankNo(currentTank);
        }
        else if (gameKeys.isPressed(gameKeys.ENTER)) {
            select();
        }

    }

    private void select() {
        if (currentOption==0) {
            if (playerManager.getPlayerNo()==0) gsm.setGameState(gsm.MENU);
            else {
                playerManager.setPlayer(player);
                init();
            }
        }
        else if (currentOption==2) {
            tankManager.allotTank(player);
            playerManager.setPlayer(player);
            if (playerManager.getPlayerNo()==1) init();
            else gsm.setGameState(gsm.PLAY);
        }
        else if (currentOption==3) {
            // come here
        }
    }

    @Override
    public void draw() {

        sb.setProjectionMatrix(MyGame.cam.combined);

        sb.begin();

        //MENU BACKGROUND
        sb.draw(menuBg,MyGame.WIDTH*3/5,0,MyGame.WIDTH*2/5,MyGame.HEIGHT);

        // TITLE
        titleFont.draw(sb,title, (MyGame.WIDTH*3/5-title_width)/2, MyGame.HEIGHT-100);

        // OPTIONS TEXT
        String[] arr = new String[no_of_options-2];
        for (int i=2;i<no_of_options;i++) {arr[i-2]=options[i];}
        tbm.setTextBox(MyGame.WIDTH*4/5,150,20,70, 2,15,
                arr,currentOption-2,Color.ORANGE,Color.WHITE,Color.YELLOW);

        sb.end();

        // RENDERING SHAPES
        Gdx.gl.glEnable(GL30.GL_BLEND);
            // back
        tbm.setBackBox("<",0,400,50,25,5,2,currentOption==0,new Color
                (0.292F,0.0F,0.507F,0.6F),new Color(0.574F,0.717F,0.9F,1F),
                new Color(1,1,1,1F));
            //
        Gdx.gl.glDisable(GL30.GL_BLEND);

        // Title Box
        tbm.setTextBox(MyGame.WIDTH*4/5,MyGame.HEIGHT*3/4,10,0,1,
                10, new String[]{"CHOOSE TANK"},-1,new Color(1F,1F,1F,1F),
                new Color(1F,1F,1F,1F), new Color(0.292F,0.0F,0.507F,1F)
        );

        // Draw Tank Holders
        tbm.selectBox(currentOption==1, currentTank, 30, new Color(1F,1F,1F,1F),tankManager);

    }

    @Override
    public void dispose() {

    }

}
