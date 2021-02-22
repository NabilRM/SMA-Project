package ma.ensias.util;
import jade.core.ProfileImpl;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.ContainerController;
import jade.core.Runtime;
public class Plateforme{
	
	private ContainerController mainContainer, aContainer;
	private boolean isMain;
	private String name;
	public Plateforme(boolean isMain, String name) {
		if(isMain) {
	        Runtime rt = Runtime.instance();
	        Properties p = new ExtendedProperties() ;        //fixer quelques propriétés
	        p.setProperty("gui","true") ;
	        ProfileImpl profile = new ProfileImpl(p);
	        mainContainer = rt.createMainContainer(profile); //créer le main-container
		}else {
	        Runtime rt = Runtime.instance() ;
	        ProfileImpl profile = new ProfileImpl(false);

	        //Le main container associé est déjà démarré sur localhost
	        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
	        profile.setParameter(ProfileImpl.CONTAINER_NAME, name);
	        aContainer = rt.createAgentContainer(profile);
		}

	}
	
	public ContainerController getMainContainer(){return mainContainer;}
	
	public ContainerController getAContainer(){return aContainer;}
	
}