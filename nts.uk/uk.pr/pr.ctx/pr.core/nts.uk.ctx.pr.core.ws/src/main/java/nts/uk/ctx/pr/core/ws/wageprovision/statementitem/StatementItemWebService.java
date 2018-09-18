package nts.uk.ctx.pr.core.ws.wageprovision.statementitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemDataDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemDataFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemNameFinder;

@Path("ctx/pr/core/wageprovision/statementitem")
@Produces("application/json")
public class StatementItemWebService extends WebService {
	@Inject
	private StatementItemDataFinder statementItemDataFinder;
	
	@Inject
	private StatementItemFinder statementItemFinder;
	
	@Inject
	private StatementItemNameFinder statementItemNameFinder;

	@POST
	@Path("getStatementItemData/{categoryAtr}/{itemNameCd}/{salaryItemId}")
	public StatementItemDataDto getStatementItemData(@PathParam("categoryAtr") int categoryAtr,
			@PathParam("itemNameCd") String itemNameCd, @PathParam("salaryItemId") String salaryItemId) {
		return this.statementItemDataFinder.getStatementItemData(categoryAtr, itemNameCd, salaryItemId);
	}
	
	@POST
	@Path("getAllStatementItemData/{categoryAtr}/{itemNameCd}/{salaryItemId}")
	public List<StatementItemDataDto> getAllStatementItemData(@PathParam("categoryAtr") Integer categoryAtr) {
		return this.statementItemDataFinder.getAllStatementItemData(categoryAtr);
	}
	
	@POST
	@Path("getAllStatementItem")
	public List<StatementItemDto> getAllStatementItem() {
		return this.statementItemFinder.findAllStatementItem();
	}
	
	@POST
	@Path("getAllStatementItem/{categoryAtr}")
	public List<StatementItemDto> getByCategory(@PathParam("categoryAtr") int categoryAtr) {
		return this.statementItemFinder.findByCategory(categoryAtr);
	}
	
	@POST
	@Path("getAllStatementItem/{categoryAtr}/{itemNameCd}")
	public List<StatementItemDto> getByCategoryAndCode(@PathParam("categoryAtr") int categoryAtr,@PathParam("itemNameCd") String itemNameCd) {
		return this.statementItemFinder.findByCategoryAndCode(categoryAtr, itemNameCd);
	}
	
	@POST
	@Path("getStatementItemName/{salaryItemId}")
	public StatementItemNameDto getByCategoryAndCode(@PathParam("salaryItemId") String salaryItemId) {
		return this.statementItemNameFinder.findStatementItemName(salaryItemId);
	}
}
