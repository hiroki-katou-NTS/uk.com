package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ktgwidget.KTG026QueryProcessor;

@Path("screen/at/ktg26")
@Produces("application/json")
public class KTG026WebService extends WebService{
	
	@Inject
	KTG026QueryProcessor processor;
	
	@POST
	@Path("startScreen")
	public void startScreenKtg026() {
		processor.startScreenKtg026();
	}

}
