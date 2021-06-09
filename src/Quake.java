import java.util.List;

import processing.core.PImage;

public class Quake extends AnimatedEntity {


    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake (
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
            scheduler.scheduleEvent(this, Factory.createAnimationAction(this,
                    QUAKE_ANIMATION_REPEAT_COUNT),
                    this.getAnimationPeriod());
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }



}
