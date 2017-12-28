package nts.uk.ctx.at.shared.ws.ot.zerotime;
/**
 * @author phongtq
 */
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.ot.zerotime.AddZeroTimeCommand;
import nts.uk.ctx.at.shared.app.command.ot.zerotime.AddZeroTimeCommandHandler;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.ZeroTimeDto;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.ZeroTimeFinder;
@Path("shared/caculation/holiday/time")
@Produces("application/json")
public class ZeroTimeWebService extends WebService{

	@Inject
	private ZeroTimeFinder finder;
	@Inject
	private AddZeroTimeCommandHandler handler;
	
	@Path("findByCid")
	@POST
	public List<ZeroTimeDto> findByCid() {
		return finder.findAllCalc();
	}
	
	@Path("add")
	@POST
	public void add(AddZeroTimeCommand command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(AddZeroTimeCommand command) {
		this.handler.handle(command);
	}
}
