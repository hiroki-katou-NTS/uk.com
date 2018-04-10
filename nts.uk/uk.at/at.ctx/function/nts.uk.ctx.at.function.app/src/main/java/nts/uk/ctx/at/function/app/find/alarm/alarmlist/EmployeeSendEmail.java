package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import lombok.Value;

@Value
public class EmployeeSendEmail {
	private String workplaceId;
	private String workplaceName;
	private String employeeId;
	private String employeeCode;
	private String employeeName;
}
