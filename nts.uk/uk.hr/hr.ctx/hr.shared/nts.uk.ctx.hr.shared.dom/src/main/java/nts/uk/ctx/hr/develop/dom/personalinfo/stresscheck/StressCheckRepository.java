package nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface StressCheckRepository {

	List<StressCheck> getPersonnelAssessmentByEmployeeId(String employeeId);

	List<StressCheck> getPersonnelAssessmentByEmployeeIds(List<String> employeeIds, GeneralDate startDate);

}
