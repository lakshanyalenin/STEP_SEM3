abstract class Shape {

    private static int counter = 0;
    private final String shapeId;

    protected Shape() {
        counter++;
        shapeId = "SH-" + counter;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
        scale(factor, factor);
    }

    public void scale(double xFactor, double yFactor) {
        // Default implementation
    }

    public String getShapeId() {
        return shapeId;
    }
}

class CircleShape extends Shape {

    private double radius;

    public CircleShape(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        radius *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        radius *= (xFactor + yFactor) / 2.0;
    }
}

class SquareShape extends Shape {

    private double side;

    public SquareShape(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side must be positive");
        }
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        side *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        side *= (xFactor + yFactor) / 2.0;
    }
}

public class Q1_BasicDrawingCanvas {

    public static void printArea(Shape s) {
        System.out.println("Area: " + s.calculateArea());
    }

    public static void main(String[] args) {

        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.println("Circle ID: " + c.getShapeId());
        System.out.println("Circle Area: " + c.calculateArea());

        System.out.println("Square ID: " + sq.getShapeId());
        System.out.println("Square Area: " + sq.calculateArea());

        sq.scale(2.0);
        System.out.println("Square Area after scale(2.0): " + sq.calculateArea());

        printArea(c);
        printArea(sq);
    }
}