package nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface StressCheckRepository {

	List<StressCheck> getPersonnelAssessmentByEmployeeId(String employeeId);

	List<StressCheckResults> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds, GeneralDate startDate);

}
