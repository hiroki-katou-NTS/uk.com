package nts.uk.ctx.at.shared.ws.bonuspay;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.CBPSettingAddCommand;
import nts.uk.ctx.at.shared.app.command.CBPSettingAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.CBPSettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.CBPSettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.CBPSettingUpdateCommand;
import nts.uk.ctx.at.shared.app.command.CBPSettingUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.CompanyBPSettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.CompanyBPSettingFinder;

@Path("at/share/cpBonusPaySetting")
@Produces("application/json")
public class CPBonusPaySettingWebService extends WebService {
	@Inject
	private CompanyBPSettingFinder companyBPSettingFinder;
	@Inject
	private CBPSettingAddCommandHandler cbPSettingAddCommandHandler;
	@Inject
	private CBPSettingDeleteCommandHandler cbPSettingDeleteCommandHandler;
	@Inject
	private CBPSettingUpdateCommandHandler cbPSettingUpdateCommandHandler;

	@POST
	@Path("getCBPSettingSetting")
	CompanyBPSettingDto getCBPSettingSetting() {
		return this.companyBPSettingFinder.getSetting();
	}

	@POST
	@Path("addCBPSettingSetting")
	void addCBPSettingSetting(CBPSettingAddCommand setting) {
		this.cbPSettingAddCommandHandler.handle(setting);
	}

	@POST
	@Path("updateCBPSettingSetting")
	void updateCBPSettingSetting(CBPSettingUpdateCommand setting) {
		this.cbPSettingUpdateCommandHandler.handle(setting);
	}

	@POST
	@Path("removeCBPSettingSetting")
	void removeCBPSettingSetting(CBPSettingDeleteCommand setting) {
		this.cbPSettingDeleteCommandHandler.handle(setting);
	}
}
