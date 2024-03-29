import java.util.List;
import java.util.Optional;

public final class Point
{
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public Entity getOccupancyCell(WorldModel world) {
        return world.getOccupancy()[this.y][this.x];
    }

    public Background getBackgroundCell(WorldModel world) {
        return world.getBackground()[this.y][this.x];
    }

    public void setOccupancyCell(
            WorldModel world, Entity entity) {
        world.getOccupancy()[this.y][this.x] = entity;
    }

    public void setBackgroundCell(
            WorldModel world, Background background) {
        world.getBackground()[this.y][this.x] = background;
    }

    // is this right???
    public Optional<Entity> nearestEntity(
            List<Entity> entities) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), this);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), this);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    private static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y
                && Math.abs(this.x - p2.x) == 1);
    }

}
