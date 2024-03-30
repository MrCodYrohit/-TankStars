package Objects;

public class myPhysicsManager {

    private myObject object;

    private float g;
    private float u;
    private float forceX;
    private float forceY;
    private boolean movable;

    public myPhysicsManager() {
        g = 0;
        forceX = 0;
        forceY = 0;
    }

    public void update() {
        updateForce();
    }

    private void updateForce() {
        if (!movable) {
            forceX = 0;
            forceY = 0;
            return;
        }
//        forceY += object.getMass() * (-g);
    }

    public void setGravity(float g) {
        this.g = g;
    }

    public void addObject(myObject object) {
        this.object = object;
    }



}
