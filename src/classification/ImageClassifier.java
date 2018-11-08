package classification;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class ImageClassifer<O> extends NeuralNetClassifier<BufferedImage, O> {

    public ImageClassifer(List<Integer> nodeConfig) {
        super(nodeConfig);
    }

    @Override
    public O classify(BufferedImage inputs) {
        return null;
    }

    @Override
    public void batchTrain(Map<BufferedImage, O> inputOutputMap, double learningRate, int epoch) {

    }

    @Override
    public void train(Map<BufferedImage, O> inputOutputMap) {

    }
}
