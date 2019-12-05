package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.ReferEvaluationItem;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;

public interface MandatoryRetirementRegulationService {

	//定年退職の就業規則の取得
	MandatoryRetirementRegulation getMandatoryRetirementRegulation(String companyId, String historyId);
	
	//定年退職の就業規則の追加
	void addMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm, DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, 
			boolean planCourseApplyEnable, List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm, PlanCourseApplyTerm planCourseApplyTerm);
	
	//定年退職の就業規則の更新
	void updateMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm, DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, 
			boolean planCourseApplyEnable, List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm, PlanCourseApplyTerm planCourseApplyTerm);

	//基準日で評価参考情報の取得
	List<ReferEvaluationItem> getReferEvaluationItemByDate(String companyId, GeneralDate baseDate);
	
	//基準日で選択されている定年退職コースの取得
	List<RetirementCourseInformationDto> getAppliedRetireCourseByDate(String companyId, GeneralDate baseDate);
	
	/**
	 * 指定期間、部門、雇用から定年退職者情報の取得
	 * @param companyId
	 * @param baseDate
	 * @param endDate
	 * @param retirementAge
	 * @param departmentId
	 * @param employmentCode
	 */
	void getMandatoryRetirementListByPeriodDepartmentEmployment(String companyId, GeneralDate baseDate, GeneralDate endDate, Optional<RetirementAge> retirementAge, List<String> departmentId, List<String> employmentCode);
}
