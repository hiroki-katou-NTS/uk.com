package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import lombok.Data;

@Data
public class FuncEmployeeSearchDto {
	/**
	 * employeeID
	 */
	private String id;
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
