package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulationRepository;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetireDateTermParam;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirePlanParam;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
@Stateless
public class MandatoryRetirementRegulationServiceImpl implements MandatoryRetirementRegulationService {

	@Inject
	private MandatoryRetirementRegulationRepository repo;
	
	@Override
	public Optional<MandatoryRetirementRegulation> getMandatoryRetirementRegulation(String companyId, String historyId) {
		return repo.getMandatoryRetirementRegulation(historyId, companyId);
	}

	@Override
	public void addMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm,
			DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, boolean planCourseApplyFlg,
			List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm,
			PlanCourseApplyTerm planCourseApplyTerm) {
		MandatoryRetirementRegulation domain = MandatoryRetirementRegulation.createFromJavaType(
				companyId, 
				historyId, 
				reachedAgeTerm, 
				publicTerm, 
				retireDateTerm, 
				planCourseApplyFlg, 
				mandatoryRetireTerm, 
				referEvaluationTerm, 
				planCourseApplyTerm);
		repo.add(domain);
	}

	@Override
	public void updateMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm,
			DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, boolean planCourseApplyEnable,
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

	@Override
	public List<RetirementDateDto> getRetireDateBySidList(List<RetirePlanParam> retirePlan, RetirementAge retirementAge,
			RetireDateTermParam retireDateTerm, Optional<GeneralDate> endDate, List<GeneralDate> closingDate,
			List<GeneralDate> attendanceDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getEvaluationInfoBySidList(List<String> retiredEmployeeId,
			List<ReferEvaluationItem> evaluationReferInfo) {
		// TODO Auto-generated method stub
		
	}

}
