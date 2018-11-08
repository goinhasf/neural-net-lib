package neuralnets.nodes;

import neuralnets.NeuralNetwork;

import java.util.List;

public class OutputNode extends HiddenNode {

    public OutputNode(NeuralNetwork<Double, Double> neuralNetwork, int layer) {
        super(neuralNetwork, layer);
    }

    /**
     * Calculates the error on the output node by using the partial derivative of the error with respect to
     * the output node.
     * @param targetOutput      The target output.
     * @param actualOutput      The actual output.
     *
     */
    public void calculateError(Double targetOutput, Double actualOutput) {

        double error = -(targetOutput - actualOutput) * actualOutput * (1-actualOutput);
        List<NodeEdge> inputs = neuralNetwork.getInputEdgesFor(this);

        for (NodeEdge edge : inputs) {
            edge.setError(error * edge.getFromNode().output());
            edge.getFromNode().backpropagate();
        }

    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "[OutputNode " + nodeNum + ", layer=" + layer + "]";
    }
}
