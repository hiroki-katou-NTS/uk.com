package nts.uk.ctx.at.request.pub.application.approvalstatus;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class ApprovalStatusEmployeeExport {
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 開始日
	 */
	private GeneralDate closureStart;
	/**
	 * 終了日
	 */
	private GeneralDate closureEnd;
}
