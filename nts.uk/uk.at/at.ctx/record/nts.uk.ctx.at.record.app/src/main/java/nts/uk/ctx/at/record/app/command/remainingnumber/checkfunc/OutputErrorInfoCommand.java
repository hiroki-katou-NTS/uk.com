package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

import lombok.Data;

@Data
public class OutputErrorInfoCommand {
	private String employeeCode;
	private String employeeName;
	private String errorMessage;
}
