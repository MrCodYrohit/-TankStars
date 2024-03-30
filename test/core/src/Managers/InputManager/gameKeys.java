package Managers.InputManager;

public class gameKeys {

    private static final int NUMBER_OF_KEYS = 6;

    private static boolean[] keys = new boolean[NUMBER_OF_KEYS];
    private static boolean[] pkeys = new boolean[NUMBER_OF_KEYS];

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int ESC = 5;

    public static void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public static void update() {
        for(int i = 0; i < NUMBER_OF_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }

    public static boolean isDown(int k) {
        return keys[k];
    }

    public static boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

}
