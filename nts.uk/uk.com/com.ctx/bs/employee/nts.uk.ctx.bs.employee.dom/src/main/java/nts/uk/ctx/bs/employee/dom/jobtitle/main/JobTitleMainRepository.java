package nts.uk.ctx.bs.employee.dom.jobtitle.main;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface JobTitleMainRepository {

	public Optional<JobTitleMain> getJobTitleMainById(String jobTitleMainId);

	// chua implement
	public Optional<JobTitleMain> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);
	
}
