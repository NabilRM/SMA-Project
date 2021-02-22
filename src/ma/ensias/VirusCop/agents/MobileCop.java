package ma.ensias.VirusCop.agents;

import java.util.List;

import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.QueryAgentsOnLocation;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import ma.ensias.VirusCop.behaviours.MovingCopBehaviour;
import ma.ensias.VirusCop.behaviours.SearchAndKillBehaviour;
import ma.ensias.VirusCop.util.utils;


public class MobileCop extends Agent {

	
	protected List<String> itinerary;
	protected int nodeStep = 0; // Starting with stationContainer;
	private int period;
	protected void setup() {
		// DEFINE ONTOLOGY & FIPA CODEC NORM.
		getContentManager().registerOntology(JADEManagementOntology.getInstance());
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
        		

		Object[] args = getArguments();
		
		itinerary = (List<String>) args[0];
		period = (int) args[1];
		

		System.out.println("Agent MobileCop cree : " + getLocalName());
		// MOBILE COP AGENT IS MOVING THROUGH ITINERARY, SEARCHING FOR THE INTRUDER. ENDING ITS ITINERARY WITH ROOT.
        addBehaviour(new MovingCopBehaviour(this, itinerary, period));
        
      // CYCLICBEHAVIOUR SCAN TO SEARCH THE INTRUDER AGENT, AND KILL IT IF EXISTS.
        addBehaviour(new SearchAndKillBehaviour(this));
        
	}
	
	//la terminaison de l'agent
	protected void takeDown() {
        System.out.println("Agent MobileCop a fait le tour est detruit : "+getLocalName());
    }
	
	
	protected void beforeMove() {
		
	}
	
    //au niveau du container d'arrivée
	protected void afterMove() {
        try {
            getContentManager().registerOntology(JADEManagementOntology.getInstance());
            getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
			System.out.println("Agent ("+getLocalName()+") arrivée : "+getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
