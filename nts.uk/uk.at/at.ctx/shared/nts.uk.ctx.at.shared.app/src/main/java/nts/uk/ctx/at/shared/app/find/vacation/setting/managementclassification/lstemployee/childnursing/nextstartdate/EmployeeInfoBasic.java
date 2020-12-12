package nts.uk.ctx.at.shared.app.find.vacation.setting.managementclassification.lstemployee.childnursing.nextstartdate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeInfoBasic {
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 社員コード
	 */
	private String employeeCode;
	
	/**
	 * 社員名
	 */
	private String employeeName;
}
