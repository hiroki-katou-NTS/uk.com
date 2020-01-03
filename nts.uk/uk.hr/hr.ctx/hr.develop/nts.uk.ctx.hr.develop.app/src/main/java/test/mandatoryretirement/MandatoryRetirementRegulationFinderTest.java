package test.mandatoryretirement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EvaluationInfoDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirePlanParam;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementPlannedPersonDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.ReachedAgeTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;

@Stateless
public class MandatoryRetirementRegulationFinderTest {

	@Inject
	private MandatoryRetirementRegulationService repo;
	
	public Optional<MandatoryRetirementRegulation> getMandatoryRetirementRegulation(String companyId, String historyId){
		return repo.getMandatoryRetirementRegulation(companyId, historyId);
	}
	
	public void addMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm, DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, 
			boolean planCourseApplyFlg, List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm, PlanCourseApplyTerm planCourseApplyTerm) {
		repo.addMandatoryRetirementRegulation(companyId, historyId, reachedAgeTerm, publicTerm, retireDateTerm, planCourseApplyFlg, mandatoryRetireTerm, referEvaluationTerm, planCourseApplyTerm);
	}
	
	public void updateMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm, DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, 
			boolean planCourseApplyFlg, List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm, PlanCourseApplyTerm planCourseApplyTerm) {
		repo.updateMandatoryRetirementRegulation(companyId, historyId, reachedAgeTerm, publicTerm, retireDateTerm, planCourseApplyFlg, mandatoryRetireTerm, referEvaluationTerm, planCourseApplyTerm);
	}

	public List<ReferEvaluationItem> getReferEvaluationItemByDate(String companyId, GeneralDate baseDate){
		return repo.getReferEvaluationItemByDate(companyId, baseDate);
	}
	
	public List<RetirementCourseInformationDto> getAppliedRetireCourseByDate(String companyId, GeneralDate baseDate){
		return repo.getAppliedRetireCourseByDate(companyId, baseDate);
	}
	
	public List<RetirementPlannedPersonDto> getMandatoryRetirementListByPeriodDepartmentEmployment(String companyId, GeneralDate startDate, GeneralDate endDate, Optional<RetirementAge> retirementAge, List<String> departmentId, List<String> employmentCode){
		return repo.getMandatoryRetirementListByPeriodDepartmentEmployment(companyId, startDate, endDate, retirementAge, departmentId, employmentCode);
	}
	
	public List<RetirementDateDto> getRetireDateBySidList(List<RetirePlanParam> retirePlan, ReachedAgeTerm reachedAgeTerm, RetireDateTerm retireDateTerm, Optional<GeneralDate> endDate, List<EmploymentDateDto> closingDate, List<EmploymentDateDto> attendanceDate){
		return repo.getRetireDateBySidList(retirePlan, reachedAgeTerm, retireDateTerm, endDate, closingDate, attendanceDate);
	}
	
	public EvaluationInfoDto getEvaluationInfoBySidList(List<String> retiredEmployeeId, List<ReferEvaluationItem> evaluationReferInfo) {
		return repo.getEvaluationInfoBySidList(retiredEmployeeId, evaluationReferInfo);
	}
}
