package ore;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class Pusher extends Machine {



    public Pusher(GGBackground bg) {
        super(bg, "sprites/pusher.png");

    }


    /**
     * Method to move pusher automatically based on the instructions input from properties file
     */

    boolean canMove(Location location)
    {
        // Test if try to move into border, rock or clay
        refresh(location);

        if (getC().equals(getBorderColor()) || getRock() != null || getClay() != null || getBulldozer() != null || getExcavator() != null)
            return false;
        else // Test if there is an ore
        {
            if (getOre() != null)
            {

                // Try to move the get ore
                getOre().setDirection(this.getDirection());
                if (moveOre(getOre())){

                    return true;
                }
                else
                    return false;
            }
        }

        return true;
    }
    private boolean moveOre(OreSim.Ore ore)
    {
        Location next = ore.getNextMoveLocation();
        // Test if try to move into border
        Color c = getBg().getColor(next);;
        OreSim.Rock rock = (OreSim.Rock)gameGrid.getOneActorAt(next, OreSim.Rock.class);
        OreSim.Clay clay = (OreSim.Clay)gameGrid.getOneActorAt(next, OreSim.Clay.class);
        if (c.equals(getBorderColor()) || rock != null || clay != null)
            return false;

        // Test if there is another ore
        OreSim.Ore neighbourOre =
                (OreSim.Ore)gameGrid.getOneActorAt(next, OreSim.Ore.class);
        if (neighbourOre != null)
            return false;

        // Reset the target if the ore is moved out of target
        Location currentLocation = ore.getLocation();
        List<Actor> actors = gameGrid.getActorsAt(currentLocation);
        if (actors != null) {
            for (Actor actor : actors) {
                if (actor instanceof OreSim.Target) {
                    OreSim.Target currentTarget = (OreSim.Target) actor;
                    currentTarget.show();
                    ore.show(0);
                }
            }
        }

        // Move the ore
        ore.setLocation(next);

        // Check if we are at a target
        OreSim.Target nextTarget = (OreSim.Target) gameGrid.getOneActorAt(next, OreSim.Target.class);
        if (nextTarget != null) {
            ore.show(1);
            nextTarget.hide();
        } else {
            ore.show(0);
        }

        return true;
    }
}
