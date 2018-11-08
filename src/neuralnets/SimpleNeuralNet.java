package neuralnets;

import neuralnets.nodes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleNeuralNet extends NeuralNetwork<Double, Double> {

    public SimpleNeuralNet(List<Integer> nodeConfig) {
        super(out -> 1 / (1 + Math.exp(-out)), nodeConfig);
        initialize();
    }

    public SimpleNeuralNet() {
        super(out -> 1 / (1 + Math.exp(-out)));
    }

    /**
     * Initializes the edges of the network and creates links between them.
     */
    private void initialize() {

        List<INode> fromNodes = new ArrayList<>();
        List<INode> toNodes = new ArrayList<>();

        // Starting on layer 1, go through all the layers in the nodeConfig list.
        for (int i = 1; i < nodeConfig.size(); i++) {
            // Store the number of nodes required per layer.
            int numOfFromNodes = nodeConfig.get(i - 1);
            int numOfToNodes = nodeConfig.get(i);

            List<NodeEdge> edges = new ArrayList<>();

            // If it's the first iteration of the loop we will want to create the InputNodes and then add a Bias node.
            // Otherwise the fromNodes become the toNodes because they would have already been created on the previous
            // iteration.
            if (i == 1) {
                for (int n = 0; n < numOfFromNodes; n++) {
                    fromNodes.add(new InputNode(this, 0));
                }
                fromNodes.add(new Bias(this, 0));
                allNodes.addAll(fromNodes);
            } else {
                fromNodes = toNodes;
                toNodes = new ArrayList<>();
            }


            // If it's the last iteration, then create the output nodes.
            // Otherwise create hidden nodes and the add a bias.
            if (i == nodeConfig.size() - 1) {
                for (int k = 0; k < numOfToNodes; k++) {
                    toNodes.add(new OutputNode(this, i));
                }

            } else {
                for (int k = 0; k < numOfToNodes; k++) {
                    toNodes.add(new HiddenNode(this, i));
                }
                toNodes.add(new Bias(this, i));
            }


            allNodes.addAll(toNodes);

            // Links all nodes to form a fully connected network.
            for (INode fromNode : fromNodes) {
                for (INode toNode : toNodes) {
                    // Do not connect to bias nodes.
                    if (!(toNode instanceof Bias))
                        edges.add(new NodeEdge(i - 1, fromNode, toNode));
                }
            }
            // Insert edges onto map.
            this.edges.put(i - 1, edges);

        }

    }


    @Override
    public void train(Map<List<Double>, List<Double>> inputOutputMap, int epoch) {

        // Loop until epoch time has expired
        for (int i = 0; i < epoch; i++) {
            // Iterate through all input/output list pairs
            train(inputOutputMap);

        }

    }

    /**
     * Backpropagtes the error to all edges. The error is then used to perform the calculations to update the weights.
     *
     * @param targetOutput The target output of the given output node.
     * @param actualOutput The actual output produced by the output node.
     */
    @Override
    public synchronized void backpropagate(Double targetOutput, Double actualOutput) {

        for (INode out : getOutputNodes()) {
            ((OutputNode) out).calculateError(targetOutput, actualOutput);
        }

    }

    // Updates all weights from all edges in the network.
    private synchronized void updateWeights() {
        for (int i = edges.size() - 1; i >= 0; i--) {
            for (NodeEdge edge : edges.get(i)) {
                edge.updateWeight();
            }
        }
    }

    @Override
    public synchronized List<Double> predict(List<Double> inputs) {

        input(inputs);

        return output();


    }

    public List<Double> train(Map<List<Double>, List<Double>> inputOutputMap) {

        List<Double> output = new ArrayList<>();

        for (Map.Entry<List<Double>, List<Double>> entry : inputOutputMap.entrySet()) {
            output = predict(entry.getKey());
            // Iterate through all output edges
            for (int j = 0; j < getOutputNodes().size(); j++) {
                // Backpropagate the error.
                backpropagate(entry.getValue().get(j), output.get(j));
                // Updates the weights.
                updateWeights();

            }


        }

        return output;
    }


    @Override
    public synchronized void input(List<Double> inputs) {

        if (edges.isEmpty()) {
            return;
        }

        for (int i = 0; i < getInputNodes().size(); i++) {

            ((InputNode) getInputNodes().get(i)).setInputVal(inputs.get(i));

        }

    }


    @Override
    public synchronized List<Double> output() {

        if (edges.isEmpty()) {
            return new ArrayList<>();
        }

        List<Double> outputs = new ArrayList<>();

        // Adds the output node results to a list
        for (INode outputNode : getOutputNodes()) {
            outputs.add(outputNode.output());
        }

        return outputs;
    }


}
