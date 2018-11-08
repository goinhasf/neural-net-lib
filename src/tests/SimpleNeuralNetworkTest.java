package tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import neuralnets.*;
import neuralnets.nodes.*;
import org.junit.Test;
import utils.OutputFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleNeuralNetworkTest {
	
	@Test
	public void initializeNetworkTest() {
		
		SimpleNeuralNet net = new SimpleNeuralNet(Arrays.asList(new Integer[] {2, 2, 1}));

		System.out.println(net);
		
		assertEquals(7, net.getNumOfNodes());
		
		HiddenNode layer1HiddenNode1 = (HiddenNode) net.getOutputEdgesFor(net.getInputNodes().get(0)).get(0).getToNode();
		assertEquals(3, net.getInputEdgesFor(layer1HiddenNode1).size());
		
		HiddenNode layer1HiddenNode2 = (HiddenNode) net.getOutputEdgesFor(net.getInputNodes().get(0)).get(1).getToNode();
		assertEquals(3, net.getInputEdgesFor(layer1HiddenNode2).size());
		
		OutputNode outputNode = (OutputNode) net.getOutputNodes().get(0);
		assertEquals(3, net.getInputEdgesFor(outputNode).size());


		
	}
	
	@Test
	public void settingInputs() {
		
		SimpleNeuralNet net = new SimpleNeuralNet(Arrays.asList(new Integer[] {2, 2, 1}));
		List<Double> inputs = Arrays.asList(new Double[] {1d, 0d});
		net.input(inputs);
		assertTrue(inputs.get(0).equals(((InputNode)net.getInputNodes().get(0)).getInputVal()));
		assertTrue(inputs.get(1).equals(((InputNode)net.getInputNodes().get(1)).getInputVal()));

	}

	@Test
	public void outputTestSimpleNetwork() {
		SimpleNeuralNet net1 = new SimpleNeuralNet(Arrays.asList(new Integer[] {2, 1}));
		net1.input(Arrays.asList(new Double[] {1d, 1d}));
		OutputNode outputNode = (OutputNode) net1.getOutputNodes().get(0);
		net1.getInputEdgesFor(outputNode).get(0).setWeight(0.3);
		net1.getInputEdgesFor(outputNode).get(1).setWeight(0.5);

		OutputFunction<Double, Double> activationFunction = out -> 1 / (1 + Math.exp(-out));
		assertEquals(activationFunction.result((Double)0.8d), (Double)net1.output().get(0));
		
		SimpleNeuralNet net2 = new SimpleNeuralNet(Arrays.asList(new Integer[] {2, 2, 1}));
		
		net2.input(Arrays.asList(new Double[] {1d, 1d}));
		OutputNode outputNode1 = (OutputNode) net2.getOutputNodes().get(0);
		net2.getInputEdgesFor(outputNode1).get(0).setWeight(0.3);
		net2.getInputEdgesFor(outputNode1).get(1).setWeight(0.5);

		InputNode input1 = (InputNode) net2.getInputNodes().get(0);
		InputNode input2 = (InputNode) net2.getInputNodes().get(1);
		NodeEdge hiddenEdge1 = net2.getOutputEdgesFor(input1).get(0);
		NodeEdge hiddenEdge2 = net2.getOutputEdgesFor(input1).get(1);
		NodeEdge hiddenEdge3 = net2.getOutputEdgesFor(input2).get(0);
		NodeEdge hiddenEdge4 = net2.getOutputEdgesFor(input2).get(1);
		hiddenEdge1.setWeight(0.5);
		hiddenEdge2.setWeight(0.5);
		hiddenEdge3.setWeight(0.3);
		hiddenEdge4.setWeight(0.3);
		
		double expected = activationFunction.result(activationFunction.result(0.8) * 0.3 + activationFunction.result(0.8) * 0.5);
		assertEquals((Double)0.8d, ((HiddenNode)hiddenEdge1.getToNode()).netOutput());
		assertEquals((Double)0.8d, ((HiddenNode)hiddenEdge2.getToNode()).netOutput());
		assertEquals((Double)(activationFunction.result(0.8d) * 0.3d + activationFunction.result(0.8d) * 0.5d), outputNode1.netOutput());
		assertEquals((Double) expected, outputNode1.output());
	}

	@Test
	public void backpropagationTest() {

		SimpleNeuralNet net = new SimpleNeuralNet(Arrays.asList(new Integer[]{2, 2, 1}));

		List<NodeEdge> layer0 = net.getEdges().get(0);
		List<NodeEdge> layer1 = net.getEdges().get(1);

		InputNode i1 = (InputNode) layer0.get(0).getFromNode();
		InputNode i2 = (InputNode) layer0.get(2).getFromNode();
		HiddenNode h1 = (HiddenNode) layer0.get(0).getToNode();
		HiddenNode h2 = (HiddenNode) layer0.get(1).getToNode();
		OutputNode o1 = (OutputNode) net.getOutputNodes().get(0);


		NodeEdge w1 = layer0.get(0);
		NodeEdge w2 = layer0.get(1);
		NodeEdge w3 = layer0.get(2);
		NodeEdge w4 = layer0.get(3);
		NodeEdge w5 = layer1.get(0);
		NodeEdge w6 = layer1.get(1);
		NodeEdge be1 = layer0.get(4);
		NodeEdge be2 = layer0.get(5);
		NodeEdge be3 = layer1.get(2);

		i1.setInputVal(0.05);
		i2.setInputVal(0.10);
		w1.setWeight(0.15);
		w2.setWeight(0.2);
		w3.setWeight(0.25);
		w4.setWeight(0.3);
		w5.setWeight(0.4);
		w6.setWeight(0.5);
		be1.setWeight(0.35);
		be2.setWeight(0.35);
		be3.setWeight(0.6);

		double outputO1 = o1.output();
		double outputh1 = h2.output();

		System.out.println(outputO1);
		System.out.println(outputh1);
		o1.calculateError(1d, o1.output());
		System.out.println(w5.getError());


	}

	
	@Test
	public void trainTest() {
		SimpleNeuralNet net = new SimpleNeuralNet(Arrays.asList(new Integer[] {2, 2, 1}));
		net.setLearningRate(0.5);


		net.train(ANNUtils.getANDTrainingSet(), 50000);
		
		System.out.println(net.predict(Arrays.asList(new Double[] {0d, 0d})));
		System.out.println(net.predict(Arrays.asList(new Double[] {1d, 0d})));
		System.out.println(net.predict(Arrays.asList(new Double[] {0d, 1d})));
		System.out.println(net.predict(Arrays.asList(new Double[] {1d, 1d})));
	}
	
	@Test
	public void XORNetTest() {

		SimpleNeuralNet net = new SimpleNeuralNet(Arrays.asList(new Integer[] {2, 4, 2, 1}));
		net.setLearningRate(0.2);


		net.train(ANNUtils.getXORTrainingSet(), 50000);

		System.out.println(net.predict(Arrays.asList(new Double[] {0d, 0d})));
		System.out.println(net.predict(Arrays.asList(new Double[] {1d, 0d})));
		System.out.println(net.predict(Arrays.asList(new Double[] {0d, 1d})));
		System.out.println(net.predict(Arrays.asList(new Double[] {1d, 1d})));
		
		
	}
}	
