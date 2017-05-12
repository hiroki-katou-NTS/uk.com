package nts.uk.ctx.at.schedule.ws.budget.premium;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.premium.DeletePremiumBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.DeletePremiumBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.InsertPremiumBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.InsertPremiumBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePremiumBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePremiumBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PremiumBudgetDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PremiumBudgetFinder;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/budget/premium")
@Produces("application/json")
public class PremiumBudgetWebService extends WebService{
	
	@Inject
	private InsertPremiumBudgetCommandHandler insertPremiumBudgetCommandHandler;
	
	@Inject
	private PremiumBudgetFinder premiumBudgetFinder;
	
	@Inject
	private UpdatePremiumBudgetCommandHandler updatePremiumBudgetCommandHandler;
	
	@Inject
	private DeletePremiumBudgetCommandHandler deletePremiumBudgetCommandHandler;
	
	@POST
	@Path("insert")
	public void insert(InsertPremiumBudgetCommand command){
		this.insertPremiumBudgetCommandHandler.handle(command);
	}
	
	@POST
	@Path("findPremiumBudget")
	public PremiumBudgetDto findByCompanyID(PremiumBudgetDto command){
		return this.premiumBudgetFinder.findPremiumBudget(command.getHistoryID());
	}
	
	@POST
	@Path("findBycompanyID")
	public List<PremiumBudgetDto> find(){
		return this.premiumBudgetFinder.findByCompanyID();
	}
	
	@POST
	@Path("update")
	public void update(UpdatePremiumBudgetCommand command){
		this.updatePremiumBudgetCommandHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeletePremiumBudgetCommand command){
		this.deletePremiumBudgetCommandHandler.handle(command);
	}

}
