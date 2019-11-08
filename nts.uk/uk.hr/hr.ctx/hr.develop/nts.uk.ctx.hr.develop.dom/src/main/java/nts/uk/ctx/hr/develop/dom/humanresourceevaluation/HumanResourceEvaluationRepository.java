package nts.uk.ctx.hr.develop.dom.humanresourceevaluation;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface HumanResourceEvaluationRepository {
	List<PersonnelAssessment> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds, GeneralDate startDate);
}
