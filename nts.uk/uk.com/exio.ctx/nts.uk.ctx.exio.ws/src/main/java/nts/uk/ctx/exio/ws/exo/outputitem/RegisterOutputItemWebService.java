package nts.uk.ctx.exio.ws.exo.outputitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.outputitemsetting.AddOutputItemCommand;
import nts.uk.ctx.exio.app.command.exo.outputitemsetting.AddOutputItemService;

@Path("exio/exo/registeroutputitem")
@Produces("application/json")
public class RegisterOutputItemWebService {
	@Inject
	private AddOutputItemService addOutputItemService;

	@POST
	@Path("add")
	public void addOutputItem(List<AddOutputItemCommand> comand) {
		this.addOutputItemService.handle(comand);
	}

}
