import java.util.*;

import processing.core.PImage;

public class MinerFull extends Miner {


    public MinerFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }


    private void transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        MinerNotFull miner = Factory.createMinerNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }



    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), Blacksmith.class);

        if (fullTarget.isPresent() && this.moveTo(world,
                fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        return true;
    }

}
