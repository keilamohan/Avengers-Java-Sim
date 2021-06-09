import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends ActiveEntity {
    private final int animationPeriod;

    public AnimatedEntity(String id, Point position,
                          List<PImage> images,
                          int actionPeriod,
                          int animationPeriod)
    {
        super(position, images, actionPeriod, id);
        this.animationPeriod = animationPeriod;
    }

    public void nextImage() {
        setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }

    protected int getAnimationPeriod() {
        return this.animationPeriod;
    }

}
