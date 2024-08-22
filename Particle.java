public class Particle {
    
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double mass;
    private double radius;

    public Particle() {

        this.position = new Vector2D();
        this.velocity = new Vector2D();
        this.acceleration = new Vector2D();

        this.mass = 1.0;
        this.radius = 1.0;

    }

    public Particle(double mass, double radius) {
        this.position = new Vector2D();
        this.velocity = new Vector2D();
        this.acceleration = new Vector2D();

        this.mass = mass;
        this.radius = radius;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void applyForce(Vector2D force) {
        // f = ma
        force = force.divide(this.mass);
        acceleration = acceleration.add(force);
    }

    public void applyAcceleration(Vector2D accel) {

        acceleration = acceleration.add(accel);

    }

    public Vector2D[] checkParticleCollision(Particle p2) {


        // Difference vector
        Vector2D distance_vector = p2.position.subtract(this.position);
        if (distance_vector.equals(new Vector2D(0, 0))) {
            distance_vector = new Vector2D(0, 0.1);
        }

        double distance = distance_vector.magnitude();

        // Normalized
        distance_vector = distance_vector.divide(distance_vector.magnitude());

        double min_distance = this.radius;

        if (distance < min_distance) {

            double overlap = min_distance - distance;
            this.position = this.position.subtract(distance_vector.multiply(overlap / 2));
            p2.position = p2.position.add(distance_vector.multiply(overlap / 2));

            distance_vector = p2.position.subtract(this.position);
            // Normalized
            distance_vector = distance_vector.divide(distance_vector.magnitude());

            double v_p1_parallel = this.velocity.dot(distance_vector);
            double v_p2_parallel = p2.velocity.dot(distance_vector);

            double v_p1_parallel_post = (v_p1_parallel * (this.mass - p2.mass) + 2 * p2.mass * v_p2_parallel) / (this.mass + p2.mass);
            double v_p2_parallel_post = (v_p2_parallel * (p2.mass - this.mass) + 2 * this.mass * v_p1_parallel) / (this.mass + p2.mass);
            
            this.velocity = this.velocity.add(distance_vector.multiply(v_p1_parallel_post - v_p1_parallel));
            p2.velocity = p2.velocity.add(distance_vector.multiply(v_p2_parallel_post - v_p2_parallel));

            return new Vector2D[]{this.velocity, p2.velocity, this.position, p2.position};
        }
        return null;
    }

    public void checkBoundsCollision(double[] dimensions) {
        // Vector2D difference_bottom_right = this.position.subtract(new Vector2D(dimensions[0], dimensions[1]));
        // Vector2D difference_top_left = this.position.subtract(new Vector2D(0, 0));
        // double distance = Math.min(difference_bottom_right.magnitude(), difference_bottom_right.magnitude());
        
        // System.out.println(Math.abs(-this.position.getY() - dimensions[1]));

        if (Math.abs(-this.position.getY() + this.radius) > dimensions[1]) {
            this.position.setY(-dimensions[1] + this.radius);
            this.velocity.setY(-this.velocity.getY());
            this.velocity = this.velocity.multiply(0.5);
        }
        if (Math.abs(-this.position.getY() + this.radius) < 0) {
            this.position.setY(this.radius);
            this.velocity.setY(-this.velocity.getY());
            this.velocity = this.velocity.multiply(0.5);
        }
        if (Math.abs(this.position.getX() + this.radius) > dimensions[0]) {
            this.position.setX(dimensions[0] - this.radius);
            this.velocity.setX(-this.velocity.getX());
            this.velocity = this.velocity.multiply(0.5);
        }
        if (Math.abs(this.position.getX() - this.radius) < 100) {
            this.position.setX(100 + this.radius);
            this.velocity.setX(-this.velocity.getX());
            this.velocity = this.velocity.multiply(0.5);
        }
    }

    public double getMass() {
        return this.mass;
    }

    public double getRadius() {
        return this.radius;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public Vector2D getVelocity() {
        return this.velocity;
    }

    public Vector2D getAcceleration() {
        return this.acceleration;
    }

    public void update(double time, double[] dimensions) {

        velocity = velocity.add(acceleration.multiply(time));
        position = position.add(velocity.multiply(time));

        checkBoundsCollision(dimensions);

    }

    public String toString() {
        return "Particle(mass=" + this.mass + ",radius=" + this.radius + "):\n\tPosition: " + 
        this.position + "\n\tVelocity: " + this.velocity + "\n\tAcceleration: " + this.acceleration;
    }
}
