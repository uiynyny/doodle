package Paint;

import javax.swing.text.Element;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by temp user on 25/02/16.
 */
public class Elements implements Serializable {
    public Point p;
    public Color elemColor;
    public int strokeWidth;

    public Elements(){
    }

    public Elements(Point p, Color clr, int width){
        this.p=p;
        elemColor=clr;
        strokeWidth=width;
    }
}
