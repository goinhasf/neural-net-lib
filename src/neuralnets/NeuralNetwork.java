package neuralnets;

import neuralnets.nodes.*;
import neuralnets.visualization.NeuralNetworkVisualizer;
import utils.OutputFunction;

import java.util.*;

public abstract class NeuralNetwork<I, O> {

    protected List<Integer> nodeConfig;
    protected Map<Integer, List<NodeEdge>> edges;
    protected List<INode> allNodes;
    protected OutputFunction<Double, Double> activationFunction;
    protected double learningRate = 0.1;
    protected NeuralNetworkVisualizer nnVisualizer;

    /**
     * Constructs a standard neural net with the given configuration. Weights are
     * randomized and inputs set to 0 as default.
     *
     * @param activationFunction The activation function to be used in the neural net.
     * @param nodeConfiguration  A list of numbers corresponding to the number of edges at each
     *                           layer. For example: [2, 2, 1] represents a neural net with 2
     *                           inputs, 2 hidden layers and 1 output.
     */
    public NeuralNetwork(OutputFunction<Double, Double> activationFunction, List<Integer> nodeConfiguration) {
        this.activationFunction = activationFunction;
        nodeConfig = nodeConfiguration;
        edges = new HashMap<>();
        allNodes = new ArrayList<>();
    }

    /**
     * Constructs a standard neural net with the given configuration. Weights are
     * randomized and inputs set to 0 as default.
     *
     * @param activationFunction The activation function to be used in the neural net.
     * @param nodeConfiguration  A list of numbers corresponding to the number of edges at each
     *                           layer. For example: [2, 2, 1] represents a neural net with 2
     *                           inputs, 2 hidden layers and 1 output.
     * @param learningRate       The learning rate of the network.
     */
    public NeuralNetwork(OutputFunction<Double, Double> activationFunction, double learningRate,
                         List<Integer> nodeConfiguration) {
        this.activationFunction = activationFunction;
        this.learningRate = learningRate;
        nodeConfig = nodeConfiguration;
        edges = new HashMap<>();
        allNodes = new ArrayList<>();

    }

    /**
     * Default Constructor. Weights are randomized and inputs set to 0 as default.
     *
     * @param activationFunction The activation function to be used in the neural net.
     */
    public NeuralNetwork(OutputFunction<Double, Double> activationFunction) {
        this.activationFunction = activationFunction;
        nodeConfig = new ArrayList<>();
        edges = new HashMap<>();
        allNodes = new ArrayList<>();
    }

    /**
     * Trains the network epoch times.
     *
     * @param inputOutputMap A map containing the input and output lists to be used for
     *                       training. Note if the neural net only has x input edges, then it
     *                       will use the first x entries of the list. Likewise when the list
     *                       is smaller than the number of input edges. The same applies for
     *                       the outputs.
     * @param epoch          Epoch time of the training.
     */
    public abstract void train(Map<List<I>, List<O>> inputOutputMap, int epoch);

    /**
     * Back propagates the error in the network.
     */

    public abstract void backpropagate(Double targetOutput, Double actualOutput);

    /**
     * Returns a list of output results given a list of inputs.
     *
     * @param inputs A list of inputs. Note if the neural net only has x input edges,
     *               then it will use the first x entries of the list. Likewise when
     *               the list is smaller than the number of input edges.
     * @return Returns a list of output results given a list of inputs.
     */
    public abstract List<O> predict(List<I> inputs);

    /**
     * Sets the input values of the input edges in the neural network.
     *
     * @param inputs A list of inputs. Note if the neural net only has x input edges,
     *               then it will use the first x entries of the list. Likewise when
     *               the list is smaller than the number of input edges.
     */
    public abstract void input(List<I> inputs);

    /**
     * Returns a list of outputs given the existing inputs.
     *
     * @return Returns a list of outputs given the existing inputs.
     */
    public abstract List<O> output();



    /**
     * Sets the learning of the network.
     *
     * @param learningRate  The learning rate
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * @return Returns the learning rate of the network.
     */
    public synchronized double getLearningRate() {
        return learningRate;
    }

    /**
     * Returns a string representation of the node configuration.
     *
     * @return Returns a string representation of the node configuration.
     */
    public synchronized String getNodeConfig() {
        return nodeConfig.toString();
    }

    /**
     * Returns a map of edges at each layer of the network.
     *
     * @return Returns a map of edges at each layer of the network.
     */
    public synchronized Map<Integer, List<NodeEdge>> getNodeEdges() {
        return edges;
    }

    public synchronized int getNumOfNodes() {

        return allNodes.size();

    }


