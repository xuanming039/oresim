package ore;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class Excavator extends Machine {



    public Excavator(GGBackground bg) {
        super(bg, "sprites/excavator.png");

    }




    boolean canMove(Location location)
    {
        // Test if try to move into border, rock or clay
        refresh(location);

        if (getC().equals(getBorderColor()) || getClay() != null || getPusher() != null || getBulldozer() != null || getExcavator() != null || getOre() != null)
            return false;
        else // Test if there is an ore
        {
            if (getRock() != null)
            {
                getRock().removeSelf();
                addRemovedCount();
            }
        }

        return true;
    }

}
