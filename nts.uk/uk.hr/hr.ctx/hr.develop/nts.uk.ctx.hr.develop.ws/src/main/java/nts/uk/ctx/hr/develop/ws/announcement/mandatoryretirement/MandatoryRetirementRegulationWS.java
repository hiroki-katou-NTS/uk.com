package nts.uk.ctx.hr.develop.ws.announcement.mandatoryretirement;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.command.UpdateMandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;

@Path("mandatoryRetirementRegulation")
@Produces(MediaType.APPLICATION_JSON)
public class MandatoryRetirementRegulationWS {

	@Inject
	private UpdateMandatoryRetirementRegulation command;
	
	@POST
	@Path("/update")
	public void update(MandatoryRetirementRegulationDto param){
		command.update(param);
	}
}
