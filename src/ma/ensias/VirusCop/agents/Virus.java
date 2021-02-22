package ma.ensias.VirusCop.agents;

import java.util.List;
import java.util.Random;

import ma.ensias.VirusCop.behaviours.VirusMoveRandomBehaviour;
import ma.ensias.VirusCop.util.utils;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.ControllerException;

public class Virus extends Agent {
	
	protected List<String> containersNames;
	
	protected void setup() {
		System.out.println("Agent Intrus cree : " + getLocalName());

		Object[] args= getArguments();
		containersNames = (List<String>) args[0];
		int period = (int) args[1];
		
		// INTRUS AGENT MOVING RANDOMLY THROUGH ALL NODES.
        addBehaviour(new VirusMoveRandomBehaviour(this, period, containersNames));
        
        
        
	}
	
	//la terminaison de l'agent
	protected void takeDown() {
        System.out.println("Agent detruit : "+getLocalName());
    }
	
	
	// BEFORE MOVE, CONTAMINATE THE NODE.
	protected void beforeMove() {
		try {
			String nodeName = getContainerController().getContainerName();
			if(!utils.isContaminted(nodeName)) {
				utils.contaminate(nodeName);
				System.out.println("Intruder has contaminated the node: "+ nodeName);
			}
		} catch (ControllerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	
	}

	
	
}
