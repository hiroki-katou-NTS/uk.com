package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSearchDto {
	/**
	 * employeeID
	 */
	public String id;
	/**
	 * employeeCode
	 */
	private String code;
	/**
	 * employeeName
	 */
	private String name;
	private String workplaceId;
	private String workplaceCode;
	private String workplaceName;
}
