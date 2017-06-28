package nts.uk.ctx.at.record.ws.standardtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.standardtime.operationsetting.AddAgreementOperationSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.operationsetting.AddAgreementOperationSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.operationsetting.UpdateAgreementOperationSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.operationsetting.UpdateAgreementOperationSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementOperationSettingFinder;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementOperationSettingDto;

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
