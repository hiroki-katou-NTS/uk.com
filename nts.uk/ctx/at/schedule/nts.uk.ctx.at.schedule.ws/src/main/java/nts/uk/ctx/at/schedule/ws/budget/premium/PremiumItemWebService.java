package nts.uk.ctx.at.schedule.ws.budget.premium;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePremiumItemCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePremiumItemCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PremiumItemDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PremiumItemFinder;

@Path("at/budget/premium/premiumItem")
@Produces("application/json")
public class PremiumItemWebService extends WebService {
	
	@Inject
	private PremiumItemFinder premiumItemFinder;
	
	@Inject
	private UpdatePremiumItemCommandHandler updatePremiumItemCommandHandler;
	
	@POST
	@Path("findBycompanyID")
	public List<PremiumItemDto> find(){
		return this.premiumItemFinder.findByCompanyID();
	}
	
	@POST
	@Path("update")
	public void update(List<UpdatePremiumItemCommand> command){
		this.updatePremiumItemCommandHandler.handle(command);
	}
}
