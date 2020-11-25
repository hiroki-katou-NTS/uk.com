package nts.uk.ctx.at.shared.ws.holidaymanagement;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.flex.AddFlexWorkSettingCommand;
import nts.uk.ctx.at.shared.app.command.holidaymanagement.SettingPublicHolidayAddCommand;
import nts.uk.ctx.at.shared.app.command.holidaymanagement.SettingPublicHolidayAddCommandHandler;

@Path("at/share/holidaymanagement")
@Produces("application/json")
public class SettingPublicHolidayWs extends WebService {
	
	@Inject
	SettingPublicHolidayAddCommandHandler settingPublicHolidayHandler;

	
	@Path("add")
	@POST
	public void registerFlexWorkSet(SettingPublicHolidayAddCommand command) {
		settingPublicHolidayHandler.handle(command);
	}
}
