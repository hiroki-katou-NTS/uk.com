package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.command;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateMandatoryRetirementRegulation {

	@Inject
	private MandatoryRetirementRegulationService service;
	
	public void update(MandatoryRetirementRegulationDto mandatoryRetirementRegulationDto) {
		service.updateMandatoryRetirementRegulation(
				AppContexts.user().companyId(), 
				mandatoryRetirementRegulationDto.getHistoryId(), 
				mandatoryRetirementRegulationDto.getReachedAgeTerm(), 
				mandatoryRetirementRegulationDto.getPublicTerm().toDomain(), 
				mandatoryRetirementRegulationDto.getRetireDateTerm().toDomain(), 
				mandatoryRetirementRegulationDto.getPlanCourseApplyFlg(), 
				mandatoryRetirementRegulationDto.getMandatoryRetireTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				mandatoryRetirementRegulationDto.getReferEvaluationTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				mandatoryRetirementRegulationDto.getPlanCourseApplyTerm().toDomain());
	}
	
}
