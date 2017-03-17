package nts.uk.ctx.pr.report.app.payment.comparing.command;

import lombok.Data;

@Data
public class InsertComparingFormHeaderCommand {
	private String formCode;
	private String formName;
}