    /**
     * Returns the input edges.
     *
     * @return returns the input edges.
     */
    public List<INode> getInputNodes() {


        List<INode> nodes = getNodesAtLayer(0);
        nodes.removeIf(e -> e.getClass().equals(Bias.class));

        return nodes;

    }

    /**
     * Returns the output edges.
     *
     * @return Returns the output edges.
     */
    public List<INode> getOutputNodes() {
        List<INode> nodes = getNodesAtLayer(nodeConfig.size()-1);
        nodes.removeIf(e -> e.getClass().equals(Bias.class));
        return nodes;
    }

    /**
     * Returns the number of layers of the NN.
     * @return Returns the number of layers of the NN.
     */
    public synchronized int getNumberOfLayers() {
        return Collections.max(edges.keySet());
    }

    /**
     * Returns the activation function being used.
     * @return Returns the activation function being used.
     */
    public synchronized OutputFunction<Double, Double> getActivationFunction() {
        return activationFunction;
    }

    @Override
    public String toString() {

        String nodesString = "";

        for (int layer : edges.keySet()) {


            for (NodeEdge edge : edges.get(layer)) {
                nodesString += edge.toString() + " \n";
            }
        }
        return nodesString;
    }

    /**
     * Returns the input edges of a given node.
     * @param node  A node in the NN.
     * @return Returns the input edges of a given node.
     */
    public List<NodeEdge> getInputEdgesFor(INode node) {


        ArrayList<NodeEdge> edges = getAllEdges();
        edges.removeIf(edge -> !edge.getToNode().equals(node));

        return edges;

    }

    /**
     * Return the output edges of a given node.
     * @param node  A node in the NN.
     * @return Return the output edges of a given node.
     */
    public List<NodeEdge> getOutputEdgesFor(INode node) {

        List<NodeEdge> edges = getAllEdges();
        edges.removeIf(edge -> !edge.getFromNode().equals(node));

        return edges;

    }

    /**
     * Returns a list of nodes at a given layer.
     * @param layer The layer in the NN.
     * @return Returns a list of nodes at a given layer.
     */
    public synchronized List<INode> getNodesAtLayer(int layer) {

        if (layer > nodeConfig.size() || layer < 0) {
            throw new IllegalArgumentException("Layer index out of bounds");
        }

        List<INode> nodesAtLayer = new ArrayList<>(allNodes);
        nodesAtLayer.removeIf(e -> e.getLayer() != layer);

        return nodesAtLayer;

    }


    private synchronized ArrayList<NodeEdge> getAllEdges() {
        Collection<List<NodeEdge>> listEdges = this.edges.values();
        ArrayList<NodeEdge> edges = new ArrayList<>();

        for (List<NodeEdge> listEdge : listEdges) {
            edges.addAll(listEdge);
        }

        return edges;
    }

    /**
     * Returns the bias node at a given layer.
     * @param layer     The layer in the NN.
     * @return  Returns the bias node at a given layer.
     */
    public double getBiasAt(int layer) {

        List<INode> nodesAtLayer = getNodesAtLayer(layer);
        nodesAtLayer.removeIf(e -> !e.getClass().equals(Bias.class));

        return ((Bias)nodesAtLayer.get(0)).getValue();

    }

    /**
     * Returns the input nodes connected by NodeEdge to the given node in the NN.
     * @param node  A node in the NN.
     * @return Returns a List of INode objects connected to the given node by NodeEdge objects.
     */
    public List<INode> getInputNodesFor(INode node) {

        List<NodeEdge> edges = getInputEdgesFor(node);
        List<INode> nodes = new ArrayList<>();

        for (NodeEdge edge : edges) {
            if (!edge.getFromNode().equals(node))
                nodes.add(edge.getFromNode());
            else
                nodes.add(edge.getToNode());
        }

        return nodes;
    }

    /**
     * Returns a list of INode objects that the given node connects to through NodeEdge objects.
     * @param node  A node in the NN.
     * @return  Returns a list of INode objects that the given node connects to through NodeEdge objects.
     */
    public List<INode> getOutputNodesFor(INode node) {

        List<NodeEdge> edges = getOutputEdgesFor(node);
        List<INode> nodes = new ArrayList<>();

        for (NodeEdge edge : edges) {
            if (edge.getFromNode().equals(node))
                nodes.add(edge.getFromNode());
        }

        return nodes;
    }

    /**
     * Returns the map holding the edges per layer.
     *
     * @return Returns the map holding the edges per layer.
     */
    public synchronized Map<Integer, List<NodeEdge>> getEdges() {
        return edges;
    }

    public void setVisualizer(NeuralNetworkVisualizer neuralNetworkVisualizer) {
        this.nnVisualizer = neuralNetworkVisualizer;
    }
}
