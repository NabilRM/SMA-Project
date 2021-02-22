package ma.ensias.behaviours;

import java.util.List;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ma.ensias.agents.PolicierMobile;

public class RespawnPoliceBehaviour extends CyclicBehaviour {
	
	List<String> itinerary;
	private int period;
	public RespawnPoliceBehaviour(Agent a, int period, List<String> itinerary) {
		super(a);
		this.itinerary = itinerary;
		this.period = period;
	}
	@Override
	public void action() {
		int lifetime = period * itinerary.size() +2000;
		try {
			AgentController acMobile = getAgent().getContainerController().createNewAgent("mobilePoliceAgent", PolicierMobile.class.getName(), new Object []{itinerary, period});
			acMobile.start();
			Thread.sleep(lifetime);
		} catch (StaleProxyException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
