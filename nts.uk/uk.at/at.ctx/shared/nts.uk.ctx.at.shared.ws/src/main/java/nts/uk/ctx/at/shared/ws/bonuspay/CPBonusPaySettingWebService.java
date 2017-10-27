package nts.uk.ctx.at.shared.ws.bonuspay;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.CBPSettingCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.CBPSettingCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.CompanyBPSettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.CompanyBPSettingFinder;

@Produces("application/json")
@Path("at/share/cpBonusPaySetting")
public class CPBonusPaySettingWebService extends WebService {
	@Inject
	private CompanyBPSettingFinder finder;

	@Inject
	private CBPSettingCommandHandler commandHandler;

	@POST
	@Path("getSetting")
	public CompanyBPSettingDto getCBPSettingSetting() {
		return this.finder.getSetting();
	}

	@POST
	@Path("saveSetting")
	public void addCBPSettingSetting(CBPSettingCommand command) {
		this.commandHandler.handle(command);
	}
}
