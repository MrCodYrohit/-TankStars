package Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class myShapeRenderer extends ShapeRenderer {

    public void roundedRect(float x, float y, float width, float height, float radius, Color col){

        super.setAutoShapeType(true);
        super.begin(ShapeRenderer.ShapeType.Filled);

        // Color
        super.setColor(col);

        // Central rectangle
        super.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

        // Four side rectangles, in clockwise order
        super.rect(x + radius, y, width - 2*radius, radius);
        super.rect(x + width - radius, y + radius, radius, height - 2*radius);
        super.rect(x + radius, y + height - radius, width - 2*radius, radius);
        super.rect(x, y + radius, radius, height - 2*radius);

        // Four arches, clockwise too
        super.arc(x + radius, y + radius, radius, 180f, 90f);
        super.arc(x + width - radius, y + radius, radius, 270f, 90f);
        super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        super.arc(x + radius, y + height - radius, radius, 90f, 90f);

        super.end();
    }

    public void halfRoundedRect(float x, float y, float width, float radius, float padding, Color col1, Color col2) {

        super.setAutoShapeType(true);
        super.begin(ShapeRenderer.ShapeType.Filled);

        // Color
        super.setColor(col1);

        // Rectangle
        super.rect(x, y, width, 2*radius);

        // Arch
        super.arc(x + width, y + radius, radius, 270f, 180f);

        // Color
        super.setColor(col2);

        // Circle
        super.circle(x + width, y + radius, radius - padding);

        super.end();
    }

}
