import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends Entity {

    private final int actionPeriod;

    public ActiveEntity(
            Point position,
            List<PImage> images,
            int actionPeriod, String id)
    {
        super(position, images, id);
        this.actionPeriod = actionPeriod;
    }

    public abstract void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);

    public abstract void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler);

    protected int getActionPeriod() {
        return actionPeriod;
    }
}
