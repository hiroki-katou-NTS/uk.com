package nts.uk.ctx.at.record.dom.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員情報
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class EmployeeInfoImported {
	private String sid;
	private String employeeCode;
	private String employeeName;
}
