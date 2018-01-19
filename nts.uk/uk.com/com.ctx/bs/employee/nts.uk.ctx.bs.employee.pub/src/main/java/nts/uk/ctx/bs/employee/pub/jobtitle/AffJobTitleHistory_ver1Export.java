package nts.uk.ctx.bs.employee.pub.jobtitle;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.shr.com.history.DateHistoryItem;

@AllArgsConstructor
public class AffJobTitleHistory_ver1Export {

	/** 会社ID */
	public String companyId;

	/** The employee id. */
	// 社員ID
	public String employeeId;
	
	
	public String jobTitleId;

	/** The Date History Item. */
	// 履歴項目
	public List<DateHistoryItem> historyItems;

}
