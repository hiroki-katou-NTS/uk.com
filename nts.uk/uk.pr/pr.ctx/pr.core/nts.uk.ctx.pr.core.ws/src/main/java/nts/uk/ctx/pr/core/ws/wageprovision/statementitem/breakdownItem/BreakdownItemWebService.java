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
class AverageWageCalculationSetService extends WebService {
	

}
