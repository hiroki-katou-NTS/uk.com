package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementTimeOfEmploymentCommand;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementTimeOfEmploymentCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.RemoveAgreementTimeOfEmploymentCommand;
import nts.uk.ctx.at.record.app.command.standardtime.RemoveAgreementTimeOfEmploymentCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementTimeOfEmploymentCommand;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementTimeOfEmploymentCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfEmploymentDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfEmploymentFinder;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfEmploymentListDto;

@Path("at/record/agreementTimeOfEmployment")
@Produces("application/json")
public class AgreementTimeOfEmploymentWebservice extends WebService {
	
	@Inject
	private AgreementTimeOfEmploymentFinder agreementTimeOfEmploymentFinder;
	
	@Inject
	private AddAgreementTimeOfEmploymentCommandHandler addAgreementTimeOfEmploymentCommandHandler;
	
	@Inject
	private RemoveAgreementTimeOfEmploymentCommandHandler removeAgreementTimeOfEmploymentCommandHandler;
	
	@Inject
	private UpdateAgreementTimeOfEmploymentCommandHandler updateAgreementTimeOfEmploymentCommandHandler;
	
	@POST
	@Path("getAgreementTimeOfEmployment/{laborSystemAtr}")
	public AgreementTimeOfEmploymentListDto getList(@PathParam("laborSystemAtr") int laborSystemAtr) {
		return this.agreementTimeOfEmploymentFinder.findAll(laborSystemAtr);
	}
	
	@POST
	@Path("getAgreementTimeOfEmployment/{laborSystemAtr}/{employmentCategoryCode}")
	public AgreementTimeOfEmploymentDetailDto getDetail(@PathParam("laborSystemAtr") int laborSystemAtr, @PathParam("employmentCategoryCode") String employmentCategoryCode) {
		return this.agreementTimeOfEmploymentFinder.findDetail(laborSystemAtr, employmentCategoryCode);
	}
	
	@POST
	@Path("addAgreementTimeOfEmployment")
	public JavaTypeResult<List<String>> addAgreementTimeOfEmployment(AddAgreementTimeOfEmploymentCommand command) {
		return this.addAgreementTimeOfEmploymentCommandHandler.handle(command);
	}
	
	@POST
	@Path("removeAgreementTimeOfEmployment")
	public void removeAgreementTimeOfEmployment(RemoveAgreementTimeOfEmploymentCommand command) {
		this.removeAgreementTimeOfEmploymentCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementTimeOfEmployment")
	public JavaTypeResult<List<String>> updateAgreementTimeOfEmployment(UpdateAgreementTimeOfEmploymentCommand command) {
		return this.updateAgreementTimeOfEmploymentCommandHandler.handle(command);
	}
}
