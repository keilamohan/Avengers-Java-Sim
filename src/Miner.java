import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Miner extends MovedEntity {

    private final int resourceLimit;
    private int resourceCount;

    public Miner(String id,
                 Point position,
                 List<PImage> images,
                 int resourceLimit,
                 int resourceCount,
                 int actionPeriod,
                 int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    protected int getResourceLimit() {
        return resourceLimit;
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



    protected int getResourceCount() {
        return resourceCount;
    }

    protected void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
}
