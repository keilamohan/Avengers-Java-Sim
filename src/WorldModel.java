import processing.core.PImage;

import java.util.*;

public final class WorldModel
{
    private final int numRows;
    private final int numCols;
    private final Background background[][];
    private final Entity occupancy[][];
    private final Set<Entity> entities;

    private static final int ORE_REACH = 1;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && pos.getOccupancyCell(this) != null) {
            Entity entity = pos.getOccupancyCell(this);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            pos.setOccupancyCell(this, null);
        }
    }


    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            entity.getPosition().setOccupancyCell(this, entity);
            this.entities.add(entity);
        }
    }

    public void removeEntity(Entity entity) {
        this.removeEntityAt(entity.getPosition());
    }

    public Optional<PImage> getBackgroundImage(
            Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(pos.getBackgroundCell(this).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }

    public Optional<Entity> findNearest(
            Point pos, Class classname) {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities) {
            if (entity.getClass() == classname) {
                ofType.add(entity);
            }
        }

        return pos.nearestEntity(ofType);
    }

    public Optional<Point> findOpenAround(Point pos) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (this.withinBounds(newPt) && ! this.isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    public void setBackground(
            Point pos, Background background) {
        if (this.withinBounds(pos)) {
            pos.setBackgroundCell(this, background);
        }
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && pos.getOccupancyCell(this) != null;
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(pos.getOccupancyCell(this));
        } else {
            return Optional.empty();
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            oldPos.setOccupancyCell(this, null);
            this.removeEntityAt(pos);
            pos.setOccupancyCell(this, entity);
            entity.setPosition(pos);
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Background[][] getBackground() {
        return background;
    }

    public Entity[][] getOccupancy() {
        return occupancy;
    }

    public Set<Entity> getEntities() {
        return entities;
    }


}
