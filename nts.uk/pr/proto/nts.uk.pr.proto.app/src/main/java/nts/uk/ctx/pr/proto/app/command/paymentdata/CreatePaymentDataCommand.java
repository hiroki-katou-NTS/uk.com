package nts.uk.ctx.pr.proto.app.command.paymentdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Command: add payment data
 * @author chinhbv
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentDataCommand {
	private String personId;
	
	private int processingNo;
	
	/**
	 * Current processing year month
	 */
	private int processingYearMonth;
}
