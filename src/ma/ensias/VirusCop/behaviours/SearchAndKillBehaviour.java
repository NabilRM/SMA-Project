package ma.ensias.VirusCop.behaviours;

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
import jade.wrapper.ControllerException;
import ma.ensias.VirusCop.util.utils;

public class SearchAndKillBehaviour extends CyclicBehaviour {

	
	public SearchAndKillBehaviour(Agent a) {
		super(a);
	}
	
	
	@Override
	public void action() {
		//BEFORE MOVE, UNCONTAMINATE THE NODE.
		try {
			String nodeName = getAgent().getContainerController().getContainerName();
			if(utils.isContaminted(nodeName) && !getAgent().getContainerController().getContainerName().equals("stationContainer")) {
				utils.uncontaminate(nodeName);
				System.out.println("Policier has uncontaminated the node: "+ nodeName);
			}
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		try {
			if(!getAgent().getContainerController().getContainerName().equals("stationContainer")) {
				  Action action = new Action();
			        QueryAgentsOnLocation queryOnLocation = new QueryAgentsOnLocation();
			        //CREATE LOCATION OF THE MAIN-CONTAINER (WHICH CONTAINS THE OTHER TARGET AGENT 2 KILL).
				    ContainerID cid = new ContainerID();
					try {
						cid.setName(getAgent().getContainerController().getContainerName());
					} catch (ControllerException e1) {
						e1.printStackTrace();
					}
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
							  try {
								  if(ae.getLocalName().equals("Virus")) {
									  AgentController target = getAgent().getContainerController().getAgent("Virus");
									  // KILL HERE.
									  System.out.println(target.getName() + " FOUND  &  KILLED. by " + this.getAgent().getName());
									  target.kill();  
									  break;
								  }
							  }catch(Exception e) {}
						  }
 
					  }
					  catch (Exception e) { 
						  e.printStackTrace();
					  }
			  }
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

			

	}

}
