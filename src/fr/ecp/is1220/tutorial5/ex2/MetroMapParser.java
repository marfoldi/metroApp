package fr.ecp.is1220.tutorial5.ex2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * 
 * a class for parsing formatted textual files that contains the description of 
 * the metro network of a city
 *
 */
public class MetroMapParser {
	
	/**
	 * the metromap-file containing the textual description of the metro network to be parsed
	 */
	private BufferedReader fileInput;
	
	/**
	 * the graph where the result of parsing is going to be stored
	 */
	public Graph<Integer,String,String> metroGraph = new Graph<Integer,String,String>();
         
    
    public MetroMapParser(){
    	
    }
    
   
    /**
     * Builds a MetroMapParser  linked to a metromap-file with a given filename 
     * @param filename: the file containing the textual description of the metro network to be parsed
     * @throws IOException
     */
    public MetroMapParser(String filename) throws IOException {
         //a buffered reader reads line by line, returning null when file is done
        fileInput = new BufferedReader(new FileReader(filename));
    }


    /**
     * parses the fileinput and store the outcome into the metroGraph
     * @throws IOException if problem reading fileinput
     * @throws BadFileException if hte format of fileinput is not correct
     */
    public  void  generateGraphFromFile() throws IOException, BadFileException {
        String line = fileInput.readLine();
        StringTokenizer st;
        String stationID;
        String stationName;
        String lineName;
        String outboundID, inboundID;
        

        // processing the metromap file line-by-line
        while(line != null) {
            //in this loop, the program  collect the information necessary to 
            //construct the metro-graph, and  construct the graph as well.
            //
            //StringTokenizer is a java.util Class that can break a string into tokens
            // based on a specified delimiter.  The default delimiter is " \t\n\r\f" which
            // corresponds to the space character, the tab character, the newline character,
            // the carriage-return character and the form-feed character.
            st = new StringTokenizer(line);

            //We want to handle empty lines effectively, we just ignore them!
            if(!st.hasMoreTokens()) {
                line = fileInput.readLine();
                continue;
            }
    
            //from the grammar, we know that the Station ID is the first token on the line
            stationID = st.nextToken();
    
            if(!st.hasMoreTokens()) {
                throw new BadFileException("no station name");
            }

            //from the grammar, we know that the Station Name is the second token on the line.
            stationName = st.nextToken();
    
            //MetroStation aStation = new MetroStation(Integer.parseInt(stationID),stationName);
            Node<Integer,String> aMetroStation = new Node<Integer, String>(Integer.parseInt(stationID), stationName);
            
            if(!st.hasMoreTokens()) {
                throw new BadFileException("station is on no lines");
            }

            // iteratively process the description of  nodes in the list of adjacent station 
            while(st.hasMoreTokens()) {
            	// the first token is the name of the line of the next pair of adjacent station
                lineName = st.nextToken();
                if(!st.hasMoreTokens()) {
                    throw new BadFileException("poorly formatted line info");
                }
                
                // second token is the name of first adjacent station
                outboundID = st.nextToken();
                if(!st.hasMoreTokens()) {
                    throw new BadFileException("poorly formatted adjacent stations");
                }
                
                // third token is the name of second adjacent station
                inboundID = st.nextToken();
                
                // retrieving the numericID of both adjacent stations
                Integer num_outboundID = Integer.parseInt(outboundID);
                Integer num_inboundID = Integer.parseInt(inboundID);
                
                // adding a bidirectional edge (i.e. two edges) between this station and first adjacent station
                this.metroGraph.addEdge(new Edge<Integer,String>(lineName, Integer.parseInt(stationID), num_inboundID));
                this.metroGraph.addEdge(new Edge<Integer,String>(lineName, num_inboundID, Integer.parseInt(stationID)));
                
                // adding a bidirectional edge (i.e. two edges) between this station and second adjacent station
                this.metroGraph.addEdge(new Edge<Integer,String>(lineName, num_outboundID, Integer.parseInt(stationID)));
                this.metroGraph.addEdge(new Edge<Integer,String>(lineName, Integer.parseInt(stationID), num_outboundID));
                
                
                // adding first adjacent station to successors of this station  
                if(num_outboundID != 0){
                	aMetroStation.addSuccessor(num_outboundID);
                }
                
                // adding second adjacent station to successors of this station
                if(num_inboundID != 0){
                	aMetroStation.addSuccessor(num_inboundID);
                }
                
                
            }
  
            // add the Node for this station to the metro-graph
            this.metroGraph.addNode(aMetroStation);
            
            // read the next line of the metromap file
            line = fileInput.readLine();
        }
    } 
    
  
    /**
     * this a simple main method to test locally whether the parser works as expected
     * it requires passing the name of the metromapfile as the first parameter of the main
     * @param args the first argument must be the name of the file containing the metro map
     */
    public static void main(String[] args) {
        if(args.length != 1) {
           System.out.println("Pass the fileinput name as parameter to the main method");
            System.exit(0);
        }
        String filename = args[0];
        try {
            MetroMapParser mmp = new MetroMapParser(filename);
            mmp.generateGraphFromFile();
            System.out.println("scanned stations: " + mmp.toString());
            System.out.println("bostonMetrograph NODES: " + mmp.metroGraph.getNodes());
            System.out.println("bostonMetrograph EDGES: " + mmp.metroGraph.getEdges());
            
            for(Node<Integer,String> n:mmp.metroGraph.getNodes()){
            	if(n.getId().equals(20)){
            		System.out.println("node " + n.getNodeInfo() + " successors: " + n.getSuccessors());
            	}
            	if(n.getId().equals(27)){
            		System.out.println("node " + n.getNodeInfo() + " successors: " + n.getSuccessors());
            	}
            }
            ArrayList<Integer> reversedRoute = new ArrayList<Integer>();
            ArrayList<Integer> route = new ArrayList<Integer>();
            route = mmp.metroGraph.findConnection(mmp.metroGraph.findNodeByID(1), 	mmp.metroGraph.findNodeByID(5));
            route = (ArrayList<Integer>) route.clone();
            Collections.reverse(route);
            
            //System.out.println("connection OakGroove and Wellington :" + mmp.bostonMetroGraph.findConnection(mmp.bostonMetroGraph.findNode(1),mmp.bostonMetroGraph.findNode(5)) );
            System.out.println("connection OakGroove and Wellington :" + route );
            
            System.out.println("found node by name OakGroove:" + mmp.metroGraph.findNodeByInfo("OakGrove"));
            System.out.println("found node by ID 1:" + mmp.metroGraph.findNodeByID(1));
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
