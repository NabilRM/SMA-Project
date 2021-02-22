package ma.ensias.VirusCop.util;

import ma.ensias.VirusCop.main.Main;

public class utils {

	public static void uncontaminate(String nodeName) {
		Main.graph.getNode(nodeName).setAttribute("contaminated", false);
		Main.graph.getNode(nodeName).setAttribute("ui.style", "fill-color: rgb(0,0,0);size:10px;");
	}

	public static boolean isContaminted(String nodeName) {
		return Main.graph.getNode(nodeName).getAttribute("contaminated");
	}

	public static void contaminate(String nodeName) {
		Main.graph.getNode(nodeName).setAttribute("contaminated", true);
		
		Main.graph.getNode(nodeName).setAttribute("ui.style", "fill-color: rgb(255,0,0);size: 30px;");
		
		// ADD THE LOGIC FOR ADJACENT NODES INFECTION.
		// get all the related edges to our node and contaminate.
		
		Main.graph.getEdgeSet().forEach(e -> {
			
			if(e.getNode0().getAttribute("ui.label").equals(nodeName)) {
				
				e.getNode1().setAttribute("contaminated", true);
				e.getNode1().setAttribute("ui.style", "fill-color: rgb(255,0,0);size: 30px;");
				
				
			} else if(e.getNode1().getAttribute("ui.label").equals(nodeName)){
				
				e.getNode0().setAttribute("contaminated", true);
				e.getNode0().setAttribute("ui.style", "fill-color: rgb(255,0,0);size: 30px;");
				
			}
		});
		
	}

}
