package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.ParamAddManRetireRegDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.EnableRetirePlanCourse;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MandatoryRetirementRegulationCommand {

	@Inject
	private MandatoryRetirementRegulationService service;
	
	@Inject
	private EmploymentRegulationHistoryInterface hisService;
	
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
	
	public void add(ParamAddManRetireRegDto param) {
		String cId = AppContexts.user().companyId();
		Optional<String> hisId = hisService.getHistoryIdByDate(cId, param.getBaseDate().addDays(-1));
		if(hisId.isPresent()) {
			Optional<MandatoryRetirementRegulation> domain = service.getMandatoryRetirementRegulation(cId, hisId.get());
			if(domain.isPresent()) {
				service.addMandatoryRetirementRegulation(cId, 
						param.getHistoryId(), 
						domain.get().getReachedAgeTerm().value, 
						domain.get().getPublicTerm(), 
						domain.get().getRetireDateTerm(), 
						domain.get().isPlanCourseApplyFlg(), 
						domain.get().getMandatoryRetireTerm(), 
						domain.get().getReferEvaluationTerm(), 
						domain.get().getPlanCourseApplyTerm());
			}else {
				this.addDefault(param);
			}
		}else {
			this.addDefault(param);
		}
	}
	
	private void addDefault(ParamAddManRetireRegDto param) {
		String cId = AppContexts.user().companyId();
		List<ReferEvaluationItem> df = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			df.add(ReferEvaluationItem.createFromJavaType(i, false, 1, "B"));
		}
		List<MandatoryRetireTerm> mandatoryRetireTerm = new ArrayList<>();
		List<EnableRetirePlanCourse>  enableRetirePlanCourse = new ArrayList<>();
		if(param.getMaxRetirePlanCource().isPresent()) {
			enableRetirePlanCourse.add(new EnableRetirePlanCourse(param.getMaxRetirePlanCource().get().getRetirePlanCourseId()));
		}
		mandatoryRetireTerm.add(new MandatoryRetireTerm(param.getMinOrderCmmMastIt().getCommonMasterItemId(), true, enableRetirePlanCourse));
		service.addMandatoryRetirementRegulation(cId, 
			param.getHistoryId(), 
			0,
			DateCaculationTerm.createFromJavaType(1, null, null), 
			RetireDateTerm.createFromJavaType(0, null), 
			false, 
			mandatoryRetireTerm, 
			df, 
			PlanCourseApplyTerm.createFromJavaType(50, 59, 12, 31));
	}
	
	
}
