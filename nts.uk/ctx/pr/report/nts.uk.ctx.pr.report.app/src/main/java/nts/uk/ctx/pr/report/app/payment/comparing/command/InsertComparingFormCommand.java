package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.util.List;

import lombok.Data;

@Data
public class InsertComparingFormCommand {
	private String formCode;
	private String formName;
	private List<BaseEntityCommand> comparingFormDetailList;
}
