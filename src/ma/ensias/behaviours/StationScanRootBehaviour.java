package ma.ensias.behaviours;

import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.QueryAgentsOnLocation;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import ma.ensias.util.utils;

public class StationScanRootBehaviour extends CyclicBehaviour {
	
	
	public StationScanRootBehaviour(Agent a) {
		super(a);
	}

	@Override
	public void action() {
		if(utils.isContaminted("stationContainer")) {
			utils.uncontaminate("stationContainer");
			System.out.println("PolicierStation has uncontaminated the node: root");
		}
		
		Action action = new Action();
        QueryAgentsOnLocation queryOnLocation = new QueryAgentsOnLocation();
        //CREATE LOCATION OF THE MAIN-CONTAINER (WHICH CONTAINS THE OTHER TARGET AGENT 2 KILL).
	    ContainerID cid = new ContainerID();
		cid.setName("stationContainer");
		cid.setAddress("localhost");
        queryOnLocation.setLocation(cid);
		action.setAction(queryOnLocation);
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(getAgent().getAMS());
		request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
		request.setOntology(JADEManagementOntology.NAME);
		action.setActor(getAgent().getAID());
		try {
		getAgent().getContentManager().fillContent(request, action);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// SEND REQUEST : RETRIEVING * AGENTS ON CURRENT CONTAINER.
		getAgent().send(request);

		
			// GETTING RESPONSE BY : blockReceive()
		  ACLMessage receivedMessage = getAgent().blockingReceive(MessageTemplate.MatchSender(getAgent().getAMS())); 
		  try {
			  
			  Result result = (Result) getAgent().getContentManager().extractContent(receivedMessage);
			  
			  // GETTING THE LIST OF AGENTS.
			  jade.util.leap.List listOfAgents = (jade.util.leap.List) result.getValue();
			  
			  //SEARCHING & KILLING THE TARGET AGENT.
			  for(int i=0; i<listOfAgents.size(); i++) {
				  AID ae = (AID) listOfAgents.get(i);
			  	// IF THE Intrus AGENT IS FOUND
				  
				  if(ae.getLocalName().equals("Intrus")) {
					  try {
						  AgentController target = getAgent().getContainerController().getAgent("Intrus");
						  // KILL HERE.
						  System.out.println(target.getName() + " FOUND  &  KILLED. by " + this.getAgent().getName());
						  target.kill();
						  break;
					  
					  }catch(Exception e) {}
			  }
				  
			  }
			  
		  }
		  catch (Exception e) { 
			  e.printStackTrace();
		  }

	}

}
