package nts.uk.ctx.at.record.ws.divergence.time.history;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.divergence.time.history.DivergenceReferenceTimeUsageUnitCommand;
import nts.uk.ctx.at.record.app.command.divergence.time.history.DivergenceReferenceTimeUsageUnitSaveCommandHandler;

@Path("at/record/divergence/time/divergenceRefTimeUsageUnit")
@Produces("application/json")
public class DivergenceReferenceTimeUsageUnitWs extends WebService{
	
	@Inject
	private DivergenceReferenceTimeUsageUnitCommand command;
	
	@Inject 
	private DivergenceReferenceTimeUsageUnitSaveCommandHandler saveHandler;
	
	@POST
	@Path("save")
	public void save(DivergenceReferenceTimeUsageUnitCommand command){
		this.saveHandler.handle(command);
	}
}
