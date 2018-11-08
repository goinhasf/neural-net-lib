package neuralnets.nodes;

import neuralnets.NeuralNetwork;

public abstract class INode {

	protected static int numOfNodes = 0;
	protected int nodeNum;
	protected int layer;
	protected NeuralNetwork<Double, Double> neuralNetwork;

	public INode(NeuralNetwork<Double, Double> neuralNetwork, int layer) {
		this.layer = layer;
		this.neuralNetwork = neuralNetwork;
		nodeNum = numOfNodes++;
	}

	public abstract Double output() ;
	public abstract void backpropagate();
	public int getLayer() {
		return layer;
	}
}
