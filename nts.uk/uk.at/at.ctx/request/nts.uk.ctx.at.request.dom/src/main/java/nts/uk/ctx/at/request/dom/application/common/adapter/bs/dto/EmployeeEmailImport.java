package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEmailImport {

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
