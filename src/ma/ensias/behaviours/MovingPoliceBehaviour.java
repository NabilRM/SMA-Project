package ma.ensias.behaviours;

import java.util.List;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.TickerBehaviour;

public class MovingPoliceBehaviour extends TickerBehaviour {

	
	protected List<String> itinerary;
	protected int nodeStep = 0; // Starting with stationContainer (racine).;
	private int period;
	
	public MovingPoliceBehaviour(Agent a, List<String> itinerary, int period) {
		super(a, period);
		this.itinerary = itinerary;
	}
	@Override
	protected void onTick() {
		
		System.out.println(itinerary.get(nodeStep));
		ContainerID cid = new ContainerID();
		cid.setName(itinerary.get(nodeStep));
		cid.setAddress("localhost");
		getAgent().doMove(cid);
		
		// if we're at the last step of the itinerary.
		if(nodeStep+1==itinerary.size()) {
		getAgent().doDelete();
		} 
		nodeStep++;

	}

}
