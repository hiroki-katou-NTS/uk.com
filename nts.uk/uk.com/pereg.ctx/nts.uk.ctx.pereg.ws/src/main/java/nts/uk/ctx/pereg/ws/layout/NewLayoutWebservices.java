package nts.uk.ctx.pereg.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.layout.NewLayoutCommand;
import nts.uk.ctx.pereg.app.command.layout.NewLayoutCommandHandler;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutFinder;

@Path("ctx/pereg/person/newlayout")
@Produces("application/json")
public class NewLayoutWebservices extends WebService {

	@Inject
	private NewLayoutFinder nLayoutFinder;

	@Inject
	private NewLayoutCommandHandler commandHandler;

	@POST
	@Path("get")
	public NewLayoutDto getNewLayout() {
		return nLayoutFinder.getLayout().orElse(null);
	}

	@POST
	@Path("save")
	public List<String> saveNewLayout(NewLayoutCommand command) {
		return commandHandler.handle(command);
	}

	@POST
	@Path("check-new-layout")
	public Boolean checkNewLayout() {
		return nLayoutFinder.checkLayoutExist();
	}
}
