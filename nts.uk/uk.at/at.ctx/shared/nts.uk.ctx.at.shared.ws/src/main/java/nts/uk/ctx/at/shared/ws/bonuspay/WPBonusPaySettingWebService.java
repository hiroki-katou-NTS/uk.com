package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.WPBonusPaySettingAddCommand;
import nts.uk.ctx.at.shared.app.command.WPBonusPaySettingAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.WPBonusPaySettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.WPBonusPaySettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.WPBonusPaySettingUpdateCommand;
import nts.uk.ctx.at.shared.app.command.WPBonusPaySettingUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.WPBonusPaySettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.WPBonusPaySettingFinder;

@Path("at/share/wpBonusPaySetting")
@Produces("application/json")
public class WPBonusPaySettingWebService extends WebService {
	@Inject
	private WPBonusPaySettingFinder wpBonusPaySettingFinder;
	@Inject
	private WPBonusPaySettingAddCommandHandler wpBonusPaySettingAddCommandHandler;
	@Inject
	private WPBonusPaySettingDeleteCommandHandler wpBonusPaySettingDeleteCommandHandler;
	@Inject
	private WPBonusPaySettingUpdateCommandHandler wpBonusPaySettingUpdateCommandHandler;
	@POST
	@Path("getListWPBonusPaySettingSetting")
	List<WPBonusPaySettingDto> getListWPBonusPaySettingSetting(List<String> lstWorkplace) {
		return this.wpBonusPaySettingFinder.getListSetting(lstWorkplace);
	}
	@POST
	@Path("addListWPBonusPaySettingSetting")
	void addListWPBonusPaySettingSetting(List<WPBonusPaySettingAddCommand> lstSetting) {
		this.wpBonusPaySettingAddCommandHandler.handle(lstSetting);
	}
	@POST
	@Path("updateListWPBonusPaySettingSetting")
	void updateListWPBonusPaySettingSetting(List<WPBonusPaySettingUpdateCommand> lstSetting) {
		this.wpBonusPaySettingUpdateCommandHandler.handle(lstSetting);
	}
	@POST
	@Path("removeListWPBonusPaySettingSetting")
	void removeListWPBonusPaySettingSetting(List<WPBonusPaySettingDeleteCommand> lstSetting) {
		this.wpBonusPaySettingDeleteCommandHandler.handle(lstSetting);
	}
}
