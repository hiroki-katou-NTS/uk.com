package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.standardtime.monthsetting.AddAgreementMonthSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.monthsetting.AddAgreementMonthSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.monthsetting.RemoveAgreementMonthSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.monthsetting.RemoveAgreementMonthSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.monthsetting.UpdateAgreementMonthSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.monthsetting.UpdateAgreementMonthSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementMonthSettingFinder;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementMonthSettingDto;

@Path("at/record/agreementMonthSetting")
@Produces("application/json")
public class AgreementMonthSettingWebservice {
	
	@Inject
	private AddAgreementMonthSettingCommandHandler addAgreementMonthSettingCommandHandler;
	
	@Inject
	private RemoveAgreementMonthSettingCommandHandler removeAgreementMonthSettingCommandHandler;
	
	@Inject
	private UpdateAgreementMonthSettingCommandHandler updateAgreementMonthSettingCommandHandler;
	
	@Inject
	private AgreementMonthSettingFinder agreementMonthSettingFinder;
	
	@POST
	@Path("addAgreementMonthSetting")
	public List<String> addAgreementMonthSetting(AddAgreementMonthSettingCommand command) {
		return this.addAgreementMonthSettingCommandHandler.handle(command);
	}

	@POST
	@Path("removeAgreementMonthSetting")
	public void removeAgreementMonthSetting(RemoveAgreementMonthSettingCommand command) {
		this.removeAgreementMonthSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementMonthSetting")
	public List<String> updateAgreementMonthSetting(UpdateAgreementMonthSettingCommand command) {
		return this.updateAgreementMonthSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("getAgreementMonthSetting/{employeeId}")
	public List<AgreementMonthSettingDto> getDetail(@PathParam("employeeId") String employeeId) {
		return this.agreementMonthSettingFinder.find(employeeId);
	}
}
