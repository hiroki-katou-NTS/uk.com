package nts.uk.screen.at.ws.schedule.employeeinfo;

import lombok.Value;

@Value
public class EmployeeInfoKsuSDto {
	private String id;
	private String code;
	private String name;
	private String affiliationName;
	private boolean isAlreadySetting;
}
