package nts.uk.screen.at.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kaf022.command.Kaf022AddCommand;
import nts.uk.screen.at.app.kaf022.command.UpdateKaf022AddCommandHandler;
@Path("screen/at/kaf022")
@Produces("application/json")
public class Kaf022WebService extends WebService{
	@Inject
	private UpdateKaf022AddCommandHandler update;
	
	@POST
	@Path("update")
	public void update(Kaf022AddCommand cm){
		this.update.handle(cm);
	}
}
