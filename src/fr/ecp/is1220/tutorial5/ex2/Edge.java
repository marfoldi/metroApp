package fr.ecp.is1220.tutorial5.ex2;

public class Edge<T1, T2> {
	
	private String lineName;
	private int successor1,successor2;
	

	public Edge(String lineName, int parseInt, int parseInt2) {
		this.lineName=lineName;
		this.successor1=parseInt;
		this.successor2=parseInt2;
	}


	@Override
	public String toString() {
		return "Edge [lineName=" + lineName + ", link between " + successor1 + " and " + successor2 + "]";
	}
	

}
