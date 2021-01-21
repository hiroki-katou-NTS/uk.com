package nts.uk.ctx.at.schedule.ws.shift.management.shifttable;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 * 
 * @author quytb
 *
 */

import nts.uk.ctx.at.schedule.app.command.shift.management.shifttable.RegisterShiftTableCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.management.shifttable.ShiftTableSaveCommand;
@Path("ctx/schedule/shiftmanagement/shifttable")
@Produces(MediaType.APPLICATION_JSON)
public class ShiftTableWS {
	@Inject
	private RegisterShiftTableCommandHandler commandHandler;
	
	@POST
	@Path("register")
	public void registerShiftTable(ShiftTableSaveCommand command) {
		this.commandHandler.handle(command);
	}
}
