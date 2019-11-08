package nts.uk.ctx.hr.develop.infra.repository.humanresourceevaluation;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.humanresourceevaluation.HumanResourceEvaluationRepository;
import nts.uk.ctx.hr.develop.dom.humanresourceevaluation.PersonnelAssessment;

public class JpaHREvaluationRepository implements HumanResourceEvaluationRepository {

	private static final String SEL_PERSONNEL_ASSESMENT_BY_EMPLOYEES = "SELECT "; 
	
	@Override
	public List<PersonnelAssessment> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds,
			GeneralDate startDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
