import processing.core.PImage;

import java.util.List;
//BLACKSMITH, MINER_FULL, MINER_NOT_FULL, OBSTACLE, ORE, ORE_BLOB, QUAKE, VEIN

public abstract class Entity {
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    private final String id;

    public Entity(Point position, List<PImage> images, String id)
    {
        this.position = position;
        this.images = images;
        this.id = id;
        this.imageIndex = 0;
    }

    public PImage getCurrentImage() {
        return this.getImages().get(this.imageIndex);
    }

    protected Point getPosition() {
        return position;
    }

    protected void setPosition(Point p) {
        position = p;
    }

    protected List<PImage> getImages() {
        return images;
    }

    protected String getId() {
        return id;
    }


    protected int getImageIndex() {
        return imageIndex;
    }
    protected void setImageIndex(int i) {
        this.imageIndex = i;
    }

}
