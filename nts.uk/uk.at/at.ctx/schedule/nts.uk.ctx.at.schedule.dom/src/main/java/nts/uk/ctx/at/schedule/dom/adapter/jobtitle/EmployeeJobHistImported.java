package nts.uk.ctx.at.schedule.dom.adapter.jobtitle;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
public class EmployeeJobHistImported {
	// 社員ID
	private String employeeId;

	/** The job title id. */
	// 職位ID
	private String jobTitleID;

	/** The job title name. */
	// 職位名称
	private String jobTitleName;

	/** The start date. */
	// 配属期間 start
	private GeneralDate startDate;

	/** The end date. */
	// 配属期間 end
	private GeneralDate endDate;

	public EmployeeJobHistImported(String employeeId, String jobTitleID, String jobTitleName, GeneralDate startDate,
			GeneralDate endDate) {
		super();
		this.employeeId = employeeId;
		this.jobTitleID = jobTitleID;
		this.jobTitleName = jobTitleName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
