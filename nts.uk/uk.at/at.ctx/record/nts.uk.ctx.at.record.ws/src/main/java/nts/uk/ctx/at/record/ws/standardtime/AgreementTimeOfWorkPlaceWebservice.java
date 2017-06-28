package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementTimeOfWorkPlaceCommand;
import nts.uk.ctx.at.record.app.command.standardtime.AddAgreementTimeOfWorkPlaceCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.RemoveAgreementTimeOfWorkPlaceCommand;
import nts.uk.ctx.at.record.app.command.standardtime.RemoveAgreementTimeOfWorkPlaceCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementTimeOfWorkPlaceCommand;
import nts.uk.ctx.at.record.app.command.standardtime.UpdateAgreementTimeOfWorkPlaceCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfWorkPlaceDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfWorkPlaceFinder;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfWorkPlaceListDto;

@Path("at/record/agreementTimeOfWorkPlace")
@Produces("application/json")
public class AgreementTimeOfWorkPlaceWebservice {
	
	@Inject
	private AgreementTimeOfWorkPlaceFinder agreementTimeOfWorkPlaceDetailFinder;
	
	@Inject
	private AddAgreementTimeOfWorkPlaceCommandHandler addAgreementTimeOfWorkPlaceCommandHandler;
	
	@Inject
	private UpdateAgreementTimeOfWorkPlaceCommandHandler updateAgreementTimeOfWorkPlaceCommandHandler;
	
	@Inject
	private RemoveAgreementTimeOfWorkPlaceCommandHandler removeAgreementTimeOfWorkPlaceCommandHandler;
	
	@POST
	@Path("getAgreementTimeOfWorkPlace/{laborSystemAtr}")
	public AgreementTimeOfWorkPlaceListDto getList(@PathParam("laborSystemAtr") int laborSystemAtr) {
		return this.agreementTimeOfWorkPlaceDetailFinder.findAll(laborSystemAtr);
	}
	
	@POST
	@Path("getAgreementTimeOfWorkPlace/{laborSystemAtr}/{workplaceId}")
	public AgreementTimeOfWorkPlaceDetailDto getDetail(@PathParam("laborSystemAtr") int laborSystemAtr,@PathParam("workplaceId") String workplaceId) {
		return this.agreementTimeOfWorkPlaceDetailFinder.findDetail(laborSystemAtr, workplaceId);
	}
	
	@POST
	@Path("addAgreementTimeOfWorkPlace")
	public JavaTypeResult<List<String>> addAgreementTimeOfWorkPlace(AddAgreementTimeOfWorkPlaceCommand command) {
		return this.addAgreementTimeOfWorkPlaceCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementTimeOfWorkplace")
	public JavaTypeResult<List<String>> updateAgreementTimeOfWorkPlace(UpdateAgreementTimeOfWorkPlaceCommand command) {
		return this.updateAgreementTimeOfWorkPlaceCommandHandler.handle(command);
	}
	
	@POST
	@Path("removeAgreementTimeOfWorkplace")
	public void removeAgreementTimeOfWorkplace(RemoveAgreementTimeOfWorkPlaceCommand  command) {
		this.removeAgreementTimeOfWorkPlaceCommandHandler.handle(command);
	}

}
