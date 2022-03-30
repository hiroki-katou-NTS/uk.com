package nts.uk.ctx.at.shared.ws.scherec.dailyattdcal.declare;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.dailyattdcal.declare.RegisterFilingSettingsCommand;
import nts.uk.ctx.at.shared.app.command.scherec.dailyattdcal.declare.RegisterFilingSettingsCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/ctx/scherec/dailyattdcal/declare")
@Produces("application/json")
public class FilingSettingsWebService extends WebService {

	@Inject
	private RegisterFilingSettingsCommandHandler registerJudgmentCriteriaSettingScreenCommandHandler;

	@POST
	@Path("register-filing-settings")
	public void RegisterFilingSettingsCommandHandler(RegisterFilingSettingsCommand command) {
		registerJudgmentCriteriaSettingScreenCommandHandler.handle(command);
	}

}