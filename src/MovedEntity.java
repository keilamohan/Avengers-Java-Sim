import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MovedEntity extends AnimatedEntity{

    public MovedEntity(String id,
                 Point position,
                 List<PImage> images,
                 int actionPeriod,
                 int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public Point nextPosition(
            WorldModel world, Point destPos) {
        PathingStrategy strategy = new AStarPathingStrategy();

        List<Point> points = strategy.computePath(getPosition(), destPos, (Point p)->
                world.withinBounds(p) && !world.isOccupied(p), (Point p1, Point p2) ->
                p1.adjacent(p2), PathingStrategy.CARDINAL_NEIGHBORS);

        if (points.isEmpty())
            return getPosition();
        return points.get(0);
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return moveToHelper(world, target, scheduler);
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }

    }

    protected abstract boolean moveToHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler);
}
