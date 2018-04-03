package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeSendEmail {
	private String workplaceId;
	private String workplaceName;
	private String employeeId;
	private String employeeCode;
	private String employeeName;
}
