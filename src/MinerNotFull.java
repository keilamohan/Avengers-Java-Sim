import java.util.*;

import processing.core.PImage;

public class MinerNotFull extends Miner {

    public MinerNotFull (
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    private boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            MinerFull miner = Factory.createMinerFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> notFullTarget =
                world.findNearest(this.getPosition(), Ore.class);

        if (!notFullTarget.isPresent() || !this.moveTo(world,
                notFullTarget.get(),
                scheduler)
                || !this.transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
            setResourceCount((this.getResourceCount())+1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;

    }


}
