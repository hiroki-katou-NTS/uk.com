package nts.uk.ctx.pr.core.ws.wageprovision.statementitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.AddStatementItemDataCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.RemoveStatementItemDataCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.StatementItemDataCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.UpdateStatementItemDataCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.UpdateStatementItemNameCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.*;

@Path("ctx/pr/core/wageprovision/statementitem")
@Produces("application/json")
public class StatementItemWebService extends WebService {
	@Inject
	private StatementItemDataFinder statementItemDataFinder;

	@Inject
	private StatementItemFinder statementItemFinder;

	@Inject
	private StatementItemNameFinder statementItemNameFinder;

	@Inject
	private AddStatementItemDataCommandHandler addStatementItemDataCommandHandler;

	@Inject
	private UpdateStatementItemDataCommandHandler updateStatementItemDataCommandHandler;

	@Inject
	private RemoveStatementItemDataCommandHandler removeStatementItemDataCommandHandler;
	
	@Inject
	private UpdateStatementItemNameCommandHandler updateStatementItemNameCommandHandler;

	@POST
	@Path("getStatementItemData/{categoryAtr}/{itemNameCd}/{salaryItemId}")
	public StatementItemDataDto getStatementItemData(@PathParam("categoryAtr") int categoryAtr,
			@PathParam("itemNameCd") String itemNameCd, @PathParam("salaryItemId") String salaryItemId) {
		return this.statementItemDataFinder.getStatementItemData(categoryAtr, itemNameCd, salaryItemId);
	}

	@POST
	@Path("getAllStatementItemData/{categoryAtr}/{isIncludeDeprecated}")
	public List<StatementItemCustomDto> getAllStatementItemData(@PathParam("categoryAtr") Integer categoryAtr,
																@PathParam("isIncludeDeprecated") boolean isIncludeDeprecated) {
		return this.statementItemDataFinder.getListStatementItemData(categoryAtr, isIncludeDeprecated);
	}

	@POST
	@Path("getAllStatementItem")
	public List<StatementItemDto> getAllStatementItem() {
		return this.statementItemFinder.findAllStatementItem();
	}
	
	@POST
	@Path("getStatementItemAndStatementItemName/{categoryAtr}")
	public List<StatementItemAndStatementItemNameDto> findStatementItemNameByListSalaryItemId(@PathParam("categoryAtr") int categoryAtr) {
		return this.statementItemFinder.findStatementItemNameByListSalaryItemId(categoryAtr);
	}

	@POST
	@Path("getAllStatementItem/{categoryAtr}")
	public List<StatementItemDto> getByCategory(@PathParam("categoryAtr") int categoryAtr) {
		return this.statementItemFinder.findByCategory(categoryAtr);
	}

	@POST
	@Path("getAllStatementItem/{categoryAtr}/{itemNameCd}")
	public List<StatementItemDto> getByCategoryAndCode(@PathParam("categoryAtr") int categoryAtr,
			@PathParam("itemNameCd") String itemNameCd) {
		return this.statementItemFinder.findByCategoryAndCode(categoryAtr, itemNameCd);
	}

	@POST
	@Path("getStatementItemName/{salaryItemId}")
	public StatementItemNameDto getByCategoryAndCode(@PathParam("salaryItemId") String salaryItemId) {
		return this.statementItemNameFinder.findStatementItemName(salaryItemId);
	}

	@POST
	@Path("registerStatementItemData")
	public void registerStatementItemData(StatementItemDataCommand command) {
		if (command.isCheckCreate()) {
			this.addStatementItemDataCommandHandler.handle(command);
		} else {
			this.updateStatementItemDataCommandHandler.handle(command);
		}
	}

	@POST
	@Path("removeStatementItemData")
	public void removeStatementItemData(StatementItemDataCommand command) {
		this.removeStatementItemDataCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateStatementItemName")
	public void UpdateStatementItemName(List<StatementItemAndStatementItemNameDto> command) {
		this.updateStatementItemNameCommandHandler.handle(command);
	}

}
