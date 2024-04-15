package ore;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class Bulldozer extends Machine {


    public Bulldozer(GGBackground bg) {
        super(bg, "sprites/bulldozer.png");

    }




    boolean canMove(Location location)
    {
        // Test if try to move into border, rock or clay
        refresh(location);

        if (getC().equals(getBorderColor()) || getRock() != null || getPusher() != null || getBulldozer() != null || getExcavator() != null || getOre() != null)
            return false;
        else // Test if there is an ore
        {
            if (getClay() != null)
            {
                getClay().removeSelf();
                addRemovedCount();
            }
        }

        return true;
    }

}
