package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import lombok.Data;

@Data
public class OutputErrorInfoCommand {
	private String employeeCode;
	private String employeeName;
	private String errorMessage;
}
