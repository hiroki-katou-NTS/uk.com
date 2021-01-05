package nts.uk.ctx.sys.assist.dom.favorite.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeBasicImport {
	/** The emp id. */
	// 社員ID
	private String employeeId;

	/** The employeeCode. */
	// 個人ID
	private String personId;

	/** The BusinessName. */
	// ビジネスネーム
	private String BusinessName;
}
