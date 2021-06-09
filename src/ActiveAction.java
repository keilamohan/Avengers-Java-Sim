import processing.core.PImage;

import java.util.List;

public abstract class ActiveAction extends ActiveEntity {

    public ActiveAction (

            String id,
            Point position,
            List<PImage> images,
            int actionPeriod)
    {

        super(position, images, actionPeriod, id);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }
}
