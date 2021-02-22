package ma.ensias.VirusCop.main;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import ma.ensias.VirusCop.agents.Virus;
import ma.ensias.VirusCop.agents.FixCop;
import ma.ensias.VirusCop.util.Plateforme;

public class Main {
	
	// DECLARE GRAPH
	public static Graph graph = new SingleGraph("agentsGraph");
	
	
	public Main() throws StaleProxyException {
		
	
		List<ContainerController> containers = new ArrayList<>();

		
		// LIST OF CONTAINER NAMES : TO USE FOR THE INTRUDER MOBILITY.
		List<String> containersNames = new ArrayList<String>();
		
		// LIST OF ITINERARY : TO USE FOR THE POLICE MOBILITY. -- WILL BE FILLED USING DFS + RETURN TO stationContainer CONTAINER.
		List<String> itinerary = new ArrayList<String>();
		
		// MAIN CONTAINER.
		ContainerController mainContainer = new Plateforme(true, null).getMainContainer();
		
		// stationContainer CONTAINER.
		ContainerController rootContainer = new Plateforme(false, "stationContainer").getAContainer();
		
		// ADD stationContainer NAME TO %containersNames% LIST.
		containersNames.add("stationContainer");

		// ADD stationContainer TO THE GRAPH.
		graph.addNode("stationContainer").setAttribute("ui.label", "stationContainer");
		graph.getNode("stationContainer").addAttribute("contaminated", false);
		
		
		// ADD NODES TO THE LIST OF Names & TO THE GRAPH.
		for(int i=0; i<10; i++) {
			ContainerController ccbuffer = new Plateforme(false, "container_n"+i).getAContainer();
			
			containers.add(ccbuffer);
			containersNames.add("container_n"+i);

			graph.addNode("container_n"+i).setAttribute("ui.label", "container_n"+i);
			graph.getNode("container_n"+i).addAttribute("contaminated", false);
		}
		
        // DEFINE EDGES IN GRAPH.
		graph.addEdge("root-container_n0", "stationContainer", "container_n0");
        graph.addEdge("container_n0-container_n1", "container_n0", "container_n1");
        graph.addEdge("root-container_n2", "stationContainer", "container_n2");
        graph.addEdge("root-container_n3", "stationContainer", "container_n3");
        graph.addEdge("container_n2-container_n4", "container_n2", "container_n4");
        graph.addEdge("container_n2-container_n5", "container_n2", "container_n5");
        
        graph.addEdge("container_n5-container_n6", "container_n5", "container_n6");
        graph.addEdge("container_n5-container_n7", "container_n5", "container_n7");
        
        graph.addEdge("container_n4-container_n8", "container_n4", "container_n8");
        graph.addEdge("container_n4-container_n9", "container_n4", "container_n9");
        
        
        graph.display(true);

		
		// FILL THE ITINERARY.
		Iterator<Node> itineraryIterator = graph.getNode("stationContainer").getDepthFirstIterator();

		// CONVERT DFS ITERATOR TO LIST OF STRINGS.
		while (itineraryIterator.hasNext()) {
			itinerary.add(itineraryIterator.next().getAttribute("ui.label"));
		}	
		// ADD stationContainer TO COMPLETE THE ITINERARY.
		itinerary.add("stationContainer");
		
		// AgentController : to create an agent named "StationPolice"
		AgentController acStation = rootContainer.createNewAgent("StationPolice", FixCop.class.getName(), new Object []{itinerary, 3000});
		acStation.start();
		// AgentController : to create an intruder agent named "Virus"
		AgentController acIntrus = containers.get(3).createNewAgent("Virus", Virus.class.getName(), new Object []{containersNames, 3000});
		acIntrus.start();
	}
	public static void main(String[] args) throws StaleProxyException {new Main();}
	
	
	
}