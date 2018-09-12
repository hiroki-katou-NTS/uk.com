package nts.uk.ctx.pr.core.ws.breakdownItem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.BreakdownItemFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.BreakdownItemSetDto;

/**
 * 
 * @author thanh.tq
 *
 */
@Path("ctx/pr/core/breakdownItem")
@Produces("application/json")
public class BreakdownItem extends WebService {

	@Inject
	private BreakdownItemFinder breakdownItemFinder;

	@POST
	@Path("getAllBreakdownItemSetById")
	public List<BreakdownItemSetDto> getAllBreakdownItemSetById(String salaryItemId) {
		return this.breakdownItemFinder.getBreakdownItemStBySalaryId(salaryItemId);
	}
}
