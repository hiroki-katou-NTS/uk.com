package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

//import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.at.record.app.command.standardtime.classification.AddAgreementTimeOfClassificationCommand;
import nts.uk.ctx.at.record.app.command.standardtime.classification.AddAgreementTimeOfClassificationCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.classification.RemoveAgreementTimeOfClassificationCommand;
import nts.uk.ctx.at.record.app.command.standardtime.classification.RemoveAgreementTimeOfClassificationCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.classification.UpdateAgreementTimeOfClassificationCommand;
import nts.uk.ctx.at.record.app.command.standardtime.classification.UpdateAgreementTimeOfClassificationCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfClassificationFinder;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfClassificationDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfClassificationListDto;

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
	public List<String> addAgreementTimeOfClassification(AddAgreementTimeOfClassificationCommand command) {
		return this.addAgreementTimeOfClassificationCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementTimeOfClassification")
	public List<String> updateAgreementTimeOfClassification(UpdateAgreementTimeOfClassificationCommand command) {
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
	public AgreementTimeOfClassificationDetailDto getDetail(@PathParam("laborSystemAtr") int laborSystemAtr, @PathParam("classificationCode") String classificationCode) {
		return this.agreementTimeOfClassificationFinder.findDetail(laborSystemAtr, classificationCode);
	}

}
