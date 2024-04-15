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
    private boolean moveOre(Ore ore)
    {
        Location next = ore.getNextMoveLocation();
        // Test if try to move into border
        Color c = getBg().getColor(next);;
        Rock rock = (Rock)gameGrid.getOneActorAt(next, Rock.class);
        Clay clay = (Clay)gameGrid.getOneActorAt(next, Clay.class);
        Machine machine= (Machine)gameGrid.getOneActorAt(next, Machine.class);
        if (c.equals(getBorderColor()) || rock != null || clay != null || machine != null)
            return false;

        // Test if there is another ore
        Ore neighbourOre =
                (Ore)gameGrid.getOneActorAt(next, Ore.class);
        if (neighbourOre != null)
            return false;

        // Reset the target if the ore is moved out of target
        Location currentLocation = ore.getLocation();
        List<Actor> actors = gameGrid.getActorsAt(currentLocation);
        if (actors != null) {
            for (Actor actor : actors) {
                if (actor instanceof Target) {
                    Target currentTarget = (Target) actor;
                    currentTarget.show();
                    ore.show(0);
                }
            }
        }

        // Move the ore
        ore.setLocation(next);

        // Check if we are at a target
        Target nextTarget = (Target) gameGrid.getOneActorAt(next, Target.class);
        if (nextTarget != null) {
            ore.show(1);
            nextTarget.hide();
        } else {
            ore.show(0);
        }

        return true;
    }
}
