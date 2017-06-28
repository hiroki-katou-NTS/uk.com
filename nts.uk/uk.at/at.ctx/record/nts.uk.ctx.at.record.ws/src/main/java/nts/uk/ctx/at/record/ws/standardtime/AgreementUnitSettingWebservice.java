package nts.uk.ctx.at.record.ws.standardtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementUnitSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementUnitSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementUnitSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementUnitSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementUnitSettingDto;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementUnitSettingFinder;

@Path("at/record/agreementUnitSetting")
@Produces("application/json")
public class AgreementUnitSettingWebservice {
	
	@Inject
	private AddAgreementUnitSettingCommandHandler addAgreementUnitSettingCommandHandler;
	
	@Inject
	private UpdateAgreementUnitSettingCommandHandler updateAgreementUnitSettingCommandHandler;
	
	@Inject
	private AgreementUnitSettingFinder agreementUnitSettingFinder;

	@POST
	@Path("addAgreementUnitSetting")
	public void addAgreementUnitSetting(AddAgreementUnitSettingCommand command) {
		this.addAgreementUnitSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementUnitSetting")
	public void updateAgreementUnitSetting(UpdateAgreementUnitSettingCommand command) {
		this.updateAgreementUnitSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("getAgreementTimeOfWorkPlace")
	public AgreementUnitSettingDto getList() {
		return this.agreementUnitSettingFinder.find();
	}
}
