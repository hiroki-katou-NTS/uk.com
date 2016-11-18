package nts.uk.ctx.pr.proto.app.paymentdata.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

/**
 * Command: add payment data
 * 
 * @author vunv
 *
 */
@Getter
@Setter
public class UpdatePaymentDataCommand extends PaymentDataCommand {
	/** version */
	private long version;

	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	@Override
	public Payment toDomain(String companyCode) {
		Payment domain = super.toDomain(companyCode);
		domain.setVersion(this.version);
		return domain;
	}
}
