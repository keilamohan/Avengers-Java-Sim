import java.util.*;

import processing.core.PImage;

public class OreBlob extends MovedEntity {
    private static final String QUAKE_KEY = "quake";


    public OreBlob (
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }


    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
            scheduler.scheduleEvent(this,
                    Factory.createAnimationAction(this, 0),
                    this.getAnimationPeriod());

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

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> blobTarget =
                world.findNearest(this.getPosition(), Vein.class);
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveTo(world, blobTarget.get(), scheduler)) {
                Quake quake = Factory.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public boolean moveToHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
    }


}
