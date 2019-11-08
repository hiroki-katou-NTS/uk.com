package nts.uk.ctx.hr.develop.infra.entity.humanresourceevaluation.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.humanresourceevaluation.HumanResourceEvaluationRepository;
import nts.uk.ctx.hr.develop.dom.humanresourceevaluation.PersonnelAssessment;

public class JpaHREvaluationRepository implements HumanResourceEvaluationRepository {

	@Override
	public List<PersonnelAssessment> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds,
			GeneralDate startDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
