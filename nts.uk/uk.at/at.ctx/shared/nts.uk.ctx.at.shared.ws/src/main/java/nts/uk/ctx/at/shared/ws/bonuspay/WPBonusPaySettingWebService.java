package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.WPBonusPaySettingCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.WPBonusPaySettingCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.WPBonusPaySettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.WPBonusPaySettingFinder;

@Path("at/share/wpBonusPaySetting")
@Produces("application/json")
public class WPBonusPaySettingWebService extends WebService {
	@Inject
	private WPBonusPaySettingFinder finder;

	@Inject
	private WPBonusPaySettingCommandHandler commandHandler;

	@POST
	@Path("getListWPBonusPaySetting")
	public List<WPBonusPaySettingDto> getListWPBonusPaySettingSetting(List<String> lstWorkplace) {
		return this.finder.getListSetting(lstWorkplace);
	}

	@POST
	@Path("getWPBPSetting/{WorkplaceId}")
	public WPBonusPaySettingDto getWPBPSetting(@PathParam("WorkplaceId") String WorkplaceId) {
		return this.finder.getWPBPSetting(WorkplaceId);
	}

	@POST
	@Path("saveWpBonsPaySetting")
	public void addWPBonusPaySettingSetting(WPBonusPaySettingCommand command) {
		this.commandHandler.handle(command);
	}
}
