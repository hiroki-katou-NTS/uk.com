package nts.uk.ctx.at.function.pub.alarmworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員情報
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class EmployeeInfoExport {
	private String sid;
	private String employeeCode;
	private String employeeName;
}
