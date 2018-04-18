package nts.uk.ctx.at.request.app.command.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@AllArgsConstructor
public class DetailSendMailCommand {
	@Getter
	private String employeeID;
	private String employeeName;
	private String sMail;
}
