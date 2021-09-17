package nts.uk.ctx.at.shared.dom.adapter.jobtitle;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
@Value
public class SharedAffJobTitleHisImport {

	private String employeeId;

	// 職位ID
	private String jobTitleId;

	private DatePeriod dateRange;

	// 職位名称
	private String jobTitleName;

	//職位コード
	private String jobTitleCode;

}
