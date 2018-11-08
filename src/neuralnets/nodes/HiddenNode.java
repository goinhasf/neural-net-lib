package neuralnets.nodes;

import neuralnets.NeuralNetwork;
import java.util.List;

public class HiddenNode extends INode {

	public HiddenNode(NeuralNetwork<Double, Double> neuralNetwork, int layer) {
		super(neuralNetwork, layer);
	}

	@Override
	public void backpropagate() {

		double error = 0;

		List<NodeEdge> outputs = neuralNetwork.getOutputEdgesFor(this);
		List<NodeEdge> inputs = neuralNetwork.getInputEdgesFor(this);

		for (NodeEdge edge : outputs) {
			error += edge.getError() * edge.getWeight();
		}
		double output = output();

		for (NodeEdge edge : inputs) {
			edge.setError(error * output * (1 - output) * edge.getFromNode().output());
			edge.getFromNode().backpropagate();
		}

	}

	// Output of the node by doing the dot product of the Input and Weight vectors
	@Override
	public Double output() {
		return neuralNetwork.getActivationFunction().result(netOutput());

	}

	public Double netOutput() {
		double output = 0;

		for (NodeEdge inputEdge : neuralNetwork.getInputEdgesFor(this)) {
			output += inputEdge.getFromNode().output() * inputEdge.getWeight() + neuralNetwork.getBiasAt(inputEdge.getLayer());
		}

		return output;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[HiddenNode " + nodeNum + ", layer=" + layer + "]";
	}


}
