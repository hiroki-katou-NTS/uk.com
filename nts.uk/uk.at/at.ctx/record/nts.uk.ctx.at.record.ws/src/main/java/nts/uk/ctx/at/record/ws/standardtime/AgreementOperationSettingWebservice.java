package nts.uk.ctx.at.record.ws.standardtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementOperationSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementOperationSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementOperationSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementOperationSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementOperationSettingDto;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementOperationSettingFinder;

@Path("at/record/agreementOperationSetting")
@Produces("application/json")
public class AgreementOperationSettingWebservice {
	
	@Inject
	private AddAgreementOperationSettingCommandHandler addAgreementOperationSettingCommandHandler;
	
	@Inject
	private UpdateAgreementOperationSettingCommandHandler updateAgreementOperationSettingCommandHandler;
	
	@Inject
	private AgreementOperationSettingFinder agreementOperationSettingFinder;
	
	@POST
	@Path("addAgreementOperationSetting")
	public void addAgreementOperationSetting(AddAgreementOperationSettingCommand command) {
		this.addAgreementOperationSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementOperationSetting")
	public void updateAgreementOperationSetting(UpdateAgreementOperationSettingCommand command) {
		this.updateAgreementOperationSettingCommandHandler.handle(command);
	}

	@POST
	@Path("getAgreementOperationSetting")
	public AgreementOperationSettingDto getList() {
		return this.agreementOperationSettingFinder.find();
	}
}
