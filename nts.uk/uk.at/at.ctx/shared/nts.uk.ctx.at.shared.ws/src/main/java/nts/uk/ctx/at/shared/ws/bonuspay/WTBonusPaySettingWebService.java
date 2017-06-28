package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.WTBonusPaySettingAddCommand;
import nts.uk.ctx.at.shared.app.command.WTBonusPaySettingAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.WTBonusPaySettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.WTBonusPaySettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.WTBonusPaySettingUpdateCommand;
import nts.uk.ctx.at.shared.app.command.WTBonusPaySettingUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.WTBonusPaySettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.WTBonusPaySettingFinder;

@Path("at/share/wtBonusPaySetting")
@Produces("application/json")
public class WTBonusPaySettingWebService extends WebService {
	@Inject
	private WTBonusPaySettingFinder wtBonusPaySettingFinder;
	@Inject
	private WTBonusPaySettingAddCommandHandler wtBonusPaySettingAddCommandHandler;
	@Inject
	private WTBonusPaySettingDeleteCommandHandler wtBonusPaySettingDeleteCommandHandler;
	@Inject
	private WTBonusPaySettingUpdateCommandHandler wtBonusPaySettingUpdateCommandHandler;

	@POST
	@Path("getListWTBonusPaySettingSetting")
	List<WTBonusPaySettingDto> getListWTBonusPaySettingSetting() {
		return this.wtBonusPaySettingFinder.getListSetting();
	}
	@POST
	@Path("getWTBPSetting/{workingTimesheetCode}")
	WTBonusPaySettingDto getWTBPSetting(@PathParam("workingTimesheetCode") String workingTimesheetCode){
		return this.wtBonusPaySettingFinder.getWTBPSetting(workingTimesheetCode);
	}

	@POST
	@Path("addWTBonusPaySettingSetting")
	void addWTBonusPaySettingSetting(WTBonusPaySettingAddCommand wtBonusPaySettingAddCommand) {
		this.wtBonusPaySettingAddCommandHandler.handle(wtBonusPaySettingAddCommand);
	}

	@POST
	@Path("updateWTBonusPaySettingSetting")
	void updateWTBonusPaySettingSetting(WTBonusPaySettingUpdateCommand wtBonusPaySettingUpdateCommand) {
		this.wtBonusPaySettingUpdateCommandHandler.handle(wtBonusPaySettingUpdateCommand);
	}

	@POST
	@Path("removeWTBonusPaySettingSetting")
	void removeWTBonusPaySettingSetting(WTBonusPaySettingDeleteCommand wtBonusPaySettingDeleteCommand) {
		this.wtBonusPaySettingDeleteCommandHandler.handle(wtBonusPaySettingDeleteCommand);
	}
}
