package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public List<PSBonusPaySettingDto> getListPSBonusPaySettingSetting(List<String> lstEmployeeId) {
		return this.psBonusPaySettingFinder.getListSetting(lstEmployeeId);
	}
	@POST
	@Path("getPersonalBonusPaySetting/{employeeId}")
	public PSBonusPaySettingDto getPersonalBonusPaySetting(@PathParam("employeeId") String employeeId){
		return this.psBonusPaySettingFinder.getPersonalBonusPaySetting(employeeId);
	}

	@POST
	@Path("addPSBonusPaySettingSetting")
	public void addPSBonusPaySettingSetting(PSBonusPaySettingAddCommand psBonusPaySettingAddCommand) {
	this.psBonusPaySettingAddCommandHandler.handle(psBonusPaySettingAddCommand);
	}

	@POST
	@Path("updatePSBonusPaySettingSetting")
	public void updatePSBonusPaySettingSetting(PSBonusPaySettingUpdateCommand psBonusPaySettingUpdateCommand) {
	this.psBonusPaySettingUpdateCommandHandler.handle(psBonusPaySettingUpdateCommand);
	}

	@POST
	@Path("removePSBonusPaySettingSetting")
	public void removePSBonusPaySettingSetting(PSBonusPaySettingDeleteCommand psBonusPaySettingDeleteCommand) {
	this.psBonusPaySettingDeleteCommandHandler.handle(psBonusPaySettingDeleteCommand);
	}

}
