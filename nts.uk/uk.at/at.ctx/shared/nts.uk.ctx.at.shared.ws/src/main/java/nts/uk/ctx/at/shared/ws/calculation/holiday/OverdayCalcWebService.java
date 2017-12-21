package nts.uk.ctx.at.shared.ws.calculation.holiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.AddFlexSetCommand;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.AddFlexSetCommandHandler;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.FlexSetDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.FlexSetFinder;

@Path("shared/caculation/holiday/time")
@Produces("application/json")
public class OverdayCalcWebService extends WebService{

	@Inject
	private FlexSetFinder finder;
	@Inject
	private AddFlexSetCommandHandler addFlexSetCommandHandler;
	
	@Path("findByCid")
	@POST
	public List<FlexSetDto> findByCid() {
		return finder.findAllFlexSet();
	}
	
	@Path("add")
	@POST
	public JavaTypeResult<List<String>> add(AddFlexSetCommand command) {
		return new JavaTypeResult<List<String>>(this.addFlexSetCommandHandler.handle(command));
	}
	
	@Path("update")
	@POST
	public JavaTypeResult<List<String>> update(AddFlexSetCommand command) {
		return new JavaTypeResult<List<String>>(this.addFlexSetCommandHandler.handle(command));
	}
}
