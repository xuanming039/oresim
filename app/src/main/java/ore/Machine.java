package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import ore.OreSim;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class  Machine extends Actor {

    private Color c;
    private Rock rock;
    private Clay clay;
    private Bulldozer bulldozer;
    private Excavator excavator;
    private Pusher pusher;
    private Ore ore;
    private int removedCount;
    private int moveStepCount;
    private List<String> controls = null;
    private int autoMovementIndex = 0;
    private final Color borderColor = new Color(100, 100, 100);
    void addRemovedCount(){

        removedCount++ ;
    }
    void addMoveStepCount(){

        moveStepCount++ ;

    }

    public int getRemovedCount() {
        return removedCount;
    }

    public int getMoveStepCount() {
        return moveStepCount;
    }

    private GGBackground bg;
    public Machine(GGBackground bg, String spriteImages) {
        super(true,spriteImages);

        this.bg = bg;

    }

    public GGBackground getBg() {
        return bg;
    }
    abstract boolean canMove(Location location);

    void refresh(Location location){

        c = bg.getColor(location);
        rock = (Rock)gameGrid.getOneActorAt(location, Rock.class);
        clay = (Clay)gameGrid.getOneActorAt(location, Clay.class);
        bulldozer = (Bulldozer)gameGrid.getOneActorAt(location, Bulldozer.class);
        excavator = (Excavator)gameGrid.getOneActorAt(location, Excavator.class);
        pusher = (Pusher)gameGrid.getOneActorAt(location, Pusher.class);
        ore = (Ore)gameGrid.getOneActorAt(location, Ore.class);
    }

    public Ore getOre() {
        return ore;
    }
    public Pusher getPusher() {
        return pusher;
    }
    public Color getC() {
        return c;
    }

    public Rock getRock() {
        return rock;
    }

    public Clay getClay() {
        return clay;
    }

    public Bulldozer getBulldozer() {
        return bulldozer;
    }

    public Excavator getExcavator() {
        return excavator;
    }

    public Color getBorderColor() {
        return borderColor;
    }
    //test
    public void setupMachine(boolean isAutoMode, List<String> controls) {
        this.controls = controls;
    }
    public void autoMoveNext(boolean isFinished) {
        if (controls != null && autoMovementIndex < controls.size()) {
            String[] currentMove = controls.get(autoMovementIndex).split("-");
            String machine = currentMove[0];
            String move = currentMove[1];
            autoMovementIndex++;
            //排除不合理的条件
            switch (machine)
            {
                case "P":
                    if (!(this instanceof Pusher)){
                        return;
                    }
                    break;
                case "E":
                    if (!(this instanceof Excavator)){
                        return;
                    }
                    break;
                case "B":
                    if (!(this instanceof Bulldozer)){
                        return;
                    }
                    break;
            }

                if (isFinished)
                    return;

                Location next = null;
                switch (move)
                {
                    case "L":
                        next = getLocation().getNeighbourLocation(Location.WEST);
                        setDirection(Location.WEST);
                        break;
                    case "U":
                        next = getLocation().getNeighbourLocation(Location.NORTH);
                        setDirection(Location.NORTH);
                        break;
                    case "R":
                        next = getLocation().getNeighbourLocation(Location.EAST);
                        setDirection(Location.EAST);
                        break;
                    case "D":
                        next = getLocation().getNeighbourLocation(Location.SOUTH);
                        setDirection(Location.SOUTH);
                        break;
                }

                Target curTarget = (Target) gameGrid.getOneActorAt(getLocation(), Target.class);
                if (curTarget != null){
                    curTarget.show();
                }
                if (next != null && canMove(next))
                {
                    setLocation(next);
                    addMoveStepCount();
                }


        }
    }
}
