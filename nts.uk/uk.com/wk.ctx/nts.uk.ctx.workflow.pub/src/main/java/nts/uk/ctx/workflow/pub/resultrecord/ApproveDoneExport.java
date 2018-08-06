package nts.uk.ctx.workflow.pub.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 承認対象者の特定日の日別確認が承認済かチェックする
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproveDoneExport {
	
	/**
	 * 年月日
	 */
	private GeneralDate date;
	
	/**
	 * 承認済か？
	 */
	private boolean isApproved;
	
}
