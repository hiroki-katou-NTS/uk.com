package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.ReferEvaluationItem;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.shared.dom.publicTerm.PublicTerm;

@Stateless
public class MandatoryRetirementRegulationServiceImpl implements MandatoryRetirementRegulationService  {

	@Override
	public MandatoryRetirementRegulation getMandatoryRetirementRegulation(String companyId, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm,
			PublicTerm publicTerm, RetireDateTerm retireDateTerm, boolean planCourseApplyEnable,
			List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm,
			PlanCourseApplyTerm planCourseApplyTerm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm,
			PublicTerm publicTerm, RetireDateTerm retireDateTerm, boolean planCourseApplyEnable,
			List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm,
			PlanCourseApplyTerm planCourseApplyTerm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReferEvaluationItem> getReferEvaluationItemByDate(String companyId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetirementCourseInformationDto> getAppliedRetireCourseByDate(String companyId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getMandatoryRetirementListByPeriodDepartmentEmployment(String companyId, GeneralDate baseDate,
			GeneralDate endDate, Optional<RetirementAge> retirementAge, List<String> departmentId,
			List<String> employmentCode) {
		// TODO Auto-generated method stub
		
	}

}
