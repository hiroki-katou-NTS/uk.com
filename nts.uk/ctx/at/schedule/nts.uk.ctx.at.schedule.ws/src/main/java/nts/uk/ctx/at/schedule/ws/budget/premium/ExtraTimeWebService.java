package nts.uk.ctx.at.schedule.ws.budget.premium;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdateExtraTimeCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdateExtraTimeCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.premium.ExtraTimeDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.ExtraTimeFinder;

@Path("at/budget/premium/extraTime")
@Produces("application/json")
public class ExtraTimeWebService extends WebService {
	
	@Inject
	private ExtraTimeFinder extraTimeFinder;
	
	@Inject
	private UpdateExtraTimeCommandHandler updateExtraTimeCommandHandler;
	
	@POST
	@Path("findBycompanyID")
	public List<ExtraTimeDto> find(){
		return this.extraTimeFinder.findByCompanyID();
	}
	
	@POST
	@Path("update")
	public void update(UpdateExtraTimeCommand command){
		this.updateExtraTimeCommandHandler.handle(command);
	}
}
