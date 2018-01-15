package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.WTBonusPaySettingCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.WTBonusPaySettingCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.WTBonusPaySettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.WTBonusPaySettingFinder;

@Path("at/share/wtBonusPaySetting")
@Produces("application/json")
public class WTBonusPaySettingWebService extends WebService {
	@Inject
	private WTBonusPaySettingFinder finder;
	
	@Inject
	private WTBonusPaySettingCommandHandler commandHandler;
	@POST
	@Path("getListWTBonusPaySettingSetting")
	public List<WTBonusPaySettingDto> getListWTBonusPaySettingSetting() {
		return this.finder.getListSetting();
	}
	@POST
	@Path("getWTBPSetting/{workingTimesheetCode}")
	public WTBonusPaySettingDto getWTBPSetting(@PathParam("workingTimesheetCode") String workingTimesheetCode){
		return this.finder.getWTBPSetting(workingTimesheetCode);
	}

	@POST
	@Path("saveSetting")
	public void addWTBonusPaySettingSetting(WTBonusPaySettingCommand command) {
		this.commandHandler.handle(command);
	}
}
