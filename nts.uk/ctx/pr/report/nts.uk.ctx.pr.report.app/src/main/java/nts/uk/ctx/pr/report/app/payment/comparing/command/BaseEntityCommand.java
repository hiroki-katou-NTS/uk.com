package nts.uk.ctx.pr.report.app.payment.comparing.command;

import lombok.Data;

@Data
public class BaseEntityCommand {
	 private String itemCode;
	 private int categoryAtr;
	 private int dispOrder;
}
