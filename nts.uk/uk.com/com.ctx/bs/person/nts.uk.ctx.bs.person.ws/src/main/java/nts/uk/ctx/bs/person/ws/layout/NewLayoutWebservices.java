package nts.uk.ctx.bs.person.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import command.layout.NewLayoutCommand;
import command.layout.NewLayoutCommandHandler;
import find.layout.NewLayoutDto;
import find.layout.NewLayoutFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/newlayout")
@Produces("application/json")
public class NewLayoutWebservices extends WebService {

	@Inject
	private NewLayoutFinder nLayoutFinder;

	@Inject
	private NewLayoutCommandHandler commandHandler;

	@POST
	@Path("get")
	public NewLayoutDto getNewLayout() {
		return nLayoutFinder.getLayout();
	}

	@POST
	@Path("save")
	public void addMaintenanceLayout(NewLayoutCommand command) {
		this.commandHandler.handle(command);
	}
}
