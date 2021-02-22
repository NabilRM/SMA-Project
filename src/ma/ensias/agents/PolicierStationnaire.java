package ma.ensias.agents;

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
import jade.wrapper.StaleProxyException;
import ma.ensias.behaviours.RespawnPoliceBehaviour;
import ma.ensias.behaviours.StationScanRootBehaviour;
import ma.ensias.main.Main;

public class PolicierStationnaire extends Agent {
	

	protected void setup() {
		// DEFINE ONTOLOGY & FIPA CODEC NORM.
		getContentManager().registerOntology(JADEManagementOntology.getInstance());
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
		System.out.println("Agent Police Stationnaire cree : "+getLocalName());
		Object[] args = getArguments();
		List<String> itinerary = (List<String>) args[0];
		int period = (int) args[1];
		
		
	// POLICIER STATIONNAIRE DO THE CYCLIC BEHAVIOUR TO SCAN THE ROOT , IF THE INTRUDER IS IN ROOT, POLICIER STATIO KILLS HIM.
        addBehaviour(new StationScanRootBehaviour(this));

	// RESPAWN ANOTHER POLICIER MOBILE AFTER EACH POLICIER MOBILE's LIFETIME DURATION ELAPSES.
        addBehaviour(new RespawnPoliceBehaviour(this, period, itinerary));

	}
	
}
