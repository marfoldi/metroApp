package fr.ecp.is1220.tutorial5.ex2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MetroUserTerminal {
	/**
	 * A class that provides a command prompt for the user to parse and explore
	 * a metro map
	 */
	
	
	private static MetroMapParser mmp = null; // Initialization
	
	// the reception message to bo displayed before getting any command
	public static final String reception= "the metro map application - type 'help' for a list of available commands or 'stop' to quit"; 
	
	/**
	 * A method that reads the command entered by the user
	 * and throws an exception if the user tries to explore a map 
	 * that is not already parsed
	 * @return
	 * * @throws NoGraphParsedException
	 */
	public static String readCommand() throws NoGraphParsedException{
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String command = reader.nextLine(); // Scans the next token of the input as an String.
		ArrayList<String> requireParsedGraph= new ArrayList<String>(); //creating a list with the commands that require
		requireParsedGraph.add("list");									// a map to be already parsed
		requireParsedGraph.add("connect");
		requireParsedGraph.add("succ");
		
		if ((requireParsedGraph.contains(command)) && (mmp==null)) // the user can't explore a map that is null !
			throw new NoGraphParsedException("There is no metro map parsed");
		return command;
	}
	
	/**
	 * The main method provides the command prompt with commands:
	 * "loadmap: TO LOAD A METRO NETWORK MAP FROM A FORMATTED METRO-MAP TEXTUAL FILE");
	 * "boston: TO AUTOMATICALLY LOAD BOSTON METRO NETWORK FROM FILE 'bostonmetro.txt'"
	 * "list: TO LIST ALLA STATIONS IN THE LOADED METRO NETWORK"
	 * "connect: TO FIND A CONNECTING PATH FROM A SOURCE TO A TARGET STATION"
	 * "succ: TO FIND THE LIST OF STATIONS REACHABLE IN ONE HOP FROM A GIVEN SOURCE STATION"
	 * "stop: TO HASL THE PROGRAM"
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		while(true){
			System.out.println(reception);
			try{
				String command=readCommand();
				switch(command){
					case "help": 
						System.out.println("---- the following commands are available ----");
						System.out.println("loadmap: TO LOAD A METRO NETWORK MAP FROM A FORMATTED METRO-MAP TEXTUAL FILE");
						System.out.println("boston: TO AUTOMATICALLY LOAD BOSTON METRO NETWORK FROM FILE 'bostonmetro.txt'");
						System.out.println("list: TO LIST ALLA STATIONS IN THE LOADED METRO NETWORK");
						System.out.println("connect: TO FIND A CONNECTING PATH FROM A SOURCE TO A TARGET STATION");
						System.out.println("succ: TO FIND THE LIST OF STATIONS REACHABLE IN ONE HOP FROM A GIVEN SOURCE STATION");
						System.out.println("stop: TO HASL THE PROGRAM");
						System.out.println("----------------------------------------------");
						break;
					
					case "loadmap": 
						Scanner reader = new Scanner(System.in);
						System.out.println("enter the location of the formatted text file containing the metro network:");
						String location = reader.nextLine();
						mmp = new MetroMapParser(location);
						mmp.generateGraphFromFile();
						System.out.println("your map is parsed correctly");  
		
						System.out.println("----------------------------------------------");
						break;
					
					case "boston":
							mmp = new MetroMapParser("src\\fr\\ecp\\is1220\\tutorial5\\ex2\\bostonmetro.txt");
							mmp.generateGraphFromFile();
							System.out.println("boston metro map is parsed correctly");
							
						System.out.println("----------------------------------------------");
						break;
					
						
					case "list": 
						if (mmp==null) throw new NoGraphParsedException("There is no metro map parsed");
						try{
						for (Node temp:mmp.metroGraph.getNodes()) System.out.println(temp);
						} catch(NoGraphParsedException e){
							
						}
						System.out.println("----------------------------------------------");
						break;
						
					case "connect": 
						System.out.println("connection command");
						System.out.println(">> enter the name of the ORIGIN station:");
						reader = new Scanner(System.in);
						String origin = reader.nextLine();
						System.out.println("origin node:"+mmp.metroGraph.findNodeByInfo(origin));
						
						System.out.println("connection command");
						System.out.println(">> enter the name of the Destination station:");
						reader = new Scanner(System.in);
						String destination = reader.nextLine();
						System.out.println("destination node:"+mmp.metroGraph.findNodeByInfo(destination));
						
						ArrayList<Integer> route = new ArrayList<Integer>();
						route = mmp.metroGraph.findConnection(mmp.metroGraph.findNodeByInfo(origin), 	mmp.metroGraph.findNodeByInfo(destination));
			            route = (ArrayList<Integer>) route.clone();
			            Collections.reverse(route);
			            ArrayList<String> routeInfo = new ArrayList<String>();
			            for (int temp : route) routeInfo.add(mmp.metroGraph.findNodeByID(temp).getNodeInfo());
			            
			            System.out.println("from "+origin+" you can reach "+destination+" via route: "+routeInfo);
			            
			            System.out.println("----------------------------------------------");
			            break;
			            
					case "succ":
						System.out.println("connection command");
						System.out.println(">> enter the name of the ORIGIN station:");
						reader = new Scanner(System.in);
						String origin1 = reader.nextLine();
						ArrayList<String> successorsInfo = new ArrayList<String>();
						ArrayList<Integer> successorsOfOrigin= mmp.metroGraph.findNodeByInfo(origin1).getSuccessors();
						for (int temp: successorsOfOrigin) successorsInfo.add(mmp.metroGraph.findNodeByID(temp).getNodeInfo());
						System.out.println("stations reachable in one hop from "+ mmp.metroGraph.findNodeByInfo(origin1).getNodeInfo() +" are: "+successorsInfo);
						
						System.out.println("----------------------------------------------");
						break;
						
					case "stop": 
						System.out.println("Thank you for choosing our program");
						System.out.println("Goodbye ! :)");
						System.exit(0);
						
					default: 
						System.out.println("wrong command entered");
						
						System.out.println("----------------------------------------------");
						break;
				}
			
			}catch(NoGraphParsedException | NodeNotFoundException | IOException e){
				System.out.println("ERROR: "+e);
				System.out.println("----------------------------------------------");
			}
		}
}
}
