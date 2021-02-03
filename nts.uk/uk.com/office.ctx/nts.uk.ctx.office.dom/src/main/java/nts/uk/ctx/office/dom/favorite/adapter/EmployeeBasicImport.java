package nts.uk.ctx.office.dom.favorite.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeBasicImport {
	/** The emp id. */
	// 社員ID
	private String employeeId;
	
	// 個人ID
	private String personalId;

	/** The BusinessName. */
	// ビジネスネーム
	private String businessName;
	
	/** The employee code. */
	// 社員コード
	private String employeeCode;
}
