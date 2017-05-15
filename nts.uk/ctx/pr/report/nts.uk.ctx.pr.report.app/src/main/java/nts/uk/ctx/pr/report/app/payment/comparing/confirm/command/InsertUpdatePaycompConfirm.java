package nts.uk.ctx.pr.report.app.payment.comparing.confirm.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InsertUpdatePaycompConfirm {

	private String employeeCode;

	private int processingYMEarlier;

	private int processingYMLater;

	private int categoryAtr;

	private String itemCode;

	private int confirmedStatus;

	private BigDecimal valueDifference;

	private String reasonDifference;
}
