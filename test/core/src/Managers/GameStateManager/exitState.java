package Managers.GameStateManager;

import Managers.InputManager.gameKeys;
import Objects.myShapeRenderer;
import Objects.myTextBoxManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class exitState {

    private myShapeRenderer msr;

    private myTextBoxManager mtb;

    private String[] options;
    private int currentOption;

    public exitState() {

        msr = new myShapeRenderer();

        mtb = new myTextBoxManager("Exo-VariableFont_wght.ttf");

        options = new String[] {"YES","NO"};
        currentOption = 0;

    }

    public boolean update() {
        return handleInput();
    }

    public boolean handleInput() {

        if (gameKeys.isPressed(gameKeys.DOWN) && currentOption==0) currentOption=1;
        else if (gameKeys.isPressed(gameKeys.UP) && currentOption==1) currentOption=0;
        else if (gameKeys.isPressed(gameKeys.ENTER)) {
            if (currentOption==0) Gdx.app.exit();
            else return false;
        }
        return true;

    }

    public void draw() {

        // Exit Background
        msr.roundedRect(100,100,500,300,10, Color.TEAL);

        // Option Boxes
        mtb.setTextBox(350,300,20,50,2,15,options,
                currentOption,Color.WHITE,Color.TEAL,Color.CYAN);

    }

}
