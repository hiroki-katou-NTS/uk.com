package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.standardtime.yearsetting.AddAgreementYearSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.yearsetting.AddAgreementYearSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.yearsetting.RemoveAgreementYearSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.yearsetting.RemoveAgreementYearSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.yearsetting.UpdateAgreementYearSettingCommand;
import nts.uk.ctx.at.record.app.command.standardtime.yearsetting.UpdateAgreementYearSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementYearSettingFinder;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementYearSettingDto;

@Path("at/record/agreementYearSetting")
@Produces("application/json")
public class AgreementYearSettingService {

	@Inject
	private AgreementYearSettingFinder agreementYearSettingFinder;

	@Inject
	private UpdateAgreementYearSettingCommandHandler updateAgreementYearSettingCommandHandler;

	@Inject
	private RemoveAgreementYearSettingCommandHandler removeAgreementYearSettingCommandHandler;

	@Inject
	private AddAgreementYearSettingCommandHandler addAgreementYearSettingCommandHandler;

	@POST
	@Path("addAgreementYearSetting")
	public void addAgreementTimeOfWorkPlace(AddAgreementYearSettingCommand command) {
		this.addAgreementYearSettingCommandHandler.handle(command);
	}

	@POST
	@Path("updateAgreementYearSetting")
	public void updateAgreementYearSetting(UpdateAgreementYearSettingCommand command) {
		this.updateAgreementYearSettingCommandHandler.handle(command);
	}

	@POST
	@Path("removeAgreementYearSetting")
	public void removeAgreementYearSetting(RemoveAgreementYearSettingCommand command) {
		this.removeAgreementYearSettingCommandHandler.handle(command);
	}

	@POST
	@Path("getAgreementYearSetting/{employeeId}")
	public List<AgreementYearSettingDto> getDetail(@PathParam("employeeId") String employeeId) {
		return this.agreementYearSettingFinder.find(employeeId);
	}
}
