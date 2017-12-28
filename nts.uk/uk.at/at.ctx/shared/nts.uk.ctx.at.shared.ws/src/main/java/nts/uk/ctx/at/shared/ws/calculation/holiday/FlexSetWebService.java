package nts.uk.ctx.at.shared.ws.calculation.holiday;
/**
 * @author phongtq
 */
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.flex.AddFlexSetCommand;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.flex.AddFlexSetCommandHandler;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.flex.FlexSetDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.flex.FlexSetFinder;

@Path("shared/caculation/holiday/flex")
@Produces("application/json")
public class FlexSetWebService extends WebService{
	@Inject
	private FlexSetFinder finder;
	@Inject
	private AddFlexSetCommandHandler handler;
	
	@Path("findByCid")
	@POST
	public List<FlexSetDto> findByCid() {
		return finder.findAllFlexSet();
	}
	
	@Path("add")
	@POST
	public void add(AddFlexSetCommand command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(AddFlexSetCommand command) {
		this.handler.handle(command);
	}
}
