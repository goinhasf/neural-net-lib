package neuralnets.nodes;

import tests.ANNUtils;

public class NodeEdge {

	private INode fromNode;
	private INode toNode;
	private double weight;
	private int layer;
	private double error;

	public NodeEdge(int layer) {
		weight = ANNUtils.generateRandom();
		this.layer = layer;
		error = 0;
	}


	public NodeEdge(int layer, INode fromNode, INode toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.layer = layer;
		error = 0;
		weight = ANNUtils.generateRandom();
	}
	
	public INode getFromNode() {
		return fromNode;
	}
	
	public INode getToNode() {
		return toNode;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void updateWeight() {
		weight = weight - toNode.neuralNetwork.getLearningRate() * error;
	}

	public void setFromNode(INode fromNode) {
		this.fromNode = fromNode;
	}

	public void setToNode(INode toNode) {
		this.toNode = toNode;
	}

	public int getLayer() {
		return layer;
	}

	@Override
	public String toString() {
		return fromNode.toString() + "---------->" + toNode.toString() + ", weight=" + weight;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}
}
