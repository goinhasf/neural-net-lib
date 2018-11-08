package classification;

import neuralnets.SimpleNeuralNet;

import java.util.List;

public abstract class NeuralNetClassifer<I, O> implements Classifier<I, O> {


    protected SimpleNeuralNet neuralNet;

    public NeuralNetClassifer(List<Integer> nodeConfig) {
        neuralNet = new SimpleNeuralNet(nodeConfig);
    }
}
