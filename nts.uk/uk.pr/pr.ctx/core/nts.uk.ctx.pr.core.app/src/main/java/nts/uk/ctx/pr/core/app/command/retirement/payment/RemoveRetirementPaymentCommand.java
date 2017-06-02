package nts.uk.ctx.pr.core.app.command.retirement.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@NoArgsConstructor
public class RemoveRetirementPaymentCommand {
	private String companyCode;
	private String personId;
	private String payDate;
}
