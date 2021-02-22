package ma.ensias.behaviours;

import java.util.List;
import java.util.Random;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.TickerBehaviour;
import ma.ensias.agents.Intrus;

public class IntrusMoveRandomBehaviour extends TickerBehaviour {
	private List<String> containersNames;
	
	public IntrusMoveRandomBehaviour(Agent a, long period, List<String> containersNames) {
		super(a, period);
		this.containersNames = containersNames;
	}

	@Override
	protected void onTick() {
	    Random rand = new Random();
	    String randomContainer = containersNames.get(rand.nextInt(containersNames.size()));
	    ContainerID cid = new ContainerID();
		cid.setName(randomContainer);
		cid.setAddress("localhost");
		getAgent().doMove(cid);

	}

}
