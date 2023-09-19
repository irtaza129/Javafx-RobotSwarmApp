import java.util.List;

public class Simulation {
    private Environment environment;
    private List<Robot> robots;

    public Simulation(Environment environment, List<Robot> robots) {
        this.environment = environment;
        this.robots = robots;
    }

    // This method is called to update the simulation
    public void start() {
        // Execute the behavior for each robot
        for (Robot robot : robots) {
            robot.executeBehavior();
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    // This method can be used by the FollowBehavior to get all robots
    public static List<Robot> getAllRobots() {
        // For the sake of this example, we're returning null.
        // In a real-world scenario, you'd return the list of all robots in the simulation.
        return null;
    }
}
