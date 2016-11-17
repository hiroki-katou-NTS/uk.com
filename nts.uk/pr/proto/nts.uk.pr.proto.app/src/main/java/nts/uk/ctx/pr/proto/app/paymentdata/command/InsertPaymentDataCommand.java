package nts.uk.ctx.pr.proto.app.paymentdata.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

/**
 * Command: insert payment data
 * 
 * @author vunv
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InsertPaymentDataCommand extends PaymentDataCommand{
	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	@Override
	public Payment toDomain(String companyCode, String personId) {
		Payment domain = super.toDomain(companyCode, personId);
		return domain;
	}
}
