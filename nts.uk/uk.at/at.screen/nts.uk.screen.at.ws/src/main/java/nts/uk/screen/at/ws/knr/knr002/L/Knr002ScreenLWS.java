package nts.uk.screen.at.ws.knr.knr002.L;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.record.app.command.knr.knr002.L.RegisterSwitchingDateCommandHandler;
import nts.uk.ctx.at.record.app.command.knr.knr002.L.RegisterSwitchingDatesCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.L.RegisterSwitchingDatesCommandHandler;
import nts.uk.screen.at.app.query.knr.knr002.L.GetProductionSwitchDateDto;
import nts.uk.screen.at.app.query.knr.knr002.L.GetProductionSwitchDateInput;
import nts.uk.screen.at.app.query.knr.knr002.L.GetProductionSwitchDateSC;

/**
 * 
 * @author dungbn
 *
 */
@Path("screen/knr002/l")
@Produces(MediaType.APPLICATION_JSON)
public class Knr002ScreenLWS {

	@Inject
	private RegisterSwitchingDatesCommandHandler registerSwitchingDatesCommandHandler;
	
	@Inject
	private RegisterSwitchingDateCommandHandler registerSwitchingDateCommandHandler;
	
	@Inject
	private GetProductionSwitchDateSC getProductionSwitchDateSC;
	
	@POST
	@Path("registerAllSwitchDates")
	public void registerAllSwitchDates(RegisterSwitchingDatesCommand command) {
		this.registerSwitchingDatesCommandHandler.handle(command);
	}
	
	@POST
	@Path("registerSwitchDate")
	public void registerSwitchDates(RegisterSwitchingDatesCommand command) {
		this.registerSwitchingDateCommandHandler.handle(command);
	}
	
	@POST
	@Path("getSwitchDate")
	public List<GetProductionSwitchDateDto> getProductionSwitchDate(GetProductionSwitchDateInput input) {
		return getProductionSwitchDateSC.getProductionSwitchDate(input);
	}
}
