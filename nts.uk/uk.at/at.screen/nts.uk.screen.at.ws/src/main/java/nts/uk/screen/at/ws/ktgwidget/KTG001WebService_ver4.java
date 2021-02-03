package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.ktg.ktg001.ApproveStatusSettingCommandHandler;
import nts.uk.screen.at.app.command.ktg.ktg001.ApproveStatusSettingCommand;
import nts.uk.screen.at.app.ktgwidget.KTG001QueryProcessor_ver04;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedDataWidgetStartDto;

@Path("screen/at/ktg001")
@Produces("application/json")
public class KTG001WebService_ver4 {

	@Inject
	private KTG001QueryProcessor_ver04 queryProcessor;
	
	@Inject
	private ApproveStatusSettingCommandHandler commandHandler;

	@POST
	@Path("display")
	public ApprovedDataWidgetStartDto checkDisplay(KTG001Param param) {
		return this.queryProcessor.getApprovedDataWidgetStart(param.getYm(), param.getClosureId());
	}
	
	@POST
	@Path("setting")
	public void updateSetting(ApproveStatusSettingCommand param) {
		this.commandHandler.updateSetting(param);
	}
	
}
