package Objects;

public class boundary {

    protected boolean[][] bdry;

    protected boundary(float size) {

        bdry = new boolean[(int) (3*size)][(int) (3*size)];
        for (int i=0;i<bdry.length;i++) {
            for (int j=0;j<bdry.length;j++) {
                bdry[i][j] = false;
            }
        }

    }

    protected void update(float posX, float posY, float rotation) {

        // Reset
        for (int i=0;i<bdry.length;i++) {
            for (int j=0;j<bdry.length;j++) {
                bdry[i][j] = false;
            }
        }

        // Update
        for (int i=-bdry.length/6;i<bdry.length/6;i++) {
            for (int j=0;j<bdry.length/3;j++) {
                int x=(int) (posX + i*Math.cos(rotation*Math.PI/180) - j*Math.sin(rotation*Math.PI/180));
                x= x -(int) (posX - bdry.length/2);
                int y=(int) (posY + i*Math.sin(rotation*Math.PI/180) + j*Math.cos(rotation*Math.PI/180));
                y= y -(int) (posY - bdry.length/2);
                bdry[x][y] = true;
            }
        }

    }

}
