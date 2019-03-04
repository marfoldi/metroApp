package fr.ecp.is1220.tutorial5.ex2;

import java.util.ArrayList;

public class Node<T1, T2> {

	private int id;
	private String info;
	private ArrayList<Integer> successors = new ArrayList<Integer>();
	private boolean isVisited = false;
	private Node parent=null;
	private int distance=999;
	
	public Node(int parseInt, String stationName) {
		this.id= parseInt;
		this.info=stationName;
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void addSuccessor(Integer num_outboundID) {
		this.successors.add(num_outboundID);
	}

	public Object getId() {
		return this.id;
	}

	public  ArrayList<Integer> getSuccessors() {
		return this.successors;
	}

	public String getNodeInfo() {
		return this.info;
	}
	

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean visit) {
		this.isVisited = visit;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", station=" + info +  "]";
	}

	

	
	
}
