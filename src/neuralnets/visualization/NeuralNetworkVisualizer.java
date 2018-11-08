package neuralnets.visualization;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import neuralnets.SimpleNeuralNet;
import tests.ANNUtils;

import java.awt.image.BufferedImage;
import java.util.*;

public class NeuralNetworkVisualizer extends Application {

    public static final int CELL_WIDTH = 10;
    public static final int CELL_HEIGHT = 10;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public SimpleNeuralNet net;
    private int epoch;
    private GraphicsContext gc;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        paintNetwork();
    }

    private void paintNetwork() {
        net = new SimpleNeuralNet(Arrays.asList(2, 4, 1));
        epoch = 50000;
        net.setLearningRate(0.5);
        net.setVisualizer(this);
        Thread t1 = new Thread(this::update);
        t1.setDaemon(true);
        t1.start();
    }

    public void update() {

        System.out.println("Training");

        for (int i = 0; i < epoch; i++) {

            net.train(ANNUtils.getXORTrainingSet());
            System.out.print("%\r");
            System.out.print("Training Progress: " + (int) ((i / (double) epoch) * 100d) + "%");
            if (i % 100 == 0)
                displayUpdate();

        }

        System.out.println();
        System.out.println(net.predict(Arrays.asList(new Double[]{0d, 0d})));
        System.out.println(net.predict(Arrays.asList(new Double[]{1d, 0d})));
        System.out.println(net.predict(Arrays.asList(new Double[]{0d, 1d})));
        System.out.println(net.predict(Arrays.asList(new Double[]{1d, 1d})));
        System.out.println("Done");

    }

    private synchronized void displayUpdate() {

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        int i = 0;
        for (int x = 0; x < WIDTH; x += CELL_WIDTH) {

            for (int y = HEIGHT - 1; y >= 0; y -= CELL_HEIGHT) {
                double output = net.predict(Arrays.asList((double) x / WIDTH, (double) y / HEIGHT)).get(0);
                Color c = Color.grayRgb((int) (255 * output));
                //System.out.println("x=" + x + ", y=" + y);
                image.setRGB(x, y - CELL_HEIGHT + 1, CELL_WIDTH, CELL_HEIGHT, pixelData(c), 0, CELL_WIDTH);
            }
        }
        Image fxImage = SwingFXUtils.toFXImage(image, new WritableImage(WIDTH, HEIGHT));
        gc.drawImage(fxImage, 0, 0);

    }

    private int[] pixelData(Color c) {
        int[] data = new int[CELL_WIDTH * CELL_HEIGHT];
        for (int i = 0; i < CELL_WIDTH * CELL_HEIGHT; i++) {
            int a = (int) (255 * c.getOpacity());
            int r = (int) (255 * c.getRed());
            int g = (int) (255 * c.getGreen());
            int b = (int) (255 * c.getBlue());
            data[i++] = (a << 24) | (r << 16) | (g << 8) | b;
        }

        return data;
    }


}
