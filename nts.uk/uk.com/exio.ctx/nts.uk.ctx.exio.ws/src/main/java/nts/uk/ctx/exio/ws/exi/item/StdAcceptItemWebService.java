package nts.uk.ctx.exio.ws.exi.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.item.Cmf001DCommand;
import nts.uk.ctx.exio.app.command.exi.item.RegisterReturnStdAcceptItemCommandHandler;
import nts.uk.ctx.exio.app.command.exi.item.RegisterStdAcceptItemCommandHandler;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemDto;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemFinder;

@Path("exio/exi/item")
@Produces("application/json")
public class StdAcceptItemWebService extends WebService {
	@Inject
	private StdAcceptItemFinder stdAcceptItem;

	@Inject
	private RegisterStdAcceptItemCommandHandler regStdAcceptItemHandler;

	@Inject
	private RegisterReturnStdAcceptItemCommandHandler regReturnStdAcceptItemHandler;

	@POST
	@Path("getAllStdAcceptItem/{conditionSetCd}")
	public List<StdAcceptItemDto> getAllStdAcceptItem(@PathParam("conditionSetCd") String conditionSetCd) {
		return this.stdAcceptItem.getStdAcceptItems(conditionSetCd);
	}

	@POST
	@Path("register")
	public void register(Cmf001DCommand command) {
		this.regStdAcceptItemHandler.handle(command);
	}

	@POST
	@Path("registerReturn")
	public void registerReturn(Cmf001DCommand command) {
		this.regReturnStdAcceptItemHandler.handle(command);
	}

}
