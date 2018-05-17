package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeWithRangeDto {

	/** ビジネスネーム */
	private String businessName;
	/** 個人ID */
	private String personID;
	/** 社員コード */
	private String employeeCD;
	/** 社員ID */
	private String employeeID;
}
