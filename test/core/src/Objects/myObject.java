package Objects;

public class myObject {

    protected float size;
    protected float posX;
    protected float posY;
    protected boundary boundary;

    public void init() {
        boundary = new boundary(size);
    }

    public void setSize(float size) { this.size = size; }

    public void update(float posX, float posY, float rotation) {
        this.posX = posX;
        this.posY = posY;
        boundary.update(posX,posY,rotation);
    }

    public boolean intersects(int x, int y) {
        x-= (int) (posX - 3*size/2);
        y-= (int) (posY - 3*size/2);
        try { return  boundary.bdry[x][y]; }
        catch(Exception e) { return false; }
    }

    public int collidesWith(myObject obj) {

        for (int i=0;i<3*size;i++) {
            for (int j=0;j<3*size;j++) {
                if (boundary.bdry[i][j]) {
                    int x = i + (int) (posX - 3*size/2);
                    int y = j + (int) (posY - 3*size/2);
                    x-= obj.posX-3*obj.size/2;
                    y-= obj.posY-3*obj.size/2;
                    try {
                        if (obj.boundary.bdry[x][y]) {
                            if (posX>obj.posX) return 1;
                            else return -1;
                        }
                    } catch(Exception e) {}
                }
            }
        }
        return 0;

    }

}
