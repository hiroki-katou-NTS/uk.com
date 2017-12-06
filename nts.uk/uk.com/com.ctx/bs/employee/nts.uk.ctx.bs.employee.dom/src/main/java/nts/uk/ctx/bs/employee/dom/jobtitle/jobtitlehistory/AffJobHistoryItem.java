package nts.uk.ctx.bs.employee.dom.jobtitle.jobtitlehistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode;

/**
 * The Class AffJobHistoryItem.
 */
//  所属職位履歴項目
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AffJobHistoryItem {

	
	/** The history Id. */
	// 履歴ID
	private String historyId;
	
	/** The Employee Id. */
	// 社員ID
	private String employeeId;
	
	/** The job title code. */
	// 職位コード
	private JobTitleCode jobTitleCode;
	
	/** The AffJobHistoryItemNote. */
	// 備考
	private AffJobHistoryItemNote note;
}
