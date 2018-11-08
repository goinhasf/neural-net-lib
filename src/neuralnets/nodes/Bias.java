package neuralnets.nodes;

import neuralnets.NeuralNetwork;

public class Bias extends HiddenNode {

    private double value;

    public Bias(NeuralNetwork<Double, Double> neuralNetwork, int layer) {
        super(neuralNetwork, layer);
        value = 1;
    }

    public Bias(NeuralNetwork<Double, Double> neuralNetwork, double value, int layer) {
        super(neuralNetwork, layer);
        this.value = value;
    }


    @Override
    public synchronized Double output() {
        return value;
    }

    public synchronized void setValue(double value) {
        // this.value = value;
    }

    public synchronized double getValue() {
        return value;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String string = "[Bias " + nodeNum + ", layer= " + layer +", value=" + value +"]";

        return string;
    }
}
