package nts.uk.ctx.at.shared.dom.adapter.jobtitle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@Getter
@Setter
@AllArgsConstructor
public class SharedAffJobTitleHisImport {

	private String employeeId;

	// 職位ID
	private String jobTitleId;

	private DatePeriod dateRange;

	// 職位名称
	private String jobTitleName;

	//職位コード
	private String jobTitleCode;
	
	public SharedAffJobTitleHisImport(String employeeId, String jobTitleId, DatePeriod dateRange, String jobTitleName) {
		super();
		this.employeeId = employeeId;
		this.jobTitleId = jobTitleId;
		this.dateRange = dateRange;
		this.jobTitleName = jobTitleName;
	}

}
