package fr.ecp.is1220.tutorial5.ex2;


import java.util.ArrayList;
 /** Graph class is a representation of a graph.
  * A graph has edges and nodes
  */
public class Graph<T1, T2, T3> {
	private static int numOfGraph = 0;
	private int graphID ;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	
	
	public Graph() {
		super();
		this.graphID=numOfGraph++;
	}

	public void addEdge(Edge<Integer, String> edge) {
		this.edges.add(edge);
	}

	public void addNode(Node<Integer, String> aMetroStation) {
		this.nodes.add(aMetroStation);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public Node findNodeByID(int i) throws NodeNotFoundException {
		boolean found  = false;
		for (Node temp : nodes){
			if (temp.getId().equals(i)) return temp;
		}
		throw new NodeNotFoundException("there is no station with ID= "+i);
	}
	
	public Node findNodeByInfo(String stationName) throws NodeNotFoundException {
		for (Node temp : nodes){
			if (temp.getNodeInfo().equals(stationName)) return temp;
		} 
		throw new NodeNotFoundException("there is no station named= "+stationName);
	}

	public ArrayList<Integer> findConnection(Node origin, Node destination) {
		
		//Initialisation
		for (Node u : nodes){
			u.setVisited(false);
			u.setDistance(999);
			u.setParent(null);
		}
		// the origin is the first to be visited
		origin.setVisited(true);
		origin.setParent(null);
		origin.setDistance(0);
		
		/** make a list for visited nodes */
		ArrayList<Node> Q = new ArrayList<Node>();
		Q.add(origin);
		
		while (!Q.isEmpty()){
			
			Node u = Q.get(0);
			Q.remove(0);
			ArrayList<Integer> successorsOfU= u.getSuccessors();
			for (int temp : successorsOfU ){  // I Don't know why it couldn't accept Node temp without creating successorsOfU
				Node v;
				try {
					v = findNodeByID(temp);
					if (!v.isVisited()){
						v.setVisited(true);
						v.setDistance(u.getDistance()+1);
						v.setParent(u);
						Q.add(v);
					}
				} catch (NodeNotFoundException e) {
					e.printStackTrace();
				}
				
				
			}
		}
		
		ArrayList<Integer> route = new ArrayList<Integer>();
		Node u = destination;
		route.add((Integer) u.getId());
		while (route.get(route.size() - 1) != origin.getId()){
			route.add((Integer) u.getParent().getId());
			Node temp = u.getParent();
			u = (Node) temp;
		}
		
		return route;
	}

	

	
	
}
