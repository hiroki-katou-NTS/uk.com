package nts.uk.ctx.at.request.pub.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 */
@Value
@AllArgsConstructor
public class EmployeeEmailExport {
	/** The emp id. */
	// 社員ID
	private String sId;

	/** The emp name. */
	// 社員名
	private String sName;

	/** The entry date. */
	// 入社年月日
	private GeneralDate entryDate;

	/** The retired date. */
	// 退社年月日
	private GeneralDate retiredDate;

	/** The mail addr. */
	// 会社メールアドレス
	private String mailAddr;
}
