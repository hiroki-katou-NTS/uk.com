package nts.uk.ctx.pr.core.ws.wageprovision.statementitem.breakdownItem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.AddBreakdownItemSetCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.BreakdownItemSetCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.RemoveBreakdownItemSetCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.UpdateBreakdownItemSetCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.BreakdownItemFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.BreakdownItemSetDto;

/**
 * 
 * @author thanh.tq
 *
 */
@Path("ctx/pr/core/breakdownItem")
@Produces("application/json")
public class BreakdownItemWebService extends WebService {

	@Inject
	private BreakdownItemFinder breakdownItemFinder;

	@Inject
	private AddBreakdownItemSetCommandHandler addBreakdownItemSetCommandHandler;

	@Inject
	private UpdateBreakdownItemSetCommandHandler updateBreakdownItemSetCommandHandler;

	@Inject
	private RemoveBreakdownItemSetCommandHandler removeBreakdownItemSetCommandHandler;

	@POST
	@Path("getAllBreakdownItemSetById/{categoryAtr}/{itemNameCd}")
	public List<BreakdownItemSetDto> getAllBreakdownItemSetById(@PathParam("categoryAtr") int categoryAtr, @PathParam("itemNameCd") String itemNameCd) {
		return this.breakdownItemFinder.getBreakdownItemStById(categoryAtr, itemNameCd);
	}

	@POST
	@Path("addBreakdownItemSet")
	public void addAcceptCodeConvert(BreakdownItemSetCommand command) {
		this.addBreakdownItemSetCommandHandler.handle(command);
	}

	@POST
	@Path("updateBreakdownItemSet")
	public void updateBreakdownItemSet(BreakdownItemSetCommand command) {
		this.updateBreakdownItemSetCommandHandler.handle(command);
	}

	@POST
	@Path("removeBreakdownItemSet")
	public void removeBreakdownItemSet(BreakdownItemSetCommand command) {
		this.removeBreakdownItemSetCommandHandler.handle(command);
	}

}
