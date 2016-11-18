package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.List;

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
	private List<String> personIdList;
	
	private int processingNo;
	
	/**
	 * Current processing year month
	 */
	private int processingYearMonth;
}
