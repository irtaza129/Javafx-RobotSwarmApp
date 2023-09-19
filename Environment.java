import java.util.List;

public class Environment {
    private List<Area> areas;

    public Environment(List<Area> areas) {
        this.areas = areas;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}

abstract class Area {
    protected String label;
    protected Point position;

    public Area(String label, Point position) {
        this.label = label;
        this.position = position;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract boolean containsPoint(Point point);
}

class CircleArea extends Area {
    private double radius;

    public CircleArea(String label, Point position, double radius) {
        super(label, position);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean containsPoint(Point point) {
        return position.distanceTo(point) <= radius;
    }
}

class RectangleArea extends Area {
    private double width;
    private double height;

    public RectangleArea(String label, Point position, double width, double height) {
        super(label, position);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean containsPoint(Point point) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;
        return point.getX() >= position.getX() - halfWidth && point.getX() <= position.getX() + halfWidth &&
               point.getY() >= position.getY() - halfHeight && point.getY() <= position.getY() + halfHeight;
    }
}
 