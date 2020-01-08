package nts.uk.ctx.hr.develop.ws.announcement.mandatoryretirement;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;

@Path("mandatoryRetirementRegulation")
@Produces(MediaType.APPLICATION_JSON)
public class MandatoryRetirementRegulationWS {

	@Inject
	private MandatoryRetirementRegulationService finder;
	
	@POST
	@Path("/update")
	public void update(MandatoryRetirementRegulationDto param){
		finder.updateMandatoryRetirementRegulation(
				param.getCompanyId(), 
				param.getHistoryId(), 
				param.getReachedAgeTerm(), 
				param.getPublicTerm().toDomain(), 
				param.getRetireDateTerm().toDomain(), 
				param.getPlanCourseApplyFlg(), 
				param.getMandatoryRetireTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				param.getReferEvaluationTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				param.getPlanCourseApplyTerm().toDomain());
	}
}
