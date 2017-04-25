package nts.uk.ctx.at.schedule.ws.budget.external;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.external.DeleteExternalBudgetCmd;
import nts.uk.ctx.at.schedule.app.command.budget.external.DeleteExternalBudgetCmdHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.InsertExternalBudgetCmd;
import nts.uk.ctx.at.schedule.app.command.budget.external.InsertExternalBudgetCmdHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.UpdateExternalBudgetCmd;
import nts.uk.ctx.at.schedule.app.command.budget.external.UpdateExternalBudgetCmdHandler;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetFinder;

@Path("pr/proto/layout")
@Produces("application/json")
public class ExternalBudgetWebService extends WebService {
	@Inject
	private ExternalBudgetFinder find;

	@Inject
	private InsertExternalBudgetCmdHandler insert;

	@Inject
	private UpdateExternalBudgetCmdHandler update;

	@Inject
	private DeleteExternalBudgetCmdHandler delete;

	@POST
	@Path("findallexternalbudget")
	public List<ExternalBudgetDto> getAllExternalBudget() {
		return this.find.findAll();
	}
	
	@POST
	@Path("insertexternalbudget")
	public void insertbudget(InsertExternalBudgetCmd command) {
		this.insert.handle(command);
	}

	@POST
	@Path("updateexternalbudget")
	public void updateData(UpdateExternalBudgetCmd command) {
		this.update.handle(command);
	}

	@POST
	@Path("deleteexternalbudget")
	public void deleteData(DeleteExternalBudgetCmd command) {
		this.delete.handle(command);
	}

}
