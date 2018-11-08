package neuralnets.visualization;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.List;

public class Pixel {

    public final int x;
    public final int y;
    public final Color color;

    public Pixel(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        color = c;
    }



    @Override
    public String toString() {
        return "x=" + x + ", y=" + y +", color=" + color.toString();
    }
}
