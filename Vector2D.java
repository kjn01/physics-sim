public class Vector2D {

    private double x;
    private double y;

    public Vector2D() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    public Vector2D multiply(double value) {
        return new Vector2D(this.x * value, this.y * value);
    }

    public Vector2D divide(double value) {
        return new Vector2D(this.x / value, this.y / value);
    }

    public double dot(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    public void setX(double value) {
        this.x = value;
    }

    public void setY(double value) {
        this.y = value;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public String toString() {
        return "<" + this.x + ", " + this.y + ">";
    }
    
}