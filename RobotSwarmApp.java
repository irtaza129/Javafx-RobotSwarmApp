import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RobotSwarmApp extends Application {
    private Simulation simulation;
    private Canvas canvas;

    public RobotSwarmApp() {
        List<Area> areas = new ArrayList<>();
        areas.add(new CircleArea("C1", new Point(100, 100), 50));
        areas.add(new RectangleArea("R1", new Point(300, 300), 100, 50));
        Environment environment = new Environment(areas);

        List<Robot> robots = new ArrayList<>();
        robots.add(new Robot(new Point(400, 300), new MoveRandomBehavior(new Point(0, 0), new Point(800, 600), 5)));
        robots.add(new Robot(new Point(200, 200), new MoveRandomBehavior(new Point(0, 0), new Point(800, 600), 5)));

        simulation = new Simulation(environment, robots);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Swarm Simulation");

        canvas = new Canvas(800, 600);
        draw();

        Pane root = new Pane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                simulation.start();
                draw();
            }
        };
        gameLoop.start();
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw areas
        for (Area area : simulation.getEnvironment().getAreas()) {
            if (area instanceof CircleArea) {
                CircleArea circle = (CircleArea) area;
                gc.setStroke(Color.GREEN);
                gc.strokeOval(circle.getPosition().getX() - circle.getRadius(), circle.getPosition().getY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
            } else if (area instanceof RectangleArea) {
                RectangleArea rectangle = (RectangleArea) area;
                gc.setStroke(Color.RED);
                gc.strokeRect(rectangle.getPosition().getX() - rectangle.getWidth() / 2, rectangle.getPosition().getY() - rectangle.getHeight() / 2, rectangle.getWidth(), rectangle.getHeight());
            }
        }

        // Draw robots
        for (Robot robot : simulation.getRobots()) {
            Point position = robot.getPosition();
            gc.setFill(Color.BLUE);
            gc.fillOval(position.getX() - 5, position.getY() - 5, 10, 10);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
