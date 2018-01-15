package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.PSBonusPaySettingCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.PSBonusPaySettingCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.PSBonusPaySettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.PSBonusPaySettingFinder;

@Produces("application/json")
@Path("at/share/psBonusPaySetting")
public class PSBonusPaySettingWebService extends WebService {
	@Inject
	private PSBonusPaySettingFinder finder;

	@Inject
	private PSBonusPaySettingCommandHandler commandHandler;

	@POST
	@Path("getList")
	public List<PSBonusPaySettingDto> getListPSBonusPaySettingSetting(List<String> lstEmployeeId) {
		return this.finder.getListSetting(lstEmployeeId);
	}

	@POST
	@Path("getSetting/{employeeId}")
	public PSBonusPaySettingDto getPersonalBonusPaySetting(@PathParam("employeeId") String employeeId) {
		return this.finder.getPersonalBonusPaySetting(employeeId);
	}

	@POST
	@Path("saveSetting")
	public void addPSBonusPaySettingSetting(PSBonusPaySettingCommand command) {
		this.commandHandler.handle(command);
	}
}
