package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.PSBonusPaySettingAddCommand;
import nts.uk.ctx.at.shared.app.command.PSBonusPaySettingAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.PSBonusPaySettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.PSBonusPaySettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.PSBonusPaySettingUpdateCommand;
import nts.uk.ctx.at.shared.app.command.PSBonusPaySettingUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.PSBonusPaySettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.PSBonusPaySettingFinder;

@Path("at/share/psBonusPaySetting")
@Produces("application/json")
public class PSBonusPaySettingWebService extends WebService {
	@Inject
	private PSBonusPaySettingFinder psBonusPaySettingFinder;
	@Inject
	private PSBonusPaySettingAddCommandHandler psBonusPaySettingAddCommandHandler;
	@Inject
	private PSBonusPaySettingDeleteCommandHandler psBonusPaySettingDeleteCommandHandler;
	@Inject
	private PSBonusPaySettingUpdateCommandHandler psBonusPaySettingUpdateCommandHandler;

	@POST
	@Path("getListPSBonusPaySettingSetting")
	List<PSBonusPaySettingDto> getListPSBonusPaySettingSetting(List<String> lstEmployeeId) {
		return this.psBonusPaySettingFinder.getListSetting(lstEmployeeId);
	}

	@POST
	@Path("addListPSBonusPaySettingSetting")
	void addListPSBonusPaySettingSetting(List<PSBonusPaySettingAddCommand> lstSetting) {
	this.psBonusPaySettingAddCommandHandler.handle(lstSetting);
	}

	@POST
	@Path("updateListPSBonusPaySettingSetting")
	void updateListPSBonusPaySettingSetting(List<PSBonusPaySettingUpdateCommand> lstSetting) {
	this.psBonusPaySettingUpdateCommandHandler.handle(lstSetting);
	}

	@POST
	@Path("removeListPSBonusPaySettingSetting")
	void removeListPSBonusPaySettingSetting(List<PSBonusPaySettingDeleteCommand> lstSetting) {
	this.psBonusPaySettingDeleteCommandHandler.handle(lstSetting);
	}

}
