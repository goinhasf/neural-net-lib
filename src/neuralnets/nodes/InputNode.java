package neuralnets.nodes;

import neuralnets.NeuralNetwork;

public class InputNode extends INode {
	
	private double inputVal;
	
	public InputNode(NeuralNetwork<Double, Double> neuralNetwork, int layer) {
		super(neuralNetwork, layer);
		inputVal = 0;
	}
	
	public synchronized void setInputVal(Double entries) {
		this.inputVal = entries;
	}
	
	public synchronized double getInputVal() {
		return inputVal;
	}
	
	@Override
	public synchronized Double output() {
		return inputVal;
	}

	@Override
	public synchronized void backpropagate() {
		return;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Input Node " + nodeNum + ", input: " + inputVal + ", layer=" + layer +"]";
	}


}
