package nts.uk.ctx.exio.ws.exi.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exi.item.Cmf001DCommand;
import nts.uk.ctx.exio.app.command.exi.item.RegisterStdAcceptItemCommandHandler;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemDto;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemFinder;

@Path("exio/exi/item")
@Produces("application/json")
public class StdAcceptItemWebService {
	@Inject
	private StdAcceptItemFinder stdAcceptItem;
	
	@Inject
	private RegisterStdAcceptItemCommandHandler regStdAcceptItemHandler;

	@POST
	@Path("getAllStdAcceptItem/{systemType}/{conditionSetCd}")
	public List<StdAcceptItemDto> getAllStdAcceptItem(@PathParam("systemType") int systemType,
			@PathParam("conditionSetCd") String conditionSetCd) {
		return this.stdAcceptItem.getStdAcceptItems(systemType, conditionSetCd);
	}
	
	@POST
	@Path("register")
	public void getAllStdAcceptItem(Cmf001DCommand command) {
		this.regStdAcceptItemHandler.handle(command);
	}
}
