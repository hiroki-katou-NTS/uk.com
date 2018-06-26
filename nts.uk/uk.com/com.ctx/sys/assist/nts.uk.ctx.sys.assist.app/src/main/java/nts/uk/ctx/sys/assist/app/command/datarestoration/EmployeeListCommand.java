package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Value;

@Value
public class EmployeeListCommand {
	public String code;
	public String id;
	public Boolean isAlreadySetting;
	public String name;
}
