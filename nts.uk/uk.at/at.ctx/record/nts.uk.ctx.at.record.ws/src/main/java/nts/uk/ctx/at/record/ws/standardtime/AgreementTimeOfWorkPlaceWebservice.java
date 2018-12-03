package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

//import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.at.record.app.command.standardtime.workplace.AddAgreementTimeOfWorkPlaceCommand;
import nts.uk.ctx.at.record.app.command.standardtime.workplace.AddAgreementTimeOfWorkPlaceCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.workplace.RemoveAgreementTimeOfWorkPlaceCommand;
import nts.uk.ctx.at.record.app.command.standardtime.workplace.RemoveAgreementTimeOfWorkPlaceCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.workplace.UpdateAgreementTimeOfWorkPlaceCommand;
import nts.uk.ctx.at.record.app.command.standardtime.workplace.UpdateAgreementTimeOfWorkPlaceCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfWorkPlaceFinder;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfWorkPlaceDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfWorkPlaceListDto;

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
	public List<String> addAgreementTimeOfWorkPlace(AddAgreementTimeOfWorkPlaceCommand command) {
		return this.addAgreementTimeOfWorkPlaceCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementTimeOfWorkplace")
	public List<String> updateAgreementTimeOfWorkPlace(UpdateAgreementTimeOfWorkPlaceCommand command) {
		return this.updateAgreementTimeOfWorkPlaceCommandHandler.handle(command);
	}
	
	@POST
	@Path("removeAgreementTimeOfWorkplace")
	public void removeAgreementTimeOfWorkplace(RemoveAgreementTimeOfWorkPlaceCommand  command) {
		this.removeAgreementTimeOfWorkPlaceCommandHandler.handle(command);
	}

}
