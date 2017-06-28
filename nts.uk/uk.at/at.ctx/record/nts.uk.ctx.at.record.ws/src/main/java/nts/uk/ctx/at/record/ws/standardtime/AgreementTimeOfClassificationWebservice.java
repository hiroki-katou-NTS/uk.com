package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementTimeOfClassificationCommand;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementTimeOfClassificationCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.RemoveAgreementTimeOfClassificationCommand;
import nts.uk.ctx.at.record.app.command.standardtime.RemoveAgreementTimeOfClassificationCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementTimeOfClassificationCommand;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementTimeOfClassificationCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfClassificationDetail;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfClassificationFinder;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfClassificationListDto;

@Path("at/record/agreementTimeOfClassification")
@Produces("application/json")
public class AgreementTimeOfClassificationWebservice {
	
	@Inject
	private AddAgreementTimeOfClassificationCommandHandler addAgreementTimeOfClassificationCommandHandler;
	
	@Inject
	private RemoveAgreementTimeOfClassificationCommandHandler removeAgreementTimeOfClassificationCommandHandler;
	
	@Inject
	private UpdateAgreementTimeOfClassificationCommandHandler updateAgreementTimeOfClassificationCommandHandler;
	
	@Inject
	private AgreementTimeOfClassificationFinder agreementTimeOfClassificationFinder;
	
	@POST
	@Path("addAgreementTimeOfClassification")
	public JavaTypeResult<List<String>> addAgreementTimeOfClassification(AddAgreementTimeOfClassificationCommand command) {
		return this.addAgreementTimeOfClassificationCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementTimeOfClassification")
	public JavaTypeResult<List<String>> updateAgreementTimeOfClassification(UpdateAgreementTimeOfClassificationCommand command) {
		return this.updateAgreementTimeOfClassificationCommandHandler.handle(command);
	}
	
	@POST
	@Path("removeAgreementTimeOfClassification")
	public void removeAgreementTimeOfClassification(RemoveAgreementTimeOfClassificationCommand command) {
		this.removeAgreementTimeOfClassificationCommandHandler.handle(command);
	}
	
	@POST
	@Path("getAgreementTimeOfClassification/{laborSystemAtr}")
	public AgreementTimeOfClassificationListDto getList(@PathParam("laborSystemAtr") int laborSystemAtr) {
		return this.agreementTimeOfClassificationFinder.findAll(laborSystemAtr);
	}
	
	
	@POST
	@Path("getAgreementTimeOfClassification/{laborSystemAtr}/{classificationCode}")
	public AgreementTimeOfClassificationDetail getDetail(@PathParam("laborSystemAtr") int laborSystemAtr, @PathParam("employmentCategoryCode") String classificationCode) {
		return this.agreementTimeOfClassificationFinder.findDetail(laborSystemAtr, classificationCode);
	}

}
