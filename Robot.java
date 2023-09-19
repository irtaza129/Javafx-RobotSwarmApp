   import java.util.List;
import java.util.ArrayList;

public class Robot {
    private Point position;
    private double speed;
    private RobotBehavior behavior;

    public Robot(Point position, RobotBehavior behavior) {
        this.position = position;
        this.behavior = behavior;
    }

    public void executeBehavior() {
        behavior.execute(this);
    }

    public void move(Point target, double speed) {
        this.position = target;
        this.speed = speed;
    }

    public void signal(String label) {
        System.out.println("Robot at position " + position + " signaling: " + label);
    }

    public Point getPosition() {
        return this.position;
    }

    public double getSpeed() {
        return this.speed;
    }
}

interface RobotBehavior {
    void execute(Robot robot);
}

class MoveBehavior implements RobotBehavior {
    private Point target;
    private double speed;

    public MoveBehavior(Point target, double speed) {
        this.target = target;
        this.speed = speed;
    }

    @Override
    public void execute(Robot robot) {
        robot.move(target, speed);
    }
}

class SignalBehavior implements RobotBehavior {
    private String label;

    public SignalBehavior(String label) {
        this.label = label;
    }

    @Override
    public void execute(Robot robot) {
        robot.signal(label);
    }
}

class StopBehavior implements RobotBehavior {
    @Override
    public void execute(Robot robot) {
        robot.move(robot.getPosition(), 0);
    }
}

class MoveRandomBehavior implements RobotBehavior {
    private Point minBounds;
    private Point maxBounds;
    private double speed;

    public MoveRandomBehavior(Point minBounds, Point maxBounds, double speed) {
        this.minBounds = minBounds;
        this.maxBounds = maxBounds;
        this.speed = speed;
    }

    @Override
    public void execute(Robot robot) {
        double randomX = minBounds.getX() + Math.random() * (maxBounds.getX() - minBounds.getX());
        double randomY = minBounds.getY() + Math.random() * (maxBounds.getY() - minBounds.getY());
        Point randomTarget = new Point(randomX, randomY);
        robot.move(randomTarget, speed);
    }
}

class FollowBehavior implements RobotBehavior {
    private String label;
    private double distance;
    private double speed;

    public FollowBehavior(String label, double distance, double speed) {
        this.label = label;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    public void execute(Robot robot) {
        List<Robot> allRobots = Simulation.getAllRobots();
        List<Point> positionsToFollow = new ArrayList<>();

        for (Robot r : allRobots) {
            if (r.getPosition().distanceTo(robot.getPosition()) <= distance) {
                positionsToFollow.add(r.getPosition());
            }
        }

        if (!positionsToFollow.isEmpty()) {
            double avgX = positionsToFollow.stream().mapToDouble(Point::getX).average().orElse(0);
            double avgY = positionsToFollow.stream().mapToDouble(Point::getY).average().orElse(0);
            Point avgPosition = new Point(avgX, avgY);
            robot.move(avgPosition, speed);
        } else {
            new MoveRandomBehavior(
                new Point(robot.getPosition().getX() - distance, robot.getPosition().getY() - distance),
                new Point(robot.getPosition().getX() + distance, robot.getPosition().getY() + distance),
                speed
            ).execute(robot);
        }
    }
}

class ContinueBehavior implements RobotBehavior {
    private double duration;

    public ContinueBehavior(double duration) {
        this.duration = duration;
    }

    @Override
    public void execute(Robot robot) {
        robot.move(robot.getPosition(), robot.getSpeed());
    }
}
