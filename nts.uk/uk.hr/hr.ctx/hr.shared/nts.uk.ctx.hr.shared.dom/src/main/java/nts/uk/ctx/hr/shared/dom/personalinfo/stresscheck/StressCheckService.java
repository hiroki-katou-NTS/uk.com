package nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

@Stateless
public class StressCheckService {

	@Inject
	private StressCheckRepository stressCheckRepo;

	// ストレスチェックのロード
	public StressCheckManagement loadStressCheck(List<String> employeeIds, GeneralDate startDate) {
		List<StressCheckResults> stressChecks = stressCheckRepo.getPersonnelAssessmentByEmployeeIds(employeeIds, startDate);
		return new StressCheckManagement(stressChecks, employeeIds);
	}

	// ストレスチェックの取得
	public List<StressCheckResults> getStressCheck(String employeeId, GeneralDate startDate) {
		
		StressCheckManagement hrEval = loadStressCheck(Arrays.asList(employeeId), startDate);

		return hrEval.getStressChecks();
	}
}
