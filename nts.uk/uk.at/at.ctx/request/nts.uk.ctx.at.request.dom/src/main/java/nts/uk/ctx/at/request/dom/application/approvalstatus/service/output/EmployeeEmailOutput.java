package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;

/**
 * 個人社員基本情報
 * 
 * @author dat.lh
 */

@AllArgsConstructor
@Value
public class EmployeeEmailOutput {
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

	public static EmployeeEmailOutput fromImport(EmployeeEmailImport domain) {
		return new EmployeeEmailOutput(domain.getSId(), domain.getSName(), domain.getEntryDate(), domain.getRetiredDate(),
				domain.getMailAddr());
	}
}
