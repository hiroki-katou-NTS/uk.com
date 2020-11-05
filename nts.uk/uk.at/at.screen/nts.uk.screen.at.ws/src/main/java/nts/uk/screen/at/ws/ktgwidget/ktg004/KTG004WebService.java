package nts.uk.screen.at.ws.ktgwidget.ktg004;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.ktg.ktg001.ApproveStatusSettingCommand;
import nts.uk.screen.at.app.command.ktg.ktg004.WorkStatusSettingCommandHandler;
import nts.uk.screen.at.app.ktgwidget.ktg004.KTG004Finder;
import nts.uk.screen.at.app.ktgwidget.ktg004.WorkStatusSettingDto;

@Path("screen/at/ktg004")
@Produces("application/json")
public class KTG004WebService {

	@Inject
	private KTG004Finder ktg004Finder;
	
	@Inject 
	private WorkStatusSettingCommandHandler commandHandler;
	
	@POST
	@Path("getSetting")
	public WorkStatusSettingDto checkDisplay() {
		return ktg004Finder.getApprovedDataWidgetStart();
	}
	
	@POST
	@Path("setting")
	public void updateSetting(WorkStatusSettingDto param) {
		this.commandHandler.updateSetting(param);
	}
	
}
