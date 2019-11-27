package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface HumanResourceEvaluationRepository {
	List<PersonnelAssessment> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds, GeneralDate startDate);

	List<PersonnelAssessment> getPersonnelAssessmentByEmployeeId(String employeeId);
	
}
