package neuralnets;

import neuralnets.nodes.HiddenNode;
import neuralnets.nodes.INode;

public class FreeNetwork extends SimpleNeuralNet{

	public FreeNetwork() {
		super();
	}
	
	/**
	 * Connects two edges together by an edge.
	 * @param a			The first node.
	 * @param b			The second node.
	 * @param layerA	The layer of the first node. Can be any node.
	 * @param layerB	The layer of the second node. Cannot be an input node.
	 */
	public void connect(INode a, HiddenNode b, int layerA, int layerB) {
		
		/*if (layerA < 0 || layerB < 0) {
			throw new IllegalArgumentException("layer number must be larger than 0");
		}

		if (edges.containsKey(layerA)) {
			if (!edges.get(layerA).contains(a)) {
				edges.get(layerA).add(a);
			}
		} else
			edges.put(layerA, new ArrayList<>());

		if (edges.containsKey(layerB)) {
			if (!edges.get(layerB).contains(b)) {
				edges.get(layerB).add(b);
			}

		} else
			edges.put(layerB, new ArrayList<>());

		b.inputEdges.add(new NodeEdge(a, b));*/
		
	}
	

}
