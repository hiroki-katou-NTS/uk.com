package nts.uk.ctx.pr.proto.app.command.paymentdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.PaymentDataCommandBase;
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
public class InsertPaymentDataCommand extends PaymentDataCommandBase{
	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	@Override
	public Payment toDomain(String companyCode) {
		Payment domain = super.toDomain(companyCode);
		domain.setDetailPaymentItems(this.setDetailItems(this.getDetailPaymentItems()));
		domain.setDetailDeductionItems(this.setDudectionDetailItems(this.getDetailDeductionItems()));
		domain.setDetailArticleItems(this.setDetailItems(this.getDetailDeductionItems()));
		domain.setDetailPersonalTimeItems(this.setDetailItems(this.getDetailPersonalTimeItems()));
		
		return domain;
	}
}
