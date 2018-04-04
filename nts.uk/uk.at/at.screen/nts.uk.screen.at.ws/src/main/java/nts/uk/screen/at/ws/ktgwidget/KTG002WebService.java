package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.ktgwidget.KTG002QueryProcessor;

@Path("screen/at/ktg002")
@Produces("application/json")
public class KTG002WebService {
	
	@Inject
	private KTG002QueryProcessor queryProcessor; 
	
	@POST
	@Path("checkApproved")
	public boolean checkDataApprove(){
		return queryProcessor.checkDataApprove();
		//return true;
	}
}
