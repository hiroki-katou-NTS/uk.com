package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BaseEntityCommand {
	 private String itemCode;
	 private int categoryAtr;
	 private BigDecimal dispOrder;
}
