package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author sang.nv
 * 過去履歴
 */
@Data
@AllArgsConstructor
public class PastHistoryDto {
	/** 開始日 */
	private GeneralDate startDate;
	/** 終了日 */
	private GeneralDate endDate;
	/** 所属長のコード */
	private String departmentCode;
	/** 所属長の名前 */
	private String departmentName;
	/** 日別の承認する人のコード */
	private String dailyApprovalCode;
	/** 日別の承認する人の名前 */
	private String dailyApprovalName;
}