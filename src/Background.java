import java.util.List;

import processing.core.PImage;

public final class Background
{
    private final String id;
    private final List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public PImage getCurrentImage() {
        return ((Background) this).images.get(
                    ((Background) this).imageIndex);
        }


}
